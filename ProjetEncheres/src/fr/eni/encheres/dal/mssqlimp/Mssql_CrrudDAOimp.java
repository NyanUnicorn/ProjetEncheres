package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.general.CrrudDAO;

public abstract class Mssql_CrrudDAOimp<T> extends Mssql_DAOimp<T> implements CrrudDAO<T> {

	protected abstract String getInsert();

	protected abstract String getSelect();

	protected abstract String getSelectById();

	protected abstract String getUpdate();

	protected abstract String getDelete();

	protected abstract int getUpdateIdIndex();

	protected abstract int getItemId(T _item) throws DalException;
	
	
	@Override
	public void Insert(T _item) throws DalException {
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getInsert(), Statement.RETURN_GENERATED_KEYS);
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
	public T SelectById(int _id) throws DalException {
		T item = null;
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
	@Override
	public List<T> Select() throws DalException {
		List<T> lst = null;
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getSelect());
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				lst = new ArrayList<T>();
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
	@Override
	public void Update(T _item) throws DalException {
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getUpdate());
			ToDBMapper(stm, _item);
			ToDBIdMapper(stm, getItemId(_item), getUpdateIdIndex());
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
	@Override
	public void Delete(T _item) throws DalException {
		Connection conn = getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(getDelete());
			ToDBIdMapper(stm, getItemId(_item), 1);
			int success = stm.executeUpdate();
			if(success != 1) {
				throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_DELETE*/);
			}
		}catch(Exception e) {
			throw new DalException(/*DALExceptionCode.DAL_COULD_NOT_DELETE*/);
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
}
