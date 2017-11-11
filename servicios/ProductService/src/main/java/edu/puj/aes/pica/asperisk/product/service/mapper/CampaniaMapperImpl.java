package edu.puj.aes.pica.asperisk.product.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author acost
 */
@Service
public class CampaniaMapperImpl implements CampaniaMapper {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CampaniaMapperImpl.class);

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public Campania toEntity(Campanign dto) {
        Campania campania = new Campania();
        campania.setCategoria(dto.getCategoria());
        campania.setDescripcion(dto.getDescripcion());
        campania.setEstado(dto.getEstado());
        campania.setFechaFin(dto.getFechaFin());
        campania.setFechaInicio(dto.getFechaInicio());
        campania.setId(dto.getId());
        campania.setNombre(dto.getNombre());

        campania.setProductos(mapProductsDtoToJSONString(dto.getProductos()));
        return campania;
    }

    @Override
    public Campanign toDto(Campania entity) {

        Campanign campaniaDTO = new Campanign();
        campaniaDTO.setCategoria(entity.getCategoria());
        campaniaDTO.setDescripcion(entity.getDescripcion());
        campaniaDTO.setEstado(entity.getEstado());
        campaniaDTO.setFechaFin(entity.getFechaFin());
        campaniaDTO.setFechaInicio(entity.getFechaInicio());
        campaniaDTO.setId(entity.getId());
        campaniaDTO.setNombre(entity.getNombre());
        campaniaDTO.setProductos(mapJSONStringToProducts(entity.getProductos()));
        return campaniaDTO;
    }

    public List<Product> mapJSONStringToProducts(String jsonProducts) {
        ObjectMapper mapper = new ObjectMapper();
        List<Product> products = new LinkedList<>();
        if (jsonProducts == null) {
            return products;
        }
        try {
            Long[] readValue = mapper.readValue(jsonProducts, Long[].class);
            List<Long> asList = Arrays.asList(readValue);
            Product product;
            for (Long long1 : asList) {
                product = new Product();
                product.setId(long1);
                products.add(product);
            }
        } catch (IOException ex) {
            LOGGER.error("Error convirtiendo cadena JSON {} a Products. Error: {}", jsonProducts, ex.getMessage());
        }
        return products;
    }

    public String mapProductsDtoToJSONString(List<Product> productos) {
        if (productos == null) {
            return null;
        }
        return Arrays.toString(productos.stream().map(Product::getId).collect(Collectors.toList()).toArray());
    }

    @Override
    public List<Campania> toEntity(List<Campanign> dtoList) {
        return dtoList.parallelStream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Campanign> toDto(List<Campania> entityList) {
        return entityList.parallelStream().map(this::toDto).collect(Collectors.toList());
    }

}
