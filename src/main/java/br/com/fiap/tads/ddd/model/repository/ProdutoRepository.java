package br.com.fiap.tads.ddd.model.repository;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.multi.MultiPopupMenuUI;

import br.com.fiap.tads.ddd.model.Loja;
import br.com.fiap.tads.ddd.model.Produto;
import br.com.fiap.tads.ddd.model.TipoProduto;

public class ProdutoRepository extends Repository {

	private static final Logger logger = Logger.getLogger(ProdutoRepository.class.getName());

	public static List<Produto> findAll() {
		logger.log(Level.INFO, "Finding all Produtos.");

		List<Produto> retorno = new ArrayList<>();

		String sql = "SELECT * from DDD_PRODUTO";
		logger.log(Level.INFO, sql);
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {

				while (rs.next()) {

					long id = rs.getLong("ID");
					String nome = rs.getString("NOME");
					String descricao = rs.getString("DESCRICAO");
					String tipo = rs.getString("TIPO");
					Loja loja = LojaRepository.findById(rs.getLong("LOJA"));
					double preco = rs.getDouble("PRECO");

					LocalDate dataProducao = null;
					if (rs.getDate("DT_PRODUCAO") != null) {
						dataProducao = rs.getDate("DT_PRODUCAO").toLocalDate();
					}

					LocalDate dataValidade = null;

					if (rs.getDate("DT_VALIDADE") != null) {
						rs.getDate("DT_VALIDADE").toLocalDate();
					}

					String modelo = rs.getString("MODELO");

					if (rs.getInt("PERECIVEL") == 1) {
						Produto pp = new Produto(id, nome, modelo, descricao, tipo, loja, preco, true, dataProducao,
								dataValidade);
						retorno.add(pp);
					} else {
						Produto pnp = new Produto(id, nome, modelo, descricao, tipo, loja, preco, false, dataProducao,
								dataValidade);
						retorno.add(pnp);
					}

				}
			} else {
				System.out.println("Não temos produtos cadastrados no banco de dados.");
			}
			return retorno;
		} catch (SQLException e) {
			System.out.println("Não foi possível consultar os produtos disponíveis: " + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println("Erro ao tentar fechar o Statment ou o ResultSet");
			}

		}

		return retorno;
	}

	public static Produto findById(Long idProduto) {

		logger.log(Level.INFO, "Finding Produto by id: " + idProduto);

		Produto retorno = null;

		String sql = "SELECT * from DDD_PRODUTO where id=?";
		logger.log(Level.INFO, sql);
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, idProduto);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {

				while (rs.next()) {

					long id = rs.getLong("ID");
					String nome = rs.getString("NOME");
					String descricao = rs.getString("DESCRICAO");
					String tipo = rs.getString("TIPO");
					Loja loja = LojaRepository.findById(rs.getLong("LOJA"));
					double preco = rs.getDouble("PRECO");

					LocalDate dataProducao = null;
					if (rs.getDate("DT_PRODUCAO") != null) {
						dataProducao = rs.getDate("DT_PRODUCAO").toLocalDate();
					}

					LocalDate dataValidade = null;

					if (rs.getDate("DT_VALIDADE") != null) {
						rs.getDate("DT_VALIDADE").toLocalDate();
					}

					String modelo = rs.getString("MODELO");
					if (rs.getInt("PERECIVEL") == 1) {
						Produto pp = new Produto(id, nome, modelo, descricao, tipo, loja, preco, true, dataProducao,
								dataValidade);
						retorno = pp;
					} else {
						Produto pnp = new Produto(id, nome, modelo, descricao, tipo, loja, preco, false, dataProducao,
								dataValidade);
						retorno = pnp;
					}

				}
			} else {
				System.out.println("Não temos produtos cadastrados no banco de dados.");
			}
			return retorno;
		} catch (SQLException e) {
			System.out.println("Não foi possível consultar os produtos disponíveis: " + e.getMessage());
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println("Erro ao tentar fechar o Statment ou o ResultSet");
			}

		}

		return retorno;

	}

	@SuppressWarnings("deprecation")
	public static Produto save(Produto p) {

		logger.log(Level.INFO, "Persistindo Produto: " + p);

		// Colocando SQL numa transaaction para pegar o id do café inserido
		String sql = "begin INSERT INTO DDD_PRODUTO (ID, NOME, DESCRICAO, MODELO, TIPO, LOJA, PRECO, PERECIVEL, DT_PRODUCAO, DT_VALIDADE) VALUES ( sq_produto.nextval,?,?,?,?,?,?,?,?,?) returning id into ?;  end;";

		LocalDate prod = null;

		if (p.getDataProducao() != null)
			prod = LocalDate.of(p.getDataProducao().getYear(), p.getDataProducao().getMonthValue(),
					p.getDataProducao().getDayOfMonth());

		LocalDate valid = null;

		if (p.getDataValidade() != null)
			LocalDate.of(p.getDataValidade().getYear(), p.getDataValidade().getMonthValue(),
					p.getDataValidade().getDayOfMonth());

		CallableStatement s = null;
		try {
			s = getConnection().prepareCall(sql);
			s.setString(1, p.getNome());
			s.setString(2, p.getDescricao());
			s.setString(3, p.getModelo());
			s.setString(4, p.getTipo());
			s.setLong(5, p.getLoja().getId());
			s.setDouble(6, p.getPreco());
			s.setInt(7, p.isPerecivel() ? 1 : 0);
			s.setDate(8,
					prod != null ? new Date(prod.getYear(), prod.getMonthValue() - 1, prod.getDayOfMonth()) : null);

			s.setDate(9,
					valid != null ? new Date(valid.getYear(), valid.getMonthValue() - 1, valid.getDayOfMonth()) : null);

			s.registerOutParameter(10, java.sql.Types.INTEGER);

			s.executeUpdate();

			p.setId((long) s.getInt(10));

			return p;

		} catch (SQLException e) {
			System.out.println("Erro ao salvar o produto o banco de dados: \n" + p + "\n\n" + e.getMessage());
		} finally {
			try {
				if (s != null)
					s.close();
			} catch (SQLException e) {
				System.out.println("Erro ao tentar fechar o Statment ");
			}
		}
		return null;
	}

}
