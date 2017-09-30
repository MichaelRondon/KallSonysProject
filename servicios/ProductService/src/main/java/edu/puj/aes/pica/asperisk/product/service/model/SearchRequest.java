package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicClient;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by mfrondon on 03/08/2017.
 */
@Data
@ToString
public class SearchRequest {

    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private Product product;
    private BasicClient basicClient;
    private BasicSearchParams basicSearchParams;
    private BasicRequest basicRequest;
}
