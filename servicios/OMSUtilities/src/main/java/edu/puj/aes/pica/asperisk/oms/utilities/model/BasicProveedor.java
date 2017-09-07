package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * Created by mfrondon on 03/08/2017.
 */
@Data
public class BasicProveedor implements Serializable{

        private Long id;
        @JsonProperty("id-producto")
        private Long idProducto;
}
