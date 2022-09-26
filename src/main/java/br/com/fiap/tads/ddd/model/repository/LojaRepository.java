package br.com.fiap.tads.ddd.model.repository;

import java.lang.invoke.MethodHandles;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.fiap.tads.ddd.model.Loja;

public class LojaRepository extends Repository {

	private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	public static List<Loja> findAll() {

		logger.log(Level.INFO, "Finding all Lojas.");

		List<Loja> retorno = new ArrayList<>();

		String sql = "SELECT * from DDD_LOJA";
		logger.log(Level.INFO, sql);
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = getConnection().prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					retorno.add(new Loja(rs.getLong("ID"), rs.getString("NOME")));
				}
			} else {
				System.out.println("Não temos lojas cadastrados no banco de dados.");
			}
			return retorno;
		} catch (SQLException e) {
			System.out
					.println("Não foi possível consultar as lojas disponíveis: \n" + retorno + "\n\n" + e.getMessage());
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

	public static Loja save(Loja loja) {

		logger.log(Level.INFO, "Persisting the new coffee {0}.", loja);

		// Colocando SQL numa transaaction para pegar o id da loja inserida
		String sql = "begin INSERT INTO DDD_LOJA(id, NOME) VALUES (sq_loja.nextval,?) returning id into ?;  end;";

		CallableStatement s = null;
		try {
			s = getConnection().prepareCall(sql);
			s.setString(1, loja.getNome());

			s.registerOutParameter(2, java.sql.Types.INTEGER);
			s.executeUpdate();
			loja.setId((long) s.getInt(2));
			return loja;
		} catch (SQLException e) {
			System.out.println("Erro ao salvar a loja o banco de dados: " + e.getMessage());
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

	public static Loja findById(long id) {

		logger.log(Level.INFO, "Finding  Loja by id: " + id);

		Loja retorno = new Loja();

		String sql = "SELECT * from DDD_LOJA where ID=?";

		logger.log(Level.INFO, sql);

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = getConnection().prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {

				while (rs.next()) {

					retorno = new Loja(rs.getLong("ID"), rs.getString("NOME"));
				}

			} else {
				System.out.println("Não temos lojas cadastrados no banco de dados.");
			}
			return retorno;
		} catch (SQLException e) {
			System.out
					.println("Não foi possível consultar as lojas disponíveis: \n" + retorno + "\n\n" + e.getMessage());
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

}
