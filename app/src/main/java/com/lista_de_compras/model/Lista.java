package com.lista_de_compras.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by evely on 30/08/2017.
 */

public class Lista implements Serializable {
    private Integer codigo;
    private CategoriaDeLista categoria;
    private List<Produto> produtos;
    private String nome;
    private Date dataCriacao;
    private Date dataCompra;

    public Lista() {
    }

    public Lista(int codigo, CategoriaDeLista categoria, List<Produto> produtos, String nome, Date dataCriacao, Date dataCompra) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.produtos = produtos;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataCompra = dataCompra;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public CategoriaDeLista getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDeLista categoria) {
        this.categoria = categoria;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lista lista = (Lista) o;

        if (codigo != null ? !codigo.equals(lista.codigo) : lista.codigo != null) return false;
        if (categoria != null ? !categoria.equals(lista.categoria) : lista.categoria != null)
            return false;
        if (produtos != null ? !produtos.equals(lista.produtos) : lista.produtos != null)
            return false;
        if (nome != null ? !nome.equals(lista.nome) : lista.nome != null) return false;
        if (dataCriacao != null ? !dataCriacao.equals(lista.dataCriacao) : lista.dataCriacao != null)
            return false;
        return dataCompra != null ? dataCompra.equals(lista.dataCompra) : lista.dataCompra == null;

    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        result = 31 * result + (produtos != null ? produtos.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (dataCriacao != null ? dataCriacao.hashCode() : 0);
        result = 31 * result + (dataCompra != null ? dataCompra.hashCode() : 0);
        return result;
    }
}
