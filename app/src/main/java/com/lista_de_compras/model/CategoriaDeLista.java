package com.lista_de_compras.model;

import java.io.Serializable;

/**
 * Created by evely on 30/08/2017.
 */

public class CategoriaDeLista implements Serializable {
    private Integer codigo;
    private String nome;

    public CategoriaDeLista() {
    }

    public CategoriaDeLista(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriaDeLista that = (CategoriaDeLista) o;

        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        return nome != null ? nome.equals(that.nome) : that.nome == null;

    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }
}
