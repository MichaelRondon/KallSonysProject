/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.oms.utilities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author acost
 */
@Data
public class AsperiskPage {

    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_elements")
    private Integer totalElements;
    private Integer number;
    private String sort;
    @JsonProperty("sort_type")
    private String sortType;
    private String custom;
    
}
