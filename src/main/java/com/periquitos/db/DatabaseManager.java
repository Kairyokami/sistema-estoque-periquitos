package com.periquitos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Produto {
    private int id;
    private String nome;
    private String codigoBarras;
    private int categoriaId;
    private int fornecedorId;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private int estoqueMinimo;
    private int estoqueAtual;
    private boolean ativo;
    private LocalDateTime dataCadastro;

    public Produto() {
    }

    public Produto(String nome, String codigoBarras, int categoriaId, int fornecedorId,
                  BigDecimal precoCompra, BigDecimal precoVenda, int estoqueMinimo, int estoqueAtual,
                  boolean ativo) {
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.categoriaId = categoriaId;
        this.fornecedorId = fornecedorId;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueAtual = estoqueAtual;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
