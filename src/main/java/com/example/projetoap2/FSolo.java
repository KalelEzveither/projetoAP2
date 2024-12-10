package com.example.projetoap2;

public class FSolo extends Fertilizante {
    private  int quantidade;
    private int tamanhoSaco; //em quilogramas
    private String forma; //granulado ou purificado
    private boolean concentrado;
    //private static int qtd = 0;

    public FSolo(String nome, String fabricante,int quantidade, double preco, int cN, int cP, int cK, int tamanhoSaco, String forma, boolean concentrado) {
        super(nome,preco,quantidade, cN, cP, cK);
        this.tamanhoSaco = tamanhoSaco;
        this.forma = forma;
        this.concentrado = concentrado;
        //qtd++;
    }

    public int getTamanhoSaco() {
        return tamanhoSaco;
    }

    public String getForma() {
        return forma;
    }

    public boolean getConcentrado() {
        return concentrado;
    }

    public String getTipo(){
        return "Fertilizante de solo";
    }


}
