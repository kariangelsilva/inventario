package com.trabajo.inventario.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trabajo.inventario.dto.DetalleCompraRequestDTO;
import com.trabajo.inventario.dto.DetalleCompraResponseDTO;
import com.trabajo.inventario.repository.DetalleCompraRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleCompraService {
    
    private final DetalleCompraRepository detalleCompraRepository;
    private final ProductoRepository productoRepository;
    private final CompraRepository compraRepository;
    
    public DetalleCompraResponseDTO agregarProducto(DetalleCompraRequestDTO request) {
        
        Compra compra = compraRepository.findById(request.getCompraId())
            .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        
        if (!"PENDIENTE".equals(compra.getEstado())) {
            throw new RuntimeException("No se pueden agregar productos a una compra procesada");
        }
        
        Producto producto = productoRepository.findById(request.getProductoId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        if (producto.getCantidad() < request.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getCantidad());
        }
        
        DetalleCompra detalle = new DetalleCompra();
        detalle.setCompra(compra);
        detalle.setProducto(producto);
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.calcularSubtotal();
        
        DetalleCompra guardado = detalleCompraRepository.save(detalle);
        
        return new DetalleCompraResponseDTO(guardado);
    }
    
    @Transactional
    public void confirmarCompra(Long compraId) {
        
        Compra compra = compraRepository.findById(compraId)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        
        if (!"PENDIENTE".equals(compra.getEstado())) {
            throw new RuntimeException("La compra ya fue procesada");
        }
        
        List<DetalleCompra> detalles = detalleCompraRepository.findByCompraId(compraId);
        
        if (detalles.isEmpty()) {
            throw new RuntimeException("La compra no tiene productos");
        }
        
        for (DetalleCompra detalle : detalles) {
            Producto producto = detalle.getProducto();
            
            if (producto.getCantidad() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }
            
            producto.setCantidad(producto.getCantidad() - detalle.getCantidad());
            productoRepository.save(producto);
        }
        
        compra.setEstado("COMPLETADA");
        compraRepository.save(compra);
    }
    
    @Transactional
    public void cancelarCompra(Long compraId) {
        
        Compra compra = compraRepository.findById(compraId)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        
        if ("COMPLETADA".equals(compra.getEstado())) {
            List<DetalleCompra> detalles = detalleCompraRepository.findByCompraId(compraId);
            
            for (DetalleCompra detalle : detalles) {
                Producto producto = detalle.getProducto();
                producto.setCantidad(producto.getCantidad() + detalle.getCantidad());
                productoRepository.save(producto);
            }
        }
        
        compra.setEstado("CANCELADA");
        compraRepository.save(compra);
    }
    
    public List<DetalleCompraResponseDTO> obtenerDetallesPorCompra(Long compraId) {
        List<DetalleCompra> detalles = detalleCompraRepository.findByCompraId(compraId);
        
        return detalles.stream()
            .map(DetalleCompraResponseDTO::new)
            .collect(Collectors.toList());
    }
    
    public void eliminarDetalle(Long detalleId) {
        
        DetalleCompra detalle = detalleCompraRepository.findById(detalleId)
            .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        
        if (!"PENDIENTE".equals(detalle.getCompra().getEstado())) {
            throw new RuntimeException("No se puede eliminar detalle de compra procesada");
        }
        
        detalleCompraRepository.deleteById(detalleId);
    }
}
