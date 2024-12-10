package com.example.projetoap2;

import java.util.ArrayList;

public class Defensivo extends Produto {
	private String classe;
	private ArrayList<String> principiosAtivos;
	private ArrayList<Double> concentracoes; // 0 a 100.
	private int nivelToxicidade; // 1 a 5.
	private String toxicidade; // toxicidade em texto

	public Defensivo(String nome, double preco, int quantidade, String classe, ArrayList<String> principiosAtivos, ArrayList<Double> concentracoes, int nivelToxicidade) {
		super(nome, preco, quantidade); // Chama o construtor da classe Produto
		this.classe = classe;
		this.principiosAtivos = principiosAtivos;
		this.concentracoes = concentracoes;
		this.nivelToxicidade = nivelToxicidade;
		this.toxicidade = converterToxicidade(nivelToxicidade);
	}

	public Defensivo(String nome, String fabricante, double preco, String classe, ArrayList<String> principiosAtivos, ArrayList<Double> concentracoes, int nivelToxicidade, Object o) {
		super();
	}

	private String converterToxicidade(int nivelToxicicidade) {
		String toxico;
		switch (nivelToxicicidade) {
			case 1:
				toxico = "Extremamente perigoso";
				break;
			case 2:
				toxico = "Altamente perigoso";
				break;
			case 3:
				toxico = "Moderadamente perigoso";
				break;
			case 4:
				toxico = "Pouco perigoso";
				break;
			case 5:
				toxico = "Improvável que Apresente Perigo Agudo";
				break;
			default:
				toxico = "Nível de toxicidade desconhecido";
		}
		return toxico;
	}

	public String getTipo() {
		return "Defensivo: " + classe;
	}

	public ArrayList<Double> getConcentracoes() {
		return concentracoes;
	}

	public ArrayList<String> getPrincipiosAtivos() {
		return principiosAtivos;
	}

	public String getToxicidade() {
		return toxicidade;
	}

	@Override
	public String toString() {
		return super.toString() +
				" | Classe: " + classe +
				" | Toxicidade: " + toxicidade +
				" | Princípios Ativos: " + principiosAtivos;
	}
}
