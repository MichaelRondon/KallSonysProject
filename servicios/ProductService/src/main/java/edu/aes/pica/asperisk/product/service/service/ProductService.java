package edu.aes.pica.asperisk.product.service.service;

import edu.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;

/**
 * Created by mfrondon on 31/07/2017.
 */
public interface ProductService {

    ProductsResponse consultarHistorico(HistoricoRequest historicoRequest);
    ProductsResponse buscar(SearchRequest searchRequest);

}
