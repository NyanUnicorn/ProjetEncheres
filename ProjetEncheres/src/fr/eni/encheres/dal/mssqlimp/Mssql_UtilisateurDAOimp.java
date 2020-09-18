package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;
import sun.print.PSPrinterJob.EPSPrinter;

public class Mssql_UtilisateurDAOimp extends Mssql_CrrudDAOimp<Utilisateur> implements UtilisateurDAO{
	private static final String insert=
			"INSERT INTO [dbo].[UTILISATEURS]([pseudo]\r\n" + 
					"      ,[nom]\r\n" + 
					"      ,[prenom]\r\n" + 
					"      ,[email]\r\n" + 
					"      ,[telephone]\r\n" + 
					"      ,[rue]\r\n" + 
					"      ,[code_postal]\r\n" + 
					"      ,[ville]\r\n" + 
					"      ,[mot_de_passe]\r\n" + 
					"      ,[credit]\r\n" + 
					"      ,[administrateur] ) \r\n" + 
					"  VALUES(?,?,?,?,?,?,?,?,?,?,?) \r\n ";
	private static final String select =
			"SELECT [no_utilisateur]\r\n" + 
			"      ,[pseudo]\r\n" + 
			"      ,[nom]\r\n" + 
			"      ,[prenom]\r\n" + 
			"      ,[email]\r\n" + 
			"      ,[telephone]\r\n" + 
			"      ,[rue]\r\n" + 
			"      ,[code_postal]\r\n" + 
			"      ,[ville]\r\n" + 
			"      ,[mot_de_passe]\r\n" + 
			"      ,[credit]\r\n" + 
			"      ,[administrateur]\r\n" + 
			"  FROM [dbo].[UTILISATEURS]";
	private static final String select_by_id =
			"SELECT [no_utilisateur]\r\n" + 
			"      ,[pseudo]\r\n" + 
			"      ,[nom]\r\n" + 
			"      ,[prenom]\r\n" + 
			"      ,[email]\r\n" + 
			"      ,[telephone]\r\n" + 
			"      ,[rue]\r\n" + 
			"      ,[code_postal]\r\n" + 
			"      ,[ville]\r\n" + 
			"      ,[mot_de_passe]\r\n" + 
			"      ,[credit]\r\n" + 
			"      ,[administrateur]\r\n" + 
			"  FROM [dbo].[UTILISATEURS] WHERE [no_utilisateur]= ?";
	private static final String update=
			"";
	private static final String delete=
			"";
	private static final int update_id_index = 1;

	private static final String select_by_pseudo_passwd =
			"SELECT [no_utilisateur]\r\n" + 
			"      ,[pseudo]\r\n" + 
			"      ,[nom]\r\n" + 
			"      ,[prenom]\r\n" + 
			"      ,[email]\r\n" + 
			"      ,[telephone]\r\n" + 
			"      ,[rue]\r\n" + 
			"      ,[code_postal]\r\n" + 
			"      ,[ville]\r\n" + 
			"      ,[mot_de_passe]\r\n" + 
			"      ,[credit]\r\n" + 
			"      ,[administrateur]\r\n" + 
			"  FROM [dbo].[UTILISATEURS] WHERE [pseudo] like ? AND [mot_de_passe] like ?";
	private static final String count_email = 
			"SELECT COUNT(*) FROM [dbo].[UTILISATEURS] WHERE [email] like ?";
	private static final String count_pseudo = 
			"SELECT COUNT(*) FROM [dbo].[UTILISATEURS] WHERE [pseudo] like ?";
	
