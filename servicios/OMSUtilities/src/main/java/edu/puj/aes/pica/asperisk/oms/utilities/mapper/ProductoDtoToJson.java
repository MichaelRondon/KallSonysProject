/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.oms.utilities.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProductoDTO;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author acost
 */
public class ProductoDtoToJson {

    public Product toJson(ProductoDTO productoDTO) {
        Product product = new Product();
        product.setCategoria(productoDTO.getCategoria());
        product.setDescripcion(productoDTO.getDescripcion());
        product.setDisponibilidad(productoDTO.getDisponibilidad());
        product.setEstado(productoDTO.getEstado());
        if (productoDTO.getFechaRevDisponibilidad() != null) {
//            product.setFechaRevDisponibilidad(Date.from(productoDTO.getFechaRevDisponibilidad().atZone(ZoneId.systemDefault()).toInstant()));
            product.setFechaRevDisponibilidad(Date.from(productoDTO.getFechaRevDisponibilidad()));
        }
        product.setId(productoDTO.getId());
        product.setKeyWords(productoDTO.getKeyWords());
        product.setMarca(productoDTO.getMarca());
        product.setNombre(productoDTO.getNombre());
        product.setPrecio(productoDTO.getPrecio());
//        product.setProveedores(productoDTO.getProveedores());
        return product;
    }

    public ProductoDTO fromJson(Product product) {
        ProductoDTO productoDto = new ProductoDTO();
        productoDto.setCategoria(product.getCategoria());
        productoDto.setDescripcion(product.getDescripcion());
        productoDto.setDisponibilidad(product.getDisponibilidad());
        productoDto.setEstado(product.getEstado());
        if (product.getFechaRevDisponibilidad() != null) {
//            productoDto.setFechaRevDisponibilidad(LocalDateTime.ofInstant(product.getFechaRevDisponibilidad().toInstant(), ZoneId.systemDefault()));
            productoDto.setFechaRevDisponibilidad(product.getFechaRevDisponibilidad().toInstant());
        }
        productoDto.setId(product.getId());
        productoDto.setKeyWords(product.getKeyWords());
        productoDto.setMarca(product.getMarca());
        productoDto.setNombre(product.getNombre());
        productoDto.setPrecio(product.getPrecio());
//        productoDto.setProveedores(product.getProveedores());
        return productoDto;
    }

}
