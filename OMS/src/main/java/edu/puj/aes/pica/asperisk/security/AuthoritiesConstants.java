package edu.puj.aes.pica.asperisk.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String OTHER = "ROLE_OTHER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String CAMPANIAS = "CAMPANIAS";
    
    public static final String CLIENTES_ADMON = "CLIENTES_ADMON";
    
    public static final String CLIENTES_CONSULTA = "CLIENTES_CONSULTA";
    
    public static final String ORDENES_ADMON = "ORDENES_ADMON";
    
    public static final String ORDENES_CONSULTA = "ORDENES_CONSULTA";
    
    public static final String PRODUCTOS_ADMON = "PRODUCTOS_ADMON";
    
    public static final String PRODUCTOS_CONSULTA = "PRODUCTOS_CONSULTA";
    
    private AuthoritiesConstants() {
    }
}
