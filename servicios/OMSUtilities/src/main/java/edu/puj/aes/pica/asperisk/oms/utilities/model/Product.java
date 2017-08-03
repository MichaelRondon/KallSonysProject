package edu.puj.aes.pica.asperisk.oms.utilities.model;

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
    private String nombre;
    private String descripcion;
    private String categoria;
    private String marca;
    private BigDecimal precio;
    private State estado;
    @JsonProperty("key-words")
    private List<String> keyWords;
    private Set<BasicProveedor> proveedores;
    private Integer disponibilidad;
    @JsonProperty("fecha-rev-disponibilidad")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date fechaRevDisponibilidad;
}
