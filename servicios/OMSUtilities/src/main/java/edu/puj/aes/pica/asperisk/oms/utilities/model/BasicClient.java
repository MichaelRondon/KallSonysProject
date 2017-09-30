package edu.puj.aes.pica.asperisk.oms.utilities.model;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mfrondon on 03/08/2017.
 */
@Data
@ToString
public class BasicClient implements Serializable{

    private String ip;
    private Long clienteId;
}
