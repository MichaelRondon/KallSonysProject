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
        product.setCategoria(myStringRandomGen.getRamdomCategoria());
        product.setDescripcion(myStringRandomGen.getSentence(random.nextInt(15)));
        product.setDisponibilidad(random.nextInt(1000));
        product.setEstado(State.ACTIVO);
        product.setFechaRevDisponibilidad(new Date());
        product.setKeyWords(myStringRandomGen.getRamdomKeyWords());
        product.setMarca(myStringRandomGen.getRamdomMarca());
        product.setNombre(myStringRandomGen.getSentence(random.nextInt(3)+1));
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

        long initTime;
        for (int i = 0; i < 1000000; i++) {
            initTime = System.currentTimeMillis();
            System.out.println("i:" + i);
//            System.out.println("i:" + productGenerator.getProduct());
            productServiceRestClientImpl.save(productGenerator.getProduct());
            System.out.println("TIEMPO: " + (System.currentTimeMillis() - initTime));
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProductGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
