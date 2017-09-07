package edu.puj.aes.pica.asperisk.oms.utilities.model;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public abstract class ScrollResponse<T> extends Response<T> implements Serializable{

    private String scrollId;
}
