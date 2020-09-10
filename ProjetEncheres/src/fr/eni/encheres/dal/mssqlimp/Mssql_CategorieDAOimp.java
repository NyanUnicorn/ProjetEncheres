package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.CategorieDAO;

public class Mssql_CategorieDAOimp extends Mssql_DAOimp<Categorie> implements CategorieDAO {

	private static final String select = 
			"SELECT [no_categorie]\r\n" + 
			"      ,[libelle]\r\n" + 
			"FROM [dbo].[CATEGORIES]";
	private static final String select_by_id = 
			"SELECT [no_categorie]\r\n" + 
			"      ,[libelle]\r\n" + 
			"FROM [dbo].[CATEGORIES]]\r\n" +
			"WHERE [no_categorie] = ? ";
	
	
	public Categorie SelectById(int _id) throws DalException {
		Categorie item = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelectById());
			ToDBIdMapper(stm,_id,1);
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
	

	public List<Categorie> Select() throws DalException {
		List<Categorie> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelect());
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<Categorie>();
				do {
					lst.add(FromDbMapper(res));					
				}while(res.next());
			}
		} catch (SQLException e) {
			throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_SELECT*/);
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
	
	private String getSelectById() {
		return select_by_id;
	}

	private String getSelect() {
		return select;
	}

	@Override
	public List<ArticleVendu> GetArticlesVendu(Categorie _cat) throws DalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void FromDbIdMapper(int _res, Categorie _item) throws DalException {
		_item.setNoCategorie(_res);
	}

	@Override
	protected Categorie FromDbMapper(ResultSet _res) throws DalException {
		return S_FromDbMapper(_res);
	}

	@Override
	protected void ToDBMapper(PreparedStatement _stm, Categorie _item) throws DalException {
		int i = 1;
		try {
			_stm.setString(i++, _item.getLibelle());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Categorie _item) throws DalException {
		int i = 1;
		try {
			_stm.setInt(i++, _item.getNoCategorie());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}

	@Override
	protected void ToDBIdMapper(PreparedStatement _stm, Categorie _item, int start_index) throws DalException {
		try {
			_stm.setInt(start_index, _item.getNoCategorie());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_ERROR_WRITING_DATA);
		}
	}
	
	public static Categorie S_FromDbMapper(ResultSet _res) throws DalException {
		Categorie categ = null;
		int no_categorie = 0;
		String libelle = "";
		try {
			no_categorie = _res.getInt("no_categorie");
			libelle = _res.getString("libelle");
			categ = new Categorie(no_categorie, libelle);
		}catch (Exception e) {
			throw new DalException(DalException.DAL_ERROR_READING_DATA);
		}
		return categ;
	}
}
