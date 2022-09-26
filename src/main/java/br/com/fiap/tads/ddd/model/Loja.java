package br.com.fiap.tads.ddd.model;

public class Loja {

	private Long id;

	private String nome;

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

	public Loja(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Loja() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Loja [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome : "") + "]";
	}

}
