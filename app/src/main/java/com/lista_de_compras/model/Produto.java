package com.lista_de_compras.model;

import java.io.Serializable;

/**
 * Created by evely on 30/08/2017.
 */

public class Produto implements Serializable {
    private Integer codigo;
    private String descricao;
    private CategoriaDeProduto categoria;
    private double valor;
    private Boolean selecionado;

    public Produto() {
    }

    public Produto(Integer codigo, String descricao, CategoriaDeProduto categoria, double valor, Boolean selecionado) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.selecionado = selecionado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaDeProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDeProduto categoria) {
        this.categoria = categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public Boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produto produto = (Produto) o;

        if (Double.compare(produto.valor, valor) != 0) return false;
        if (descricao != null ? !descricao.equals(produto.descricao) : produto.descricao != null)
            return false;
        if (categoria != null ? !categoria.equals(produto.categoria) : produto.categoria != null)
            return false;
        return selecionado != null ? selecionado.equals(produto.selecionado) : produto.selecionado == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = descricao != null ? descricao.hashCode() : 0;
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        temp = Double.doubleToLongBits(valor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (selecionado != null ? selecionado.hashCode() : 0);
        return result;
    }
}
