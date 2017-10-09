package edu.puj.aes.kallsonys;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println("sds");
        ProductGenerator productGenerator = new ProductGenerator();
        System.out.println("sds2");
        ProductServiceRestClientImpl productServiceRestClientImpl = new ProductServiceRestClientImpl();

        long initTime;
        for (int i = 0; i < 100000; i++) {
            initTime = System.currentTimeMillis();
            System.out.println("i:" + i);
            productServiceRestClientImpl.save(productGenerator.getProduct());
            System.out.println("TIEMPO: " + (System.currentTimeMillis() - initTime));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProductGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
