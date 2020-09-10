package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.ArticleVenduDAO;

public class Mssql_ArticleVenduDAOimp extends Mssql_CrrudDAOimp<ArticleVendu> implements ArticleVenduDAO{

	private static final String insert =
			"INSERT INTO [dbo].[ARTICLES_VENDUS]\r\n" + 
			"    ([nom_article],[date_debut_encheres],[date_fin_encheres],[prix_initial],[prix_vente],[no_utilisateur],[no_categorie],[description])\r\n" + 
			"VALUES\r\n" + 
			"    (?,?,?,?,?,?,?,?)";
	private static final String select =
			"SELECT va.[no_article]\r\n" + 
			"      ,va.[nom_article]\r\n" + 
			"      ,va.[date_debut_encheres]\r\n" + 
			"      ,va.[date_fin_encheres]\r\n" + 
			"      ,va.[prix_initial]\r\n" + 
			"      ,va.[prix_vente]\r\n" + 
			"      ,va.[no_vendeur]\r\n" + 
			"      ,va.[no_categorie]\r\n" + 
			"      ,va.[description]\r\n" + 
			"      ,va.[etat_vente]\r\n" + 
			"      ,va.[no_acheteur]\r\n" + 
			"FROM [dbo].[VIEW_ARTICLE_VENDUS] va";
	private static final String select_by_id =
			"SELECT va.[no_article]\r\n" + 
			"      ,va.[nom_article]\r\n" + 
			"      ,va.[date_debut_encheres]\r\n" + 
			"      ,va.[date_fin_encheres]\r\n" + 
			"      ,va.[prix_initial]\r\n" + 
			"      ,va.[prix_vente]\r\n" + 
			"      ,va.[no_vendeur]\r\n" + 
			"      ,va.[no_categorie]\r\n" + 
			"      ,va.[description]\r\n" + 
			"      ,va.[etat_vente]\r\n" + 
			"      ,va.[no_acheteur]\r\n" + 
			"FROM [dbo].[VIEW_ARTICLE_VENDUS] va\r\n" + 
			"WHERE va.[no_article] = ?";
	private static final String update =
			"UPDATE [dbo].[ARTICLES_VENDUS] va\r\n" + 
			"SET va.[nom_article] = ?\r\n" + 
			"      ,va.[date_debut_encheres] = ?\r\n" + 
			"      ,va.[date_fin_encheres] = ?\r\n" + 
			"      ,va.[prix_initial] = ?\r\n" + 
			"      ,va.[prix_vente] = ?\r\n" + 
			"      ,va.[no_utilisateur] = ?\r\n" + 
			"      ,va.[no_categorie] = ?\r\n" + 
			"      ,va.[description] = ?\r\n" + 
			"WHERE va.[no_article] = ?";
	private static final String delete =
			"DELETE FROM [dbo].[ARTICLES_VENDUS] va\r\n" + 
			"    WHERE va.[no_article] = ?";
	private static final String select_category = 
			"SELECT [no_categorie]\r\n" + 
			"      ,[libelle]\r\n" + 
			"FROM [dbo].[CATEGORIES]]\r\n" +
			"WHERE [no_categorie] = ? ";
	private static final String select_en_cours =
			"SELECT va.[no_article]\r\n" + 
			"    ,va.[nom_article]\r\n" + 
			"    ,va.[date_debut_encheres]\r\n" + 
			"    ,va.[date_fin_encheres]\r\n" + 
			"    ,va.[prix_initial]\r\n" + 
			"    ,va.[prix_vente]\r\n" + 
			"    ,va.[no_vendeur]\r\n" + 
			"    ,va.[no_categorie]\r\n" + 
			"    ,va.[description]\r\n" + 
			"    ,va.[etat_vente]\r\n" + 
			"    ,va.[no_acheteur]\r\n" + 
			"FROM [dbo].[VIEW_ARTICLE_VENDUS] va\r\n" + 
			"WHERE va.[date_fin_encheres] > GETDATE()";
	private static final String select_en_cours_filtre =
			"SELECT va.[no_article]\r\n" + 
			"    ,va.[nom_article]\r\n" + 
			"    ,va.[date_debut_encheres]\r\n" + 
			"    ,va.[date_fin_encheres]\r\n" + 
			"    ,va.[prix_initial]\r\n" + 
			"    ,va.[prix_vente]\r\n" + 
			"    ,va.[no_vendeur]\r\n" + 
			"    ,va.[no_categorie]\r\n" + 
			"    ,va.[description]\r\n" + 
			"    ,va.[etat_vente]\r\n" + 
			"    ,va.[no_acheteur]\r\n" + 
			"FROM [dbo].[VIEW_ARTICLE_VENDUS] va\r\n" + 
			"WHERE va.[date_fin_encheres] > GETDATE()\r\n" + 
			"AND va.[no_categorie] = (SELECT CASE ? WHEN 0 THEN va.[no_categorie] ELSE ?)\r\n" + 
			"AND va.[nom_article] =  ? ";
	private static final int update_id_index = 9;
	
	
	@Override
	public Categorie getCategory(ArticleVendu _art) throws DalException {
		Categorie item = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectById());
			ToDBIdMapper(stm,_art.getCategorie().getNoCategorie(),1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				item = Mssql_CategorieDAOimp.S_FromDbMapper(res);
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
		_art.setCategorie(item);
		return item;
	}

