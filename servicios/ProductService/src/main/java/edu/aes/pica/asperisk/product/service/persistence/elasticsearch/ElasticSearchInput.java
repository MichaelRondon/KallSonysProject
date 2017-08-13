package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;


import lombok.Getter;
import lombok.ToString;

@ToString
public class ElasticSearchInput {

    private String id;
    private String json;
    private String tipo;

    public ElasticSearchInput setId(String id) {
        this.id = id;
        return this;
    }

    public ElasticSearchInput setJson(String json) {
        this.json = json;
        return this;
    }

    public ElasticSearchInput setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getJson() {
        return json;
    }

    public String getTipo() {
        return tipo;
    }
}
