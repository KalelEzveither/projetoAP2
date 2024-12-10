package com.example.projetoap2;

public abstract class Fertilizante extends Produto {
    private double cN, cP, cK; // Concentração de: Nitrogênio (N), Fósforo (P), Potássio (K).
    private String NPK;

    public Fertilizante(String nome, double preco, int quantidade, double cN, double cP, double cK) {
        super(nome, preco, quantidade); // Chamando o construtor da classe Produto
        this.cN = cN;
        this.cP = cP;
        this.cK = cK;
        this.NPK = formatarNPK(); // Formatar NPK na inicialização
    }

    public double getCn() {
        return cN;
    }

    public double getCp() {
        return cP;
    }

    public double getCk() {
        return cK;
    }

    public String formatarNPK() {
        return cN + "." + cP + "." + cK;
    }

    public String getNPK() {
        return NPK;
    }

    @Override
    public String toString() {
        return super.toString() + " | NPK: " + NPK;
    }
}
