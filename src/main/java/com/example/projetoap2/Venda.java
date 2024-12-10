package com.example.projetoap2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Venda implements Serializable {
    private String vendedor;
    private Cliente cliente;
    private ArrayList<ItemVenda> itensVenda;
    private String formaPagamento;
    private double valorTotal;

    public Venda(String vendedor, Cliente cliente, ArrayList<ItemVenda> itensVenda, String formaPagamento, double valorTotal) {
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.itensVenda = itensVenda;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
    }

    public Venda(String vendedor, Cliente cliente, double valorTotal) {
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }


    public String getVendedor() {
        return vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Collection<? extends ItemVenda> getItens() {
        return itensVenda;
    }
}
