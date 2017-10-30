/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Categoria;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Producto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author acost
 */
@Service
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public Product toDto(Producto producto) {
        if (producto == null) {
            return null;
        }
        Product productoDTO = new Product();
        productoDTO.setCategoria(producto.getCategoria());
//        productoDTO.setCategoria(producto.getCategoria() != null ? producto.getCategoria().getCategoria() : null);
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setDisponibilidad(producto.getDisponibilidad());
        productoDTO.setEstado(producto.getEstado());
        productoDTO.setFechaRevDisponibilidad(producto.getFechaRevDisponibilidad());
        productoDTO.setId(producto.getId());
        productoDTO.setKeyWords(transformKeyWordsToList(producto.getKeyWords()));
        productoDTO.setMarca(producto.getMarca());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
//        productoDTO.setProveedores(producto.getProveedores());
        return productoDTO;
    }

    private List<String> transformKeyWordsToList(String keyWordsString) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(keyWordsString);
        stringBuilder.deleteCharAt(stringBuilder.indexOf("["));
        stringBuilder.deleteCharAt(stringBuilder.indexOf("]"));
        String toString = stringBuilder.toString();
        return Arrays.asList(toString.split(","));
    }

    @Override
    public Producto toEntity(Product productoDTO) {
//        Categoria categoria = new Categoria();
        Producto producto = new Producto();
//        if(productoDTO.getCategoria() != null){
//            categoria.setCategoria(productoDTO.getCategoria() );
//        }
        producto.setCategoria(productoDTO.getCategoria());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setDisponibilidad(productoDTO.getDisponibilidad());
        producto.setEstado(productoDTO.getEstado());
        producto.setFechaRevDisponibilidad(productoDTO.getFechaRevDisponibilidad());
        producto.setId(productoDTO.getId());
        producto.setMarca(productoDTO.getMarca());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setKeyWords(productoDTO.getKeyWords().toString());
        return producto;
    }

    @Override
    public List<Producto> toEntity(List<Product> dtoList) {
        return dtoList.parallelStream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Product> toDto(List<Producto> entityList) {
        return entityList.parallelStream().map(this::toDto).collect(Collectors.toList());
    }

}
