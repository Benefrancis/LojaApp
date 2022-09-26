package br.com.fiap.tads.ddd.model;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.fiap.tads.ddd.model.dto.LocalDateDeserializer;
import br.com.fiap.tads.ddd.model.dto.LocalDateSerializer;

public class Produto {

	private Long id;

	private String nome;

	private String modelo;

	private String descricao;

	private String tipo;

	private Loja loja;

	private double preco;

	private boolean perecivel;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dataProducao;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dataValidade;

	public Produto() {
		super();
		this.dataProducao = (this.dataProducao == null) ? LocalDate.now() : this.dataProducao;
	}

	public Produto(Long id, String nome, String modelo, String descricao, String tipo, Loja loja, double preco,
			boolean perecivel, LocalDate dataProducao, LocalDate dataValidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.modelo = modelo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.loja = loja;
		this.preco = preco;
		this.perecivel = perecivel;
		this.dataProducao = (this.dataProducao == null) ? LocalDate.now() : this.dataProducao;
		this.dataValidade = dataValidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo.getDescricao();
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public boolean isPerecivel() {
		return perecivel;
	}

	public void setPerecivel(boolean perecivel) {
		this.perecivel = perecivel;
	}

	public LocalDate getDataProducao() {
		return dataProducao;
	}

	public void setDataProducao(LocalDate dataProducao) {
		this.dataProducao = dataProducao;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataProducao, dataValidade, descricao, id, loja, modelo, nome, perecivel, preco, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(dataProducao, other.dataProducao) && Objects.equals(dataValidade, other.dataValidade)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Objects.equals(loja, other.loja) && Objects.equals(modelo, other.modelo)
				&& Objects.equals(nome, other.nome) && perecivel == other.perecivel
				&& Double.doubleToLongBits(preco) == Double.doubleToLongBits(other.preco) && tipo == other.tipo;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", modelo=" + modelo + ", descricao=" + descricao + ", tipo="
				+ tipo + ", loja=" + loja + ", preco=" + preco + ", perecivel=" + perecivel + ", dataProducao="
				+ dataProducao + ", dataValidade=" + dataValidade + "]";
	}

}
