package com.example.projetoap2;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private String telefone;
    private String fazenda;

    public Cliente(String nome, String cpf, String telefone, String fazenda) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.fazenda = fazenda;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getFazenda() {
        return fazenda;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", fazenda='" + fazenda + '\'' +
                '}';
    }
}