	private static final String select_encheres_en_cours =
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
			"JOIN [dbo].[ENCHERES] e ON e.[no_article] = va.[no_article]\r\n" + 
			"WHERE va.[date_fin_encheres] > GETDATE()\r\n" + 
			"AND va.[no_categorie] = (SELECT CASE ? WHEN 0 THEN va.[no_categorie] ELSE ? END)\r\n" + 
			"AND va.[nom_article] LIKE  ? \r\n" + 
			"AND e.[no_utilisateur] = ? ";
	private static final String select_encheres_remportees =
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
			"JOIN [dbo].[ENCHERES] e ON e.[no_article] = va.[no_article] AND e.[montant_enchere] = va.[prix_vente]\r\n" + 
			"WHERE va.[date_fin_encheres] < GETDATE()\r\n" + 
			"AND va.[no_categorie] = (SELECT CASE ? WHEN 0 THEN va.[no_categorie] ELSE ? END)\r\n" + 
			"AND va.[nom_article] LIKE  ? \r\n" + 
			"AND e.[no_utilisateur] = ? ";
	private static final String select_vente_en_cours =
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
			"WHERE va.[date_debut_encheres] < GETDATE()\r\n" + 
			"AND va.[date_fin_encheres] > GETDATE()\r\n" + 
			"AND va.[no_categorie] = (SELECT CASE ? WHEN 0 THEN va.[no_categorie] ELSE ? END)\r\n" + 
			"AND va.[nom_article] LIKE  ? \r\n" + 
			"AND va.[no_vendeur] = ? ";
	private static final String select_ventes_non_debutees =
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
			"WHERE va.[date_debut_encheres] > GETDATE()\r\n" + 
			"AND va.[no_categorie] = (SELECT CASE ? WHEN 0 THEN va.[no_categorie] ELSE ? END)\r\n" + 
			"AND va.[nom_article] LIKE ? \r\n" + 
			"AND va.[no_vendeur] = ? ";
	private static final String select_ventes_terminees =
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
			"WHERE va.[date_fin_encheres] < GETDATE()\r\n" + 
			"AND va.[no_categorie] = (SELECT CASE ? WHEN 0 THEN va.[no_categorie] ELSE ? END)\r\n" + 
			"AND va.[nom_article] LIKE ? \r\n" + 
			"AND va.[no_vendeur] = ? ";


