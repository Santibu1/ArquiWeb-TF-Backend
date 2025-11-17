package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.ProductoDTO;
import com.upc.ecochipstf.entities.Empresa;
import com.upc.ecochipstf.entities.Producto;
import com.upc.ecochipstf.interfaces.IProductoService;
import com.upc.ecochipstf.repositorios.EmpresaRepository;
import com.upc.ecochipstf.repositorios.ProductoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Empresa empresa = empresaRepository.findById(productoDTO.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + productoDTO.getEmpresaId()));

        Producto producto = modelMapper.map(productoDTO, Producto.class);
        producto.setEmpresa(empresa);
        producto.setEstado("Activo"); // por defecto

        producto = productoRepository.save(producto);
        return modelMapper.map(producto, ProductoDTO.class);
    }
    @Override
    public List<ProductoDTO> listarTodosProductos() {
        return productoRepository.findAll()
                .stream()
                .map(prod -> modelMapper.map(prod, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByEstado("Activo")
                .stream()
                .map(prod -> modelMapper.map(prod, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> listarProductosPorEmpresa(Long empresaId) {
        return productoRepository.findByEmpresaEmpresaId(empresaId)
                .stream()
                .map(prod -> modelMapper.map(prod, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(prod -> modelMapper.map(prod, ProductoDTO.class))
                .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado"));
    }

    @Override
    public ProductoDTO actualizarProducto(ProductoDTO productoDTO) {
        return productoRepository.findById(productoDTO.getProductoId())
                .map(existing -> {
                    Producto producto = modelMapper.map(productoDTO, Producto.class);
                    producto.setEmpresa(existing.getEmpresa()); // mantener empresa original
                    Producto guardado = productoRepository.save(producto);
                    return modelMapper.map(guardado, ProductoDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Producto con ID " + productoDTO.getProductoId() + " no encontrado"));
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.findById(id).ifPresent(prod -> {
            prod.setEstado("Inactivo"); // baja lógica
            productoRepository.save(prod);
        });
    }
}
