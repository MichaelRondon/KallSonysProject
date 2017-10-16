package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Campanign  implements Serializable{


    private Long id;
    private String nombre;
    private String descripcion;
    private List<Product> productos;
    private State estado;
//    @JsonProperty("fecha_inicio")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaInicio;
//    @JsonProperty("fecha_fin")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaFin;
    private Categoria categoria;

}
