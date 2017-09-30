package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public abstract class Response<T>  implements Serializable{
    
    private AsperiskPage page;
    
    @JsonIgnore
    private List<T> objects;
}
