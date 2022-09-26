package br.com.fiap.tads.ddd.model;

public enum TipoProduto {

	ELETRONICOS("Eletrônicos"), SOFTWARE("Software"), MOVEIS("Móveis"), ALIMENTOS("Alimentos"), OUTROS("Outros");

	private String descricao;

	TipoProduto(String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}