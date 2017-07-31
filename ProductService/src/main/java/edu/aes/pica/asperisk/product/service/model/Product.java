package edu.aes.pica.asperisk.product.service.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class Product {

    private Long id;
    private String producto;
    private String descripcion;
    private Long precio;
    private Estado estado;
    private String categoria;
    private Set<String> keyWords;
    private Set<Long> proveedores;
    private Integer disponibilidad;
    private LocalDate fechaRevDisponibilidad;
}
