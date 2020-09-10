package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.EncheresDAO;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;

public class Mssql_EncheresDAOimp extends Mssql_CrrudDAOimp<Enchere> implements EncheresDAO {

	private static final String insert = 
			"INSERT INTO [dbo].[ENCHERES]\r\n" + 
			"           ([no_utilisateur]\r\n" + 
			"           ,[no_article]\r\n" + 
			"           ,[date_enchere]\r\n" + 
			"           ,[montant_enchere])\r\n" + 
			"     VALUES\r\n" + 
			"           (?\r\n" + 
			"           ,?\r\n" + 
			"           ,?\r\n" + 
			"           ,?)";
	private static final String select = 
			"SELECT [no_utilisateur]\r\n" + 
					"      ,[no_article]\r\n" + 
					"      ,[date_enchere]\r\n" + 
					"      ,[montant_enchere]\r\n" + 
					"  FROM [dbo].[ENCHERES]";
	private static final String select_by_id = 
			"SELECT [no_utilisateur]\r\n" + 
					"      ,[no_article]\r\n" + 
					"      ,[date_enchere]\r\n" + 
					"      ,[montant_enchere]\r\n" + 
					"  FROM [dbo].[ENCHERES]\r\n" +
					" WHERE [no_utilisateur] = ? AND [no_article] = ? ";
	private static final String update = 
			"UPDATE [dbo].[ENCHERES]\r\n" + 
			"   SET [date_enchere] = ?\r\n" + 
			"      ,[montant_enchere] = ?\r\n" + 
			" WHERE [no_utilisateur] = ? AND [no_article] = ?";
	private static final String delete = 
			"DELETE FROM [dbo].[ENCHERES]\r\n" + 
			"      WHERE ?";
	private static final String select_user = 
			"SELECT [no_utilisateur]\r\n" + 
			"      ,u.[pseudo]\r\n" + 
			"      ,u.[nom]\r\n" + 
			"      ,u.[prenom]\r\n" + 
			"      ,u.[email]\r\n" + 
			"      ,u.[telephone]\r\n" + 
			"      ,u.[rue]\r\n" + 
			"      ,u.[code_postal]\r\n" + 
			"      ,u.[ville]\r\n" + 
			"      ,u.[mot_de_passe]\r\n" + 
			"      ,u.[credit]\r\n" + 
			"      ,u.[administrateur]\r\n" + 
			"FROM [dbo].[UTILISATEURS] u\r\n" +
			"WHERE e.[no_utilisateur] = ? ";
	private static final String select_article = 
			"SELECT a.[no_article]\r\n" + 
			"      ,a.[nom_article]\r\n" + 
			"      ,a.[description]\r\n" + 
			"      ,a.[date_debut_encheres]\r\n" + 
			"      ,a.[date_fin_encheres]\r\n" + 
			"      ,a.[prix_initial]\r\n" + 
			"      ,a.[prix_vente]\r\n" + 
			"      ,a.[no_utilisateur]\r\n" + 
			"      ,a.[no_categorie]\r\n" + 
			"FROM [dbo].[ARTICLES_VENDUS] a\r\n" + 
			"WHERE e.[no_article] = ? ";
	private static final int update_id_index = 3;
	/*
	private static final String select_en_cours =
			"SELECT [no_utilisateur]\r\n" + 
			"      ,[no_article]\r\n" + 
			"      ,[date_enchere]\r\n" + 
			"      ,[montant_enchere]\r\n" + 
			"FROM [dbo].[ENCHERES]\r\n" + 
			"WHERE [date_enchere] < GETDATE()";
	private static final String select_en_cours_filtre =
			"SELECT e.[no_utilisateur]\r\n" + 
			"      ,e.[no_article]\r\n" + 
			"      ,e.[date_enchere]\r\n" + 
			"      ,e.[montant_enchere]\r\n" + 
			"FROM [dbo].[ENCHERES] e\r\n" + 
			"LEFT JOIN [dbo].[ARTICLES_VENDUS] a ON a.[no_article] = e.[no_article]\r\n" + 
			"WHERE [date_enchere] < GETDATE()\r\n" + 
			"    AND a.[no_categorie] = (SELECT CASE ? WHEN 0 THEN a.[no_categorie] ELSE ?)\r\n" + 
			"    AND a.[nom_article] =  ? ";
	/*
	 * 
	 * 
SELECT e.[no_utilisateur]
      ,e.[no_article]
      ,e.[date_enchere]
      ,e.[montant_enchere]
FROM [dbo].[ENCHERES] e
LEFT JOIN [dbo].[ARTICLES_VENDUS] a ON a.[no_article] = e.[no_article]
WHERE [date_enchere] < GETDATE()
    AND a.[no_categorie] = (SELECT CASE ? WHEN 0 THEN a.[no_categorie] ELSE ?)
    AND a.[nom_article] = '%' + ? + '%'
	 */
	
	
	@Override
	public Utilisateur getUtilisateur(Enchere enchere) throws DalException {
		Utilisateur item = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectUtilisateur());
			ToDBIdMapper(stm, enchere.getUtilisateur().getNoUtilisateur(), 1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
//				item = FromDbMapper(res);
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
	public ArticleVendu getArticle(Enchere enchere) throws DalException {
		ArticleVendu item = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectArticle());
			ToDBIdMapper(stm, enchere.getUtilisateur().getNoUtilisateur(), 1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
//				item = FromDbMapper(res);
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
	public void Insert(Enchere _item) throws DalException {
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getInsert(), Statement.RETURN_GENERATED_KEYS);
			ToDBIdMapper(stm, _item);
			ToDBMapper(stm, _item);
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
	public Enchere SelectByIdPair(int _id0, int _id1) throws DalException {
		Enchere item = new Enchere();
		item.setUtilisateur(new Utilisateur(_id0)); 
		item.setArticleVendu(new ArticleVendu(_id1));
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectById());
			ToDBIdMapper(stm,item);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				item = FromDbMapper(res);
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
	public void Update(Enchere _item) throws DalException {
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getUpdate());
			ToDBMapper(stm, _item);
			ToDBIdMapper(stm, _item, 3);
			int success = stm.executeUpdate();
			if(success != 1) {
				throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_UPDATE*/);
			}
		}catch(Exception e) {
			throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_UPDATE*/);
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
	
/*
	@Override
	public List<Enchere> selectEnchereEnCours() throws DalException {
		List<Enchere> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectEnCours());
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<Enchere>();
				do {
					lst.add(FromDbMapper(res));					
				}while(res.next());
			}
		} catch (SQLException e) {
			throw new DalException();
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
		return lst;
	}


	@Override
	public List<Enchere> selectEnchereEnCours(int _categ, String _name_frag) throws DalException {
		List<Enchere> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectEnCoursFiltre());
			stm.setInt(1, _categ);
			stm.setInt(2, _categ);
			stm.setString(3, "%"+_name_frag+"%");
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<Enchere>();
				do {
					lst.add(FromDbMapper(res));					
				}while(res.next());
			}
		} catch (SQLException e) {
			throw new DalException();
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
		return lst;
	}

	private String getSelectEnCours() {
		return select_en_cours;
	}
	
	private String getSelectEnCoursFiltre() {
		return select_en_cours_filtre;
	}
*/
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

