package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.RangeSearchParams;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mfrondon on 03/08/2017.
 */
@Data
@ToString
public class HistoricoRequest {

    private String categoria;
    private Integer tamanio;
    private RangeSearchParams rangeSearchParams;
    private BasicSearchParams basicSearchParams;
    private BasicRequest basicRequest;
}
