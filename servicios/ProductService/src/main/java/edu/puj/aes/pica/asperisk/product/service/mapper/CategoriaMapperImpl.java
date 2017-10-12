/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.mapper;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.CategoriaDTO;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Categoria;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author acost
 */
@Service
public class CategoriaMapperImpl implements CategoriaMapper{

    @Override
    public Categoria toEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setCategoria(dto.getCategoria());
        categoria.setId(dto.getId());
        return categoria;
    }

    @Override
    public CategoriaDTO toDto(Categoria entity) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setCategoria(entity.getCategoria());
        categoriaDTO.setId(entity.getId());
        return categoriaDTO;
    }

    @Override
    public List<Categoria> toEntity(List<CategoriaDTO> dtoList) {
        return dtoList.parallelStream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<CategoriaDTO> toDto(List<Categoria> entityList) {
        return entityList.parallelStream().map(this::toDto).collect(Collectors.toList());
    }
    
}
