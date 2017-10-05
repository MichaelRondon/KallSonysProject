package edu.puj.aes.pica.asperisk.service.generator;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.State;
import edu.puj.aes.pica.asperisk.product.service.client.ProductServiceRestClientImpl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class ProductGenerator {

    private final MyStringRandomGen myStringRandomGen = new MyStringRandomGen();
    private final Random random = new Random();

    public Product getProduct() {
        Product product = new Product();
        product.setCategoria(myStringRandomGen.generateRandomString());
        product.setDescripcion(myStringRandomGen.getSentence(random.nextInt(15)));
        product.setDisponibilidad(random.nextInt(1000));
        product.setEstado(State.ACTIVO);
        product.setFechaRevDisponibilidad(new Date());
        product.setKeyWords(myStringRandomGen.getRamdomListStrings());
        product.setMarca(myStringRandomGen.getSentence(random.nextInt(3)));
        product.setNombre(myStringRandomGen.getSentence(random.nextInt(4)));
        product.setPrecio(new BigDecimal(random.nextDouble()));                
        return product;
    }

    public State getRamdomState() {
        if (random.nextBoolean()) {
            return State.ACTIVO;
        }
        return State.INACTIVO;
    }
    
    public static void main(String[] args) {
        ProductGenerator productGenerator = new ProductGenerator();
        ProductServiceRestClientImpl productServiceRestClientImpl = new ProductServiceRestClientImpl();
        for (int i = 0; i < 1000000; i++) {
            productServiceRestClientImpl.save(productGenerator.getProduct());
            
        }
    }
}
