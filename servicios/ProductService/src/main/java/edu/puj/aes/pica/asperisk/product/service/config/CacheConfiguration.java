package edu.puj.aes.pica.asperisk.product.service.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureBefore(value = {WebConfiguration.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania.class.getName(), jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania.class.getName() + ".productos", jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Producto.class.getName(), jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Producto.class.getName() + ".proveedores", jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.DatoContacto.class.getName(), jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Proveedor.class.getName(), jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Proveedor.class.getName() + ".datosContactos", jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Proveedor.class.getName() + ".productos", jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.ProveedorProducto.class.getName(), jcacheConfiguration);
            cm.createCache(edu.puj.aes.pica.asperisk.product.service.jpa.entity.Categoria.class.getName(), jcacheConfiguration);
//            cm.createCache(edu.puj.aes.pica.asperisk.domain.User.class.getName(), jcacheConfiguration);
//            cm.createCache(edu.puj.aes.pica.asperisk.domain.Authority.class.getName(), jcacheConfiguration);
//            cm.createCache(edu.puj.aes.pica.asperisk.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
