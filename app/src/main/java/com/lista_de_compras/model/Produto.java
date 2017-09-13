package com.lista_de_compras.model;

/**
 * Created by evely on 30/08/2017.
 */

public class Produto {
    private int codigo;
    private String nome;
    private CategoriaDeProduto categoria;
    private double valor;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
