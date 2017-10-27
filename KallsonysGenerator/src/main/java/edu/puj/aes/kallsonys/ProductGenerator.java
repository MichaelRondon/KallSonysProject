package edu.puj.aes.kallsonys;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class ProductGenerator {

    private final MyStringRandomGen myStringRandomGen = new MyStringRandomGen();
    private final Random random = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGenerator.class);

    public Product getProduct() {
        Product product = new Product();
        product.setCategoria(myStringRandomGen.getRamdomCategoria());
        product.setDescripcion(myStringRandomGen.getSentence(random.nextInt(15)));
        product.setDisponibilidad(random.nextInt(1000));
        product.setEstado(State.ACTIVO);
        product.setFechaRevDisponibilidad(new Date());
        product.setKeyWords(myStringRandomGen.getRamdomKeyWords());
        product.setMarca(myStringRandomGen.getRamdomMarca());
        product.setNombre(myStringRandomGen.getRamdomNombre() + " " + myStringRandomGen.getSentence(random.nextInt(2) + 1));
        product.setPrecio(new BigDecimal(random.nextDouble() * 10000, MathContext.DECIMAL32));
        return product;
    }

    public State getRamdomState() {
        if (random.nextBoolean()) {
            return State.ACTIVO;
        }
        return State.INACTIVO;
    }

    public Runnable replace(Long id, Product product) {
        Runnable runnable;
        runnable = () -> {
            productServiceRestClientImpl.delete(id);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(ProductGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            productServiceRestClientImpl.save(product);
            Product findOne = productServiceRestClientImpl.findOne(id);
            LOGGER.info("Se remplaza el producto con id: {}", findOne.getId());
        };
        return runnable;
    }

    @Getter
    private ProductServiceRestClientImpl productServiceRestClientImpl = new ProductServiceRestClientImpl();

    public static void main(String[] args) {
        ProductGenerator productGenerator = new ProductGenerator();
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(6);
        Runnable runnable = () -> {
            long initTime = System.currentTimeMillis();
            productGenerator.getProductServiceRestClientImpl().save(productGenerator.getProduct());
            LOGGER.info("TIEMPO: {}", (System.currentTimeMillis() - initTime));
        };
        long contadorRemplazos = 0;
        for (long i = 0; i < 200000; i++) {
            LOGGER.info("i: {}", i);
            try {
                newFixedThreadPool.submit(runnable);
                Thread.sleep(400);
                if (i % 6 == 0) {
                    contadorRemplazos++;
                    newFixedThreadPool.submit(productGenerator.replace(contadorRemplazos,
                            productGenerator.getProduct()));
                }
                if (i % 100 == 0) {
                    Thread.sleep(3500);

                }
            } catch (InterruptedException ex) {
                LOGGER.error("Error en el Thread", ex);
            }
        }
    }
}