	@Override
	protected int getUpdateIdIndex() {
		return update_id_index;
	}
	
	protected String getSelectUtilisateur() {
		return select_user;
	}
	
	protected String getSelectArticle() {
		return select_article;
	}

	@Override
	protected int getItemId(Enchere _item) throws DalException {
		throw new DalException(DalException.DAL_NO_IMPLEMENTATION);
	}

	@Override
	protected void FromDbIdMapper(int _res, Enchere _item) throws DalException {
		throw new DalException(DalException.DAL_NO_IMPLEMENTATION);
	}

	@Override
	protected Enchere FromDbMapper(ResultSet _res) throws DalException {
		return S_FromDbMapper(_res);
	}

	@Override
	protected void ToDBMapper(PreparedStatement _stm, Enchere _item) throws DalException {
		int i = 1;
		try {
			_stm.setDate(i++, _item.getDateEnchere());
			_stm.setInt(i++, _item.getMontantEnchere());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, int _id, int index) throws DalException {
		throw new DalException(DalException.DAL_NO_IMPLEMENTATION);
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Enchere _item) throws DalException {
		int i = 1;
		try {
			_stm.setInt(i++, _item.getUtilisateur().getNoUtilisateur());
			_stm.setInt(i++, _item.getArticleVendu().getNoArticle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Enchere _item, int start_index) throws DalException {
		int i = start_index;
		try {
			_stm.setInt(i++, _item.getUtilisateur().getNoUtilisateur());
			_stm.setInt(i++, _item.getArticleVendu().getNoArticle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}
	
	protected Enchere S_FromDbMapper(ResultSet _res) throws DalException {
		Enchere ench = null;
		int userId = 0;
		int articleId = 0;
		Date date;
		int montant = 0;
		try {
			userId = _res.getInt("no_utilisateur");
			articleId = _res.getInt("no_article");
			date = _res.getDate("date_enchere");
			montant = _res.getInt("montant_enchere");
			ench = new Enchere(userId, articleId, date, montant);
		}catch (Exception e) {
			throw new DalException(DalException.DAL_ERROR_READING_DATA);
		}
		return ench;
	}


}
