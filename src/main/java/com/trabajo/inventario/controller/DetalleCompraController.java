package com.trabajo.inventario.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.trabajo.inventario.dto.DetalleCompraRequestDTO;
import com.trabajo.inventario.dto.DetalleCompraResponseDTO;
import com.trabajo.inventario.service.DetalleCompraService;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-compra")
@RequiredArgsConstructor 
public class DetalleCompraController {
    
    private final DetalleCompraService detalleCompraService;
    
    @PostMapping
    public ResponseEntity<?> agregarProducto(@Validated @RequestBody DetalleCompraRequestDTO request) {
        try {
            DetalleCompraResponseDTO response = detalleCompraService.agregarProducto(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/confirmar/{compraId}")
    public ResponseEntity<?> confirmarCompra(@PathVariable Long compraId) {
        try {
            detalleCompraService.confirmarCompra(compraId);
            return ResponseEntity.ok("Compra confirmada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/cancelar/{compraId}")
    public ResponseEntity<?> cancelarCompra(@PathVariable Long compraId) {
        try {
            detalleCompraService.cancelarCompra(compraId);
            return ResponseEntity.ok("Compra cancelada");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{compraId}")
    public ResponseEntity<List<DetalleCompraResponseDTO>> obtenerDetalles(@PathVariable Long compraId) {
        List<DetalleCompraResponseDTO> detalles = detalleCompraService.obtenerDetallesPorCompra(compraId);
        return ResponseEntity.ok(detalles);
    }
    
    @DeleteMapping("/{detalleId}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Long detalleId) {
        try {
            detalleCompraService.eliminarDetalle(detalleId);
            return ResponseEntity.ok("Detalle eliminado");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}