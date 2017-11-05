package edu.puj.aes.kallsonys;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
    private final List<Long> idWithoutData = new LinkedList<>();
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

    public Runnable findEmptyData() {
        Runnable runnableFindEmpty = () -> {
            for (long i = 5414L; i < 1200000; i++) {
//                try {
                    Product findOne = productServiceRestClientImpl.findOne(i);
                    if (findOne == null) {
                        LOGGER.error("\n\n\n\n*************************Producto nulo id: {}*************************\n\n", i);
                        idWithoutData.add(i);
                    }
                    LOGGER.error("ids sin datos: {}. Id actual: {}", idWithoutData, i);

//                    Thread.sleep(5000);
//                } catch (InterruptedException ex) {
//                    java.util.logging.Logger.getLogger(ProductGenerator.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }

        };
        return runnableFindEmpty;
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
        long contadorRemplazos = 300689L;
        long long_ = 963192L;
//        newFixedThreadPool.submit(productGenerator.findEmptyData());
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
                if (i % 25 == 0) {
                    Thread.sleep(5000);

                }
            } catch (InterruptedException ex) {
                LOGGER.error("Error en el Thread", ex);
            }
        }
    }
}
