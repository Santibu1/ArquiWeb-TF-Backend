package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.ProductoDTO;
import com.upc.ecochipstf.interfaces.IProductoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
public class ProductoController {
    @Autowired
    private IProductoService productoService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/Registrar-Producto")
    public ProductoDTO crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')") // Solo el admin puede ver todos
    @GetMapping("/Listar-Todos-Productos")
    public List<ProductoDTO> listarTodosProductos() {
        return productoService.listarTodosProductos();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/Listar-Productos-Activos")
    public List<ProductoDTO> listarProductosActivos() {
        return productoService.listarProductosActivos();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/Productos-Por-Empresa/{empresaId}")
    public List<ProductoDTO> listarProductosPorEmpresa(@PathVariable Long empresaId) {
        return productoService.listarProductosPorEmpresa(empresaId);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/Listar-Producto-Por/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/Modificar-Producto/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        productoDTO.setProductoId(id);
        return productoService.actualizarProducto(productoDTO);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/Eliminar-Producto/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
