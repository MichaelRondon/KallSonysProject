package edu.aes.pica.asperisk.product.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class Product {

    private Long id;
    private String producto;
    private String descripcion;
    private BigDecimal precio;
    private State estado;
    private String categoria;
    @JsonProperty("key-words")
    private List<String> keyWords;
    private Set<Long> proveedores;
    private Integer disponibilidad;
    @JsonProperty("fecha-rev-disponibilidad")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date fechaRevDisponibilidad;
}
