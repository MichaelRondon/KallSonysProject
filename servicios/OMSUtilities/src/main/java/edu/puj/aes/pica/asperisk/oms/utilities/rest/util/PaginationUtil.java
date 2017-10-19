package edu.puj.aes.pica.asperisk.oms.utilities.rest.util;

import edu.puj.aes.pica.asperisk.oms.utilities.model.AsperiskPage;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Response;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the
 * <a href="https://developer.github.com/v3/#pagination">GitHub API</a>, and
 * follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link
 * header)</a>.
 */
public final class PaginationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaginationUtil.class);

    private PaginationUtil() {
    }

    public static HttpHeaders generatePaginationHttpHeaders(Page page, String baseUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        String link = "";
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
        }
        // prev link
        if ((page.getNumber()) > 0) {
            link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
        }
        link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }

    public static PageRequest getPageRequest(Integer page, String sort, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        if (!sort.isEmpty()) {
            Sort.Order order;
            String property = "id";
            if (sort.contains(":")) {
                property = sort.substring(0, sort.indexOf(":"));
            }
            if (sort.contains("DESC")) {
                order = new Sort.Order(Sort.Direction.DESC, property);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, property);
            }
            pageRequest = new PageRequest(page, size, new Sort(order));
        }
        return pageRequest;
    }
    public static PageRequest getPageRequest(BasicSearchParams basicSearchParams) {
        
        Integer itemsPerPage = basicSearchParams.getItemsPerPage();
        Integer page = basicSearchParams.getPage();
        String sort = basicSearchParams.getSort();
        if (itemsPerPage == null || itemsPerPage < 1) {
            itemsPerPage = 20;
        }
        PageRequest pageRequest = new PageRequest(page, itemsPerPage);
        if (!sort.isEmpty()) {
            Sort.Order order;
            String property = "id";
            if (sort.contains(":")) {
                property = sort.substring(0, sort.indexOf(":"));
            }
            if (sort.contains("DESC")) {
                order = new Sort.Order(Sort.Direction.DESC, property);
            } else {
                order = new Sort.Order(Sort.Direction.ASC, property);
            }
            pageRequest = new PageRequest(page, itemsPerPage, new Sort(order));
        }
        return pageRequest;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }

    public static <R extends Response> void setResponseFromOrderedList(BasicSearchParams basicSearchParams, List list, R response) {

        AsperiskPage asperiskPage = new AsperiskPage();
        response.setPage(asperiskPage);

        asperiskPage.setNumber(basicSearchParams.getPage());
        asperiskPage.setSort(basicSearchParams.getSort());
        asperiskPage.setSortType(basicSearchParams.getSortType() == null ? null : basicSearchParams.getSortType().name());

        asperiskPage.setTotalElements(list.size());
        asperiskPage.setTotalPages(asperiskPage.getTotalElements() / basicSearchParams.getItemsPerPage());
        LOGGER.info("asperiskPage.getTotalElements: {}", asperiskPage.getTotalElements());
        LOGGER.info("basicSearchParams.getItemsPerPage(): {}", basicSearchParams.getItemsPerPage());
        LOGGER.info("asperiskPage.getTotalPages: {}", asperiskPage.getTotalPages());

        int lastIndex = 999;
        int firstIndex = 0;
        LOGGER.info("basicSearchParams.getItemsPerPage(): {}", basicSearchParams.getItemsPerPage());
        LOGGER.info("basicSearchParams.getPage(): {}", basicSearchParams.getPage());
        if (basicSearchParams.getItemsPerPage() != null && basicSearchParams.getPage() != null) {
            lastIndex = basicSearchParams.getItemsPerPage() * (basicSearchParams.getPage() + 1);
            firstIndex = (basicSearchParams.getPage()) * basicSearchParams.getItemsPerPage();
        }

        LOGGER.info("lastIndex: {} firstIndex:{}", lastIndex, firstIndex);
        if (list.size() <= firstIndex || lastIndex <= firstIndex) {
            response.setObjects(new LinkedList<>());
            return;
        }
        if (lastIndex > list.size()) {
            lastIndex = list.size();
        }
        response.setObjects(list.subList(firstIndex, lastIndex));
    }

    public static <R extends Response> void setResponseFromPage(BasicSearchParams basicSearchParams, Page<?> page, R response) {

        AsperiskPage asperiskPage = new AsperiskPage();
        response.setPage(asperiskPage);

        asperiskPage.setNumber(page.getNumber());
        asperiskPage.setSort(basicSearchParams.getSort());
        asperiskPage.setSortType(basicSearchParams.getSortType() == null ? null : basicSearchParams.getSortType().name());

        asperiskPage.setTotalElements((new BigDecimal(page.getTotalElements())).intValue());
        asperiskPage.setTotalPages(page.getTotalPages());
        LOGGER.info("asperiskPage.getTotalElements: {}", asperiskPage.getTotalElements());
        LOGGER.info("basicSearchParams.getItemsPerPage(): {}", basicSearchParams.getItemsPerPage());
        LOGGER.info("asperiskPage.getTotalPages: {}", asperiskPage.getTotalPages());
        LOGGER.info("page.hasContent(): {}", page.hasContent());

    }

}
