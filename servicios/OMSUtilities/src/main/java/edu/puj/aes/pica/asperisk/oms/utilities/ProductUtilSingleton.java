/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.oms.utilities;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        LOGGER.info("Sort: {}", pageable.getSort().toString());
        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        if (iterator.hasNext()) {
            Sort.Order next = iterator.next();
        LOGGER.info("next: {}", next);
        LOGGER.info("next: {}", next);
        LOGGER.info("next: {}", next);
            basicSearchParams.setSort(next.getProperty());
            basicSearchParams.setSortType(next.getDirection());
        }
        return basicSearchParams;
    }
}
