package edu.puj.aes.kallsonys;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    public static void main(String[] args) {
        ProductGenerator productGenerator = new ProductGenerator();
        ProductServiceRestClientImpl productServiceRestClientImpl = new ProductServiceRestClientImpl();
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(6);
        Runnable runnable = () -> {
            long initTime = System.currentTimeMillis();
            productServiceRestClientImpl.save(productGenerator.getProduct());
            LOGGER.info("TIEMPO: {}", (System.currentTimeMillis() - initTime));
        };

        for (int i = 0; i < 200000; i++) {
            LOGGER.info("i: {}", i);
            try {
                long initTime = System.currentTimeMillis();
                newFixedThreadPool.submit(runnable);
                LOGGER.info("TIEMPO2: {}", (System.currentTimeMillis() - initTime));
                Thread.sleep(400);
                if (i % 100 == 0) {
                    Thread.sleep(3500);

                }
            } catch (InterruptedException ex) {
                LOGGER.error("Error en el Thread", ex);
            }
        }
    }
}
