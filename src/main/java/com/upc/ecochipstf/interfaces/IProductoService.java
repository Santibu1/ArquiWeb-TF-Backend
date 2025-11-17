package com.upc.ecochipstf.interfaces;

import com.upc.ecochipstf.dto.ProductoDTO;

import java.util.List;

public interface IProductoService {
    public ProductoDTO crearProducto(ProductoDTO productoDTO);
    public List<ProductoDTO> listarTodosProductos();
    public List<ProductoDTO> listarProductosActivos();
    public List<ProductoDTO> listarProductosPorEmpresa(Long empresaId);
    public ProductoDTO obtenerProductoPorId(Long id);
    public ProductoDTO actualizarProducto(ProductoDTO productoDTO);
    public void eliminarProducto(Long id);
}
