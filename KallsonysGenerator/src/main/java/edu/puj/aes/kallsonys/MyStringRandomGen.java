package edu.puj.aes.kallsonys;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * From http://www.java2novice.com/java-collections-and-util/random/string/
 *
 * @author Michael Felipe Rondón Acosta
 */
public class MyStringRandomGen {

    private static final String CHAR_LIST
            = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890Diegoesmaricon";
    private static final int RANDOM_STRING_LENGTH = 10;
    private static final String[] CATEGORIAS = new String[]{"Celulares", "Televisores y Video", "Audio", "Computadores e Impresión",
         "SmartWatch", "Cámaras", "Videojuegos", "Electrohogar", "Casa Inteligente", "Accesorios", "Drones", "Deportes"};

    /**
     * This method generates random string
     *
     * @return
     */
    public String generateRandomString() {

        StringBuilder randStr = new StringBuilder();
        Random randomLenghtGenerator = new Random();
        int nextInt = randomLenghtGenerator.nextInt(15 + 1);
        for (int i = 0; i < nextInt; i++) {
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * This method generates random numbers
     *
     * @return int
     */
    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
    
    public String getRamdomCategoria() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CATEGORIAS.length);
        if (randomInt - 1 == -1) {
            return CATEGORIAS[randomInt];
        } else {
            return CATEGORIAS[randomInt - 1];
        }
    }

    public String getSentence(int wordsQuantity) {
        if (wordsQuantity <= 1) {
            return this.generateRandomString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.generateRandomString());
        for (int i = 1; i < wordsQuantity; i++) {
            stringBuilder.append(' ');
            stringBuilder.append(this.generateRandomString());
        }
        return stringBuilder.toString();
    }

        Random random = new Random();
        List<String> strings = new LinkedList<>();
        int nextInt = random.nextInt(10);
        if(nextInt < 3){
            nextInt = 10;
        }
        for (int i = 0; i < nextInt; i++) {
            strings.add(generateRandomString());
        }
        return strings;
    }

    public List<String> getRamdomKeyWords() {
        Random random = new Random();
        List<String> strings = new LinkedList<>();
        int nextInt = random.nextInt(5);
        for (int i = 0; i < nextInt; i++) {
            strings.add(getFromArray(KEY_WORDS));
        }
        strings.add(generateRandomString());
        return strings;
    }
    
    public String getRamdomMarca(){
        return getFromArray(MARCAS);
    }
    
    private String getFromArray(String[] array) {
        Random random = new Random();
        int nextInt = random.nextInt(array.length);
        return array[nextInt];
    }

    public static final String[] KEY_WORDS = new String[]{
        "Blanco", "Negro", "Gris", "Amarillo", "Azul", "Rojo", "Verde", "Naranja",
        "Cafe", "Grande", "Pequeño", "Mediano", "HD", "Portátil", "Ligero", "De lujo",
        "Resistente", "Suave", "Blando", "Alta definición", "Multiusuario", "Especial",
        "Resistente", "Gamers", "Digital", "Compacto", "Estéreo", "Periférico", "Automático",
        "Avanzado", "Científica", "Empresarial", "Híbrido", "Inalámbrico", "Insuatrial",
        "Innovador", "Térmico", "Empresarial", "Híbrido", "Inalámbrico", "Insuatrial", "Especial"
    };

    public static final String[] MARCAS = new String[]{
        "Samsung", "Nokia", "Apple", "LG", "Nintendo", "Amazon", "Google", "Microsoft",
        "Asus", "Lenovo", "SEGA", "Acer", "IBM", "Compact", "Motorola", "Xiaomi",
        "HP", "Toshiba", "Mac", "Apple", "Hyundai", "Challenger",
        "Kalley"
    };
}
