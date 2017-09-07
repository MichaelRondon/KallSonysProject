package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public abstract class Response<T>  implements Serializable{

    @JsonProperty("total-pages")
    private Integer totalPages;
    @JsonProperty("total-elements")
    private Integer totalElements;
    private Integer number;
    private String sort;
    @JsonProperty("sort-type")
    private String sortType;
    private String custom;
    @JsonIgnore
    private List<T> objects;
}
