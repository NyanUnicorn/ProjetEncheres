package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.RetraitDAO;

public class Mssql_RetraitDAOimp extends Mssql_CrrudDAOimp<Retrait> implements RetraitDAO {

	private static final String insert = 
			"INSERT INTO [dbo].[RETRAITS]\r\n" + 
			"    ([no_article],[rue],[code_postal],[ville])\r\n" + 
			"VALUES\r\n" + 
			"    (?,?,?,?)";
	private static final String select = 
			"SELECT [no_article]\r\n" + 
			"    ,[rue]\r\n" + 
			"    ,[code_postal]\r\n" + 
			"    ,[ville]\r\n" + 
			"FROM [dbo].[RETRAITS]";
	private static final String select_by_id = 
			"SELECT [no_article]\r\n" + 
			"    ,[rue]\r\n" + 
			"    ,[code_postal]\r\n" + 
			"    ,[ville]\r\n" + 
			"FROM [dbo].[RETRAITS]\r\n" + 
			"WHERE [no_article] = ?";
	private static final String update = 
			"UPDATE [dbo].[RETRAITS]\r\n" + 
			"SET [rue] = ?,[code_postal] = ?,[ville] = ?\r\n" + 
			"WHERE [no_article] = ?";
	private static final String delete = 
			"DELETE FROM [dbo].[RETRAITS]\r\n" + 
			"    WHERE [no_article] = ?";
	private static final String select_article = 
			"SELECT va.[no_article]\r\n" + 
			"      ,va.[nom_article]\r\n" + 
			"      ,va.[date_debut_encheres]\r\n" + 
			"      ,va.[date_fin_encheres]\r\n" + 
			"      ,va.[prix_initial]\r\n" + 
			"      ,va.[prix_vente]\r\n" + 
			"      ,va.[vendeur]\r\n" + 
			"      ,va.[no_categorie]\r\n" + 
			"      ,va.[description]\r\n" + 
			"      ,va.[etat_vente]\r\n" + 
			"      ,va.[acheteur]\r\n" + 
			"FROM [dbo].[VIEW_ARTICLE_VENDUS] va\r\n" + 
			"WHERE va.[no_article] = ?";
	private static final int update_id_index = 4;
	
	@Override
	public ArticleVendu getArticleVendu(Retrait retrait) throws DalException {
		ArticleVendu item = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectById());
			ToDBIdMapper(stm,retrait.getArticleVendu().getNoArticle(),1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				item = Mssql_ArticleVenduDAOimp.S_FromDbMapper(res);
			}
		} catch (SQLException e) {
			throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_SELECT_BY_ID*/);
		}finally {
			if(conn != null) {
				try {
					if(!conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return item;
	}
	
	

	@Override
	public void Insert(Retrait _item) throws DalException {
		Connection conn = getConnection();
		try {
			int i = 1;
			PreparedStatement stm = conn.prepareStatement(getInsert(), Statement.RETURN_GENERATED_KEYS);
			stm.setInt(i++, _item.getArticleVendu().getNoArticle());
			stm.setString(i++, _item.getRue());
			stm.setString(i++, _item.getCodePostale());
			stm.setString(i++, _item.getVille());
			int success = stm.executeUpdate();
			if(success == 1) {
				ResultSet res = stm.getGeneratedKeys();
				if(res.next()) {
					FromDbIdMapper(res.getInt(1), _item);				
				}else {
					throw new DalException(/*DALExceptionCode.DAL_NO_KEY_RETURNED*/);
				}
			}else {
				throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_INSERT*/);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					if(!conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}



	@Override
	protected String getInsert() {
		return insert;
	}

	@Override
	protected String getSelect() {
		return select;
	}

	@Override
	protected String getSelectById() {
		return select_by_id;
	}

	@Override
	protected String getUpdate() {
		return update;
	}

	@Override
	protected String getDelete() {
		return delete;
	}
	
	protected String getSelectArticle() {
		return select_article;
	}

	@Override
	protected int getUpdateIdIndex() {
		return update_id_index;
	}

	@Override
	protected int getItemId(Retrait _item) throws DalException {
		return _item.getArticleVendu().getNoArticle();
	}

	@Override
	protected void FromDbIdMapper(int _res, Retrait _item) throws DalException {
		_item.setArticleVendu(new ArticleVendu(_res));
	}

	@Override
	protected Retrait FromDbMapper(ResultSet _res) throws DalException {
		return S_FromDbMapper(_res);
	}

	@Override
	protected void ToDBMapper(PreparedStatement _stm, Retrait _item) throws DalException {
		int i = 1;
		try {
			_stm.setString(i++, _item.getRue());
			_stm.setString(i++, _item.getCodePostale());
			_stm.setString(i++, _item.getVille());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Retrait _item) throws DalException {
		int i = 1;
		try {
			_stm.setInt(i++, _item.getArticleVendu().getNoArticle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Retrait _item, int start_index) throws DalException {
		try {
			_stm.setInt(start_index, _item.getArticleVendu().getNoArticle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}
	
	public static Retrait S_FromDbMapper(ResultSet _res) throws DalException {
		Retrait retrait = null;
		int no_article = 0;
		String rue = null;
		String cp = null;
		String ville = null;
		try {
			no_article = _res.getInt("no_article");
			rue = _res.getString("rue");
			cp = _res.getString("code_postal");
			ville = _res.getString("ville");
			retrait = new Retrait(no_article, rue,cp,ville);
		}catch (Exception e) {
			throw new DalException(DalException.DAL_ERROR_READING_DATA);
		}
		return retrait;
	}

}