	@Override
	public void Insert(Utilisateur item) throws DalException {
		Connection con = getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(insert);
			ToDBIdMapper(stm, item);
			stm.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw new DalException();
			
		}finally {
			if(con != null) {
				try {
					if(! con.isClosed()) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public List<Utilisateur> Select() throws DalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur SelectById(int _id) throws DalException {
		Utilisateur user = new Utilisateur();
		Connection con = getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(getSelectById());
			ToDBIdMapper(stm, _id, 1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				user = FromDbMapper(res);
			}
		}catch(SQLException e) {
			throw new DalException();
		}finally {
			if(con != null) {
				try {
					if(! con.isClosed()) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return user;
	}
	
	@Override
	public List<ArticleVendu> selectEncheresEnCours(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom,
			Utilisateur user) throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectEncheresEnCours());
			stm.setInt(1, noCateg);
			stm.setInt(2, noCateg);
			stm.setString(3, "%"+fragmenNom+"%");
			stm.setInt(4, user.getNoUtilisateur() );
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
				do {
					lst.add(Mssql_ArticleVenduDAOimp.S_FromDbMapper(res));					
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
	public List<ArticleVendu> selectEncheresRemportees(List<ArticleVendu> articleEnCours, int noCateg,
			String fragmenNom, Utilisateur user) throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectEncheresRemportees());
			stm.setInt(1, noCateg);
			stm.setInt(2, noCateg);
			stm.setString(3, "%"+fragmenNom+"%");
			stm.setInt(4, user.getNoUtilisateur() );
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
				do {
					lst.add(Mssql_ArticleVenduDAOimp.S_FromDbMapper(res));					
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
	public List<ArticleVendu> selectVenteEnCours(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom,
			Utilisateur user) throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectVenteEnCours());
			stm.setInt(1, noCateg);
			stm.setInt(2, noCateg);
			stm.setString(3, "%"+fragmenNom+"%");
			stm.setInt(4, user.getNoUtilisateur() );
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
				do {
					lst.add(Mssql_ArticleVenduDAOimp.S_FromDbMapper(res));					
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
	public List<ArticleVendu> selectVentesNonDebutees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom,
			Utilisateur user) throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectVentesNonDebutees() );
			stm.setInt(1, noCateg);
			stm.setInt(2, noCateg);
			stm.setString(3, "%"+fragmenNom+"%");
			stm.setInt(4, user.getNoUtilisateur() );
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
				do {
					lst.add(Mssql_ArticleVenduDAOimp.S_FromDbMapper(res));					
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
	public List<ArticleVendu> selectVentesTerminees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom,
			Utilisateur user) throws DalException {
		List<ArticleVendu> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectVentesTerminees() );
			stm.setInt(1, noCateg);
			stm.setInt(2, noCateg);
			stm.setString(3, "%"+fragmenNom+"%");
			stm.setInt(4, user.getNoUtilisateur() );
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<ArticleVendu>();
				do {
					lst.add(Mssql_ArticleVenduDAOimp.S_FromDbMapper(res));					
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
	public void Update(Utilisateur _item) throws DalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(Utilisateur item) throws DalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void VendreArticle(Utilisateur _user, ArticleVendu _art) throws DalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ArticleVendu> GetArticlesVendu(Utilisateur _user) throws DalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVendu> GetArticlesAchat(Utilisateur _user) throws DalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> GetEcheres(Utilisateur _user) throws DalException {
		// TODO Auto-generated method stub
		return null;
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
	
	protected String getSeleceByPseudoAndPassword() {
		return select_by_pseudo_passwd;
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
	protected int getItemId(Utilisateur _item) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected String getSelectEncheresEnCours() {
		return select_encheres_en_cours;
	}

	protected String getSelectEncheresRemportees() {
		return select_encheres_remportees;
	}

	protected String getSelectVenteEnCours() {
		return select_vente_en_cours;
	}

	protected String getSelectVentesNonDebutees() {
		return select_ventes_non_debutees;
	}

	protected String getSelectVentesTerminees() {
		return select_ventes_terminees;
	}
	
	@Override
	protected void FromDbIdMapper(int _res, Utilisateur _item) throws DalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Utilisateur FromDbMapper(ResultSet _res) throws DalException {
		return s_FromDbMapper(_res);
	}

	@Override
	protected void ToDBMapper(PreparedStatement _stm, Utilisateur _item) throws DalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, int _id, int index) throws DalException {
		try {
			_stm.setInt(index, _id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DalException();
		}
		
	}
	
	protected void toDBMapper(PreparedStatement _stm, String identifiant, String password,int index) throws DalException {
		try {
			_stm.setString(index++, identifiant);
			_stm.setString(index++, password);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DalException();
		}
	}

	@Override
	public Utilisateur getUtilisateur(String identifiant, String password) throws DalException {
		Utilisateur user = null;
		Connection con = getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(getSeleceByPseudoAndPassword());
			toDBMapper(stm, identifiant, password, 1);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				user = FromDbMapper(res);
			}
		}catch(SQLException e) {
			throw new DalException();
		}finally {
			if(con != null) {
				try {
					if(! con.isClosed()) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return user;
	}
	
	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Utilisateur _item) throws DalException {
		int index = 1;
		try {
			_stm.setString(index++, _item.getPseudo());
			_stm.setString(index++, _item.getNom());
			_stm.setString(index++, _item.getPrenom());
			_stm.setString(index++,_item.getEmail());
			_stm.setString(index++, _item.getTelephone());
			_stm.setString(index++, _item.getRue());
			_stm.setString(index++, _item.getCodePostal());
			_stm.setString(index++, _item.getVille());
			_stm.setString(index++, _item.getMotDePasse());
			_stm.setInt(index++, _item.getCredit());
			_stm.setBoolean(index++, false);
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DalException();
		}		
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Utilisateur _item, int start_index) throws DalException {
		// TODO Auto-generated method stub
		
	}
	
	public Utilisateur s_FromDbMapper(ResultSet _res) throws DalException {
		Utilisateur userFromMapper = new Utilisateur();
		try {
			userFromMapper.setNoUtilisateur(_res.getInt("no_utilisateur"));
			userFromMapper.setPseudo(_res.getString("pseudo"));
			userFromMapper.setNom(_res.getString("nom"));
			userFromMapper.setPrenom(_res.getString("prenom"));
			userFromMapper.setEmail(_res.getString("email"));
			userFromMapper.setTelephone(_res.getString("telephone"));
			userFromMapper.setRue(_res.getString("rue"));
			userFromMapper.setCodePostal(_res.getString("code_postal"));
			userFromMapper.setVille(_res.getString("ville"));
			userFromMapper.setMotDePasse(_res.getString("mot_de_passe"));
			userFromMapper.setCredit(_res.getInt("credit"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DalException();
		}
		return userFromMapper;
	}

	

	@Override
	public int getNbEmail(String email) throws DalException {
		int nbEmail = 0 ;
		Connection con = getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(count_email);
			stm.setString(1, email);
			ResultSet rs = stm.executeQuery();
					if(rs.next()) {
						nbEmail = rs.getInt(1);
					}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbEmail;
	}

	@Override
	public int getNbPseudo(String pseudo) throws DalException {
		int nbPseudo = 0 ;
		Connection con = getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(count_pseudo);
			stm.setString(1, pseudo);
			ResultSet rs = stm.executeQuery();
					if(rs.next()) {
						nbPseudo = rs.getInt(1);
					}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbPseudo;
	}

}
