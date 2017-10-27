package edu.puj.aes.pica.asperisk.product.service.service;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ProductScrollResponse;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollSearchRequest;
import edu.puj.aes.pica.asperisk.product.jpa.service.repository.ProductoRepository;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Producto;
import edu.puj.aes.pica.asperisk.product.service.mapper.ProductoMapperImpl;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignRequest;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignResponse;
import edu.puj.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.puj.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.puj.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.puj.aes.pica.asperisk.product.service.model.TestResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@Service
@Qualifier("jpa")
public class JpaProductService implements ProductService {
    
    private final Logger log = LoggerFactory.getLogger(JpaProductService.class);
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ProductoMapperImpl productoMapperImpl;
    
    @Override
    public ProductsResponse consultarHistorico(HistoricoRequest historicoRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ProductsResponse buscar(SearchRequest searchRequest) throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public CampaignResponse campanias(CampaignRequest campaniasRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public TestResponse test() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Product create(Product product) throws ProductTransactionException {
        Producto save = productoRepository.save(productoMapperImpl.toEntity(product));
        return productoMapperImpl.toDto(save);
    }
    
    @Override
    public Product update(Product product) throws ProductTransactionException {
        Producto save = productoRepository.save(productoMapperImpl.toEntity(product));
        return productoMapperImpl.toDto(save);
    }
    
    @Override
    public Product findOne(String id) throws ProductTransactionException {
        Producto save = productoRepository.findOne(Long.parseLong(id));
        log.info("Producto: {}", save);
        return productoMapperImpl.toDto(save);
    }
    
    @Override
    public void delete(Long id) throws ProductTransactionException {
        productoRepository.delete(id);
    }
    
    @Override
    public List<Product> findAllByIds(List<Long> ids) throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Product> findAll() throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ProductScrollResponse findAll(ScrollSearchRequest scrollSearchRequest) throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CampaignResponse findAllCampaigns(CampaignRequest campaniasRequest, boolean full) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cleanData(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    }
