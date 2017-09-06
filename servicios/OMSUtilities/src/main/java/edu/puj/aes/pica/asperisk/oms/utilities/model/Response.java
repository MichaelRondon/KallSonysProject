package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public abstract class Response<T> {

    @JsonProperty("total-pages")
    private Integer totalPages;
    @JsonProperty("total-elements")
    private Integer totalElements;
    private Integer number;
    private String sort;
    @JsonProperty("sort-type")
    private String sortType;
    private String custom;
    private List<T> objects;
}
