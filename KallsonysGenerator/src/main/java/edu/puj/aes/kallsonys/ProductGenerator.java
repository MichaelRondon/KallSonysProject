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
        product.setDisponibilidad(random.nextInt(1000));
        product.setEstado(State.ACTIVO);
        product.setFechaRevDisponibilidad(new Date());
        product.setKeyWords(myStringRandomGen.getRamdomKeyWords());
        String ramdomMarca = myStringRandomGen.getRamdomMarca();
        product.setMarca(ramdomMarca);
        product.setNombre(String.format("%s %s Ref. %s",
                myStringRandomGen.getRamdomNombre(),
                ramdomMarca,
                myStringRandomGen.generateRandomString().toUpperCase()));
        product.setDescripcion(String.format("%s %s", product.getNombre(),
                myStringRandomGen.getSentence(random.nextInt(15))));
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
            long initTime = System.currentTimeMillis();
            product.setId(id);
            Product update = productServiceRestClientImpl.update(product);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                java.util.logging.Logger.getLogger(ProductGenerator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            productServiceRestClientImpl.save(product);
//            Product findOne = productServiceRestClientImpl.findOne(id);
            LOGGER.info("Se remplaza el producto con id: {}", update.getId());
            LOGGER.info("TIEMPO update: {}", (System.currentTimeMillis() - initTime));
        };
        return runnable;
    }

    public void findEmptyData() {
        for (long l = 0; l < 1006702; l++) {
            Product findOne = productServiceRestClientImpl.findOne(l);
            if (findOne == null) {
                LOGGER.error("\n\n\n\n*************************Producto nulo id: {}*************************\n\n", l);
            }
        }
    }

    @Getter
    private ProductServiceRestClientImpl productServiceRestClientImpl = new ProductServiceRestClientImpl();

    public static void main(String[] args) {
        ProductGenerator productGenerator = new ProductGenerator();
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(6);
        Runnable runnableCreate = () -> {
            long initTime = System.currentTimeMillis();
            productGenerator.getProductServiceRestClientImpl().save(productGenerator.getProduct());
            LOGGER.info("TIEMPO: {}", (System.currentTimeMillis() - initTime));
        };
        Runnable runnableFindEmpty = () -> {
            productGenerator.findEmptyData();
        };
        long contadorRemplazos = 77483L;
        long long_ = 963192L;
        newFixedThreadPool.submit(runnableFindEmpty);
        for (long i = 0; i < 1000000; i++) {
            LOGGER.info("i: {}", i);
            try {
                contadorRemplazos++;
                newFixedThreadPool.submit(productGenerator.replace(contadorRemplazos,
                        productGenerator.getProduct()));
                Thread.sleep(500);
//                if (i % 2 == 0) {
//                    newFixedThreadPool.submit(runnableCreate);
////                    newFixedThreadPool.submit(productGenerator.replace(long_,
////                            productGenerator.getProduct()));
//                    long_++;
//                }
                if (i % 50 == 0) {
                    Thread.sleep(5000);

                }
            } catch (InterruptedException ex) {
                LOGGER.error("Error en el Thread", ex);
            }
        }
    }
}
