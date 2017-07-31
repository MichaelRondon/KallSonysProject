package edu.aes.pica.asperisk.product.service.model;

import lombok.Data;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public abstract class Response {

    private Boolean success;
    private String status;
    private String mensaje;
}
