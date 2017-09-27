/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.oms.utilities;

import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriComponentsBuilder;

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
            LOGGER.info("next: {}", next.getProperty());
            LOGGER.info("next: {}", next.getDirection());
            basicSearchParams.setSort(next.getProperty());
            basicSearchParams.setSortType(next.getDirection());
        }
        return basicSearchParams;
    }

    public Map<String, Object> getBasicSearchParams(Pageable pageable,
            Map<String, Object> parameters) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        parameters.put("page", pageable.getPageNumber());
        parameters.put("items_per_page", pageable.getPageSize());

        LOGGER.info("Sort: {}", pageable.getSort().toString());
        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        if (iterator.hasNext()) {
            Sort.Order next = iterator.next();
            LOGGER.info("next: {}", next);
            LOGGER.info("next.getProperty(): {}", next.getProperty());
            LOGGER.info("next.getDirection(): {}", next.getDirection());
            parameters.put("sort", next.getProperty());
            parameters.put("sortType", next.getDirection());
        }
        return parameters;
    }

    public UriComponentsBuilder getBasicSearchParams(Pageable pageable,
            UriComponentsBuilder builder) {

        builder.queryParam("page", pageable.getPageNumber());
        builder.queryParam("items_per_page", pageable.getPageSize());
        LOGGER.info("page: {}", pageable.getPageNumber());
        LOGGER.info("itemsPerPage: {}", pageable.getPageSize());
        if (pageable.getSort() == null) {
            return builder;
        }

        Iterator<Sort.Order> iterator = pageable.getSort().iterator();
        if (iterator.hasNext()) {
            Sort.Order next = iterator.next();
            LOGGER.info("next.getProperty(): {}", next.getProperty());
            LOGGER.info("next.getDirection(): {}", next.getDirection());
            builder.queryParam("sort", next.getProperty());
            builder.queryParam("sortType", next.getDirection());
        }
        return builder;
    }
}