	@Override
	public Retrait getRetrait(ArticleVendu _art) throws DalException {
		Retrait item = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectById());
			ToDBIdMapper(stm,_art.getNoArticle(),1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				item = Mssql_RetraitDAOimp.S_FromDbMapper(res);
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
	public List<ArticleVendu> selectEnchereEnCours() throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectEnCours());
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
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

	@Override
	public List<ArticleVendu> selectEnchereEnCours(int _categ, String _name_frag) throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectEnCoursFiltre());
			stm.setInt(1, _categ);
			stm.setInt(2, _categ);
			stm.setString(3, "%"+_name_frag+"%");
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
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

	private String getSelectEnCoursFiltre() {
		return select_en_cours_filtre;
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

	@Override
	protected int getUpdateIdIndex() {
		return update_id_index;
	}

	@Override
	protected int getItemId(ArticleVendu _item) throws DalException {
		return _item.getNoArticle();
	}

	@Override
	protected void FromDbIdMapper(int _res, ArticleVendu _item) throws DalException {
		_item.setNoArticle(_res);
	}

	@Override
	protected ArticleVendu FromDbMapper(ResultSet _res) throws DalException {
		return S_FromDbMapper(_res);
	}

	@Override
	protected void ToDBMapper(PreparedStatement _stm, ArticleVendu _item) throws DalException {
		int i = 1;
		try {
			_stm.setString(i++, _item.getNomArticle());
			_stm.setDate(i++, _item.getDateDebutEncheres());
			_stm.setDate(i++, _item.getDateFinEncheres());
			_stm.setInt(i++, _item.getMiseAPrix());
			_stm.setInt(i++, _item.getPrixVente());
			_stm.setInt(i++, _item.getVendeur().getNoUtilisateur());
			_stm.setInt(i++, _item.getCategorie().getNoCategorie());
			_stm.setString(i++, _item.getDescritpion());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, ArticleVendu _item) throws DalException {
		int i = 1;
		try {
			_stm.setInt(i++, _item.getNoArticle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, ArticleVendu _item, int start_index) throws DalException {
		try {
			_stm.setInt(start_index, _item.getNoArticle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}
	
	public static ArticleVendu S_FromDbMapper(ResultSet _res) throws DalException {
		ArticleVendu art = null;
		int noArticle = 0;
		String nomArticle = null;
		String descritpion = null;
		Date dateDebutEncheres = null;
		Date dateFinEncheres = null;
		int miseAPrix = 0;
		int prixVente = 0;
		EtatVente etatVente = null;
		Utilisateur vendeur = null;
		Utilisateur acheteur = null;
		int categorie = 0;
		try {
			noArticle = _res.getInt("no_article");
			nomArticle = _res.getString("nom_article");
			descritpion = _res.getString("description");
			dateDebutEncheres = _res.getDate("date_debut_encheres");
			dateFinEncheres = _res.getDate("date_fin_encheres");
			miseAPrix = _res.getInt("prix_initial");
			prixVente = _res.getInt("prix_vente");
			etatVente = EtatVente.values()[_res.getInt("etat_vente")];
			try {
				vendeur = new Utilisateur(_res.getInt("no_vendeur"));				
			}catch (SQLException e) {
			}
			try {
				acheteur = new Utilisateur(_res.getInt("no_acheteur"));				
			}catch (SQLException e) {
			}
			categorie = _res.getInt("no_categorie");
			art = new ArticleVendu(noArticle,nomArticle,descritpion,dateDebutEncheres,dateFinEncheres,miseAPrix,prixVente,etatVente,vendeur,acheteur,categorie);
		}catch (SQLException e) {
			throw new DalException(DalException.DAL_ERROR_READING_DATA);
		}
		return art;
	}

}
