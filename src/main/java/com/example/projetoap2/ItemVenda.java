package com.example.projetoap2;

import java.io.Serializable;

public class ItemVenda implements Serializable {
    private String nomeProduto;
    private double precoUnitario;
    private int quantidade;
    private double valorTotal;

    // Construtor atualizado
    public ItemVenda(String nomeProduto, double precoUnitario, int quantidade, double valorTotal) {
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    // Método para exibir informações do item
    @Override
    public String toString() {
        return "ItemVenda{" +
                "nomeProduto='" + nomeProduto + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
