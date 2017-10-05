package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class Product implements Serializable{

    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String marca;
    private BigDecimal precio;
    private State estado;
    @JsonProperty("key_words")
    private List<String> keyWords;
    private Set<BasicProveedor> proveedores;
    private Integer disponibilidad;
    @JsonProperty("fecha_rev_disponibilidad")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date fechaRevDisponibilidad;
//    @JsonProperty("fechaRevDisponibilidad")
//    private String fechaRevDisponibilidadString;
    }
