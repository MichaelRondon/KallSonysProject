package edu.puj.aes.pica.asperisk.oms.utilities.model;

import lombok.Data;
import lombok.ToString;


/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
@ToString
public class BasicSearchParams {

    private Integer page;
    private Integer itemsPerPage;
    private String sort;
    private SortType sortType;
}
