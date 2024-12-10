package com.example.projetoap2;

public class FLiquido extends Fertilizante {
    private float qtdLitros; //quantidade de litros no frasco.
    //private static int qtd = 0;

    public FLiquido (String nome, String fabricante,int quantidade, double preco, int cN, int cP, int cK, float qtdLitros) {
        super(nome,preco,quantidade, cN, cP, cK);
        this.qtdLitros = qtdLitros;
        //qtd++;
    }

    public float getQtdLitros() {
        return qtdLitros;

    }
    public String getTipo(){
        return "Fertilizante líquido";
    }


}
