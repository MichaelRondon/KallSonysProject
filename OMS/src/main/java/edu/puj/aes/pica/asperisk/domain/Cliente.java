package edu.puj.aes.pica.asperisk.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "documento", nullable = false)
    private String documento;

    @NotNull
    @Column(name = "nombres", nullable = false)
    private String nombres;

    @NotNull
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo_e")
    private String correoE;

    @Column(name = "jhi_password")
    private String password;

    @OneToOne
    @JoinColumn(unique = true)
    private Tarjeta datosTarjeta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public Cliente documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public Cliente nombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Cliente apellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoE() {
        return correoE;
    }

    public Cliente correoE(String correoE) {
        this.correoE = correoE;
        return this;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

    public String getPassword() {
        return password;
    }

    public Cliente password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tarjeta getDatosTarjeta() {
        return datosTarjeta;
    }

    public Cliente datosTarjeta(Tarjeta tarjeta) {
        this.datosTarjeta = tarjeta;
        return this;
    }

    public void setDatosTarjeta(Tarjeta tarjeta) {
        this.datosTarjeta = tarjeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", documento='" + getDocumento() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correoE='" + getCorreoE() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
