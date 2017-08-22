package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Campanign {


    private Long id;
    private String nombre;
    private String descripcion;
    private List<Product> productos;
    private State estado;
    @JsonProperty("fecha-inicio")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaInicio;
    @JsonProperty("fecha-fin")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaFin;

}
