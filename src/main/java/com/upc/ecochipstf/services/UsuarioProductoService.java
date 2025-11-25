package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.UsuarioProductoRequestDTO;
import com.upc.ecochipstf.dto.UsuarioProductoResponseDTO;
import com.upc.ecochipstf.entities.Producto;
import com.upc.ecochipstf.entities.Usuario;
import com.upc.ecochipstf.entities.UsuarioProducto;
import com.upc.ecochipstf.interfaces.IUsuarioProductoService;
import com.upc.ecochipstf.repositorios.ProductoRepository;
import com.upc.ecochipstf.repositorios.UsuarioProductoRepository;
import com.upc.ecochipstf.repositorios.UsuarioRepository;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioProductoService implements IUsuarioProductoService {
    @Autowired
    private UsuarioProductoRepository usuarioProductoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public UsuarioProductoResponseDTO canjear(UsuarioProductoRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(requestDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        int costo = (int) Math.round(producto.getPrecio());
        if(usuario.getEcobits()<costo){
            throw new RuntimeException("Puntos insuficientes");
        }

        if (!"Activo".equalsIgnoreCase(producto.getEstado()) || producto.getStock()<=0){
            throw new RuntimeException("Producto no disponible");
        }

        usuario.setEcobits(usuario.getEcobits()-costo);
        producto.setStock(producto.getStock()-1);

        UsuarioProducto usuarioProducto = new UsuarioProducto();
        usuarioProducto.setUsuario(usuario);
        usuarioProducto.setProducto(producto);
        usuarioProducto.setEstado("Completado");
        usuarioProducto.setEcobitsUtilizados(costo);
        usuarioProducto.setFechaCanje(LocalDateTime.now());

        UsuarioProducto guardar = usuarioProductoRepository.save(usuarioProducto);
        UsuarioProductoResponseDTO dto = modelMapper.map(guardar, UsuarioProductoResponseDTO.class);

        dto.setProducto(producto.getNombre());
        dto.setId(guardar.getId());

        return dto;
    }

    @Override
    public List<UsuarioProductoResponseDTO> historial(Long usuarioId) {
        return usuarioProductoRepository.findByUsuarioUsuarioIdOrderByFechaCanjeDesc(usuarioId)
                .stream()
                .map(a -> {
                    UsuarioProductoResponseDTO dto = modelMapper.map(a, UsuarioProductoResponseDTO.class);
                    dto.setProducto(a.getProducto().getNombre());
                    dto.setUrlImagen(a.getProducto().getUrlImagen()); // <-- Mapeo manual si es necesario
                    dto.setId(a.getId());
                    return dto;
                }).collect(Collectors.toList());
    }
}
