package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.product.service.client.ProductServiceRestClient;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProductoDTO;
import edu.puj.aes.pica.asperisk.oms.utilities.mapper.ProductoDtoToJson;
import edu.puj.aes.pica.asperisk.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;
    private final ProductoDtoToJson productoDTOToJSON = new ProductoDtoToJson();

    @Autowired
    private ProductServiceRestClient productServiceRestClient;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
//        Producto producto = productoMapper.toEntity(productoDTO);
//        producto = productoRepository.save(producto);
//        return productoMapper.toDto(producto);
        return productoDTOToJSON.fromJson(productServiceRestClient.save(productoDTOToJSON.toJson(productoDTO)));
    }

//    public void delete(ProductoDTO productoDTO) {
//        log.debug("Request to delete Producto : {}", productoDTO);
//        Producto producto = productoMapper.toEntity(productoDTO);
//        producto = productoRepository.save(producto);
//        return productoMapper.toDto(producto);
//        productServiceRestClient.delete(productoDTO);
//    }
    /**
     * Get all the productos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
//        return productoRepository.findAll(pageable)
//                .map(productoMapper::toDto);
        return productServiceRestClient.findAll(pageable).map(productoDTOToJSON::fromJson);
    }

    @Transactional(readOnly = true)
    public Page<ProductoDTO> find(Pageable pageable,
            Long codigoProducto,
            String nombreProducto,
            String descripcion) {
        log.debug("Request to find Productos");
        log.debug("Request to find pageable: " + pageable);
        return productServiceRestClient.find(pageable, codigoProducto, nombreProducto, descripcion).map(productoDTOToJSON::fromJson);
    }

    /**
     * Get one producto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProductoDTO findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
//        Producto producto = productoRepository.findOne(id);
//        return productoMapper.toDto(producto);
        return productoDTOToJSON.fromJson(productServiceRestClient.findOne(id));
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
//        productoRepository.delete(id);
        productServiceRestClient.delete(id);
    }
}
