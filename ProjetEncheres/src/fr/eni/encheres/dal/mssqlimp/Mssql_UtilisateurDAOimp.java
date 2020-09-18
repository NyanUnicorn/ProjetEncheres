package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private static final String select_by_id=
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

	@Override
	protected void FromDbIdMapper(int _res, Utilisateur _item) throws DalException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Utilisateur FromDbMapper(ResultSet _res) throws DalException {
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
