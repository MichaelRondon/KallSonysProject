/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.util;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author acost
 */
public class ProductUtilSingleton {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductUtilSingleton.class);
    
    private ProductUtilSingleton() {
    }
    
    public static ProductUtilSingleton getInstance() {
        return ProductUtilSingletonHolder.INSTANCE;
    }
    
    private static class ProductUtilSingletonHolder {
        
        private static final ProductUtilSingleton INSTANCE = new ProductUtilSingleton();
    }
    
    public BasicSearchParams getBasicSearchParams(Pageable pageable) {
        BasicSearchParams basicSearchParams = new BasicSearchParams();
        basicSearchParams.setItemsPerPage(pageable.getPageSize());
        basicSearchParams.setPage(pageable.getPageNumber());
//        basicSearchParams.setSort(pageable.getSort().toString());
//        basicSearchParams.setSortType(pageable.getSort().toString());
        LOGGER.info("Sort: {}", pageable.getSort().toString());
        return basicSearchParams;
    }
}
