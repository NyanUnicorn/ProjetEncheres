package fr.eni.encheres.dal.mssqlimp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import fr.eni.encheres.dal.DalException;

public abstract class Mssql_DAOimp<T> {

	protected abstract void FromDbIdMapper(int _res,  T _item) throws DalException ;
	protected abstract T FromDbMapper(ResultSet _res) throws DalException ;
	protected abstract void ToDBMapper(PreparedStatement _stm, T _item) throws DalException ;
	protected abstract void ToDBIdMapper(PreparedStatement _stm, int _id,int index) throws DalException ;
	protected abstract void ToDBIdMapper(PreparedStatement _stm, T _item) throws DalException ;
	protected abstract void ToDBIdMapper(PreparedStatement _stm, T _item, int start_index) throws DalException ;
	
	protected static Connection getConnection() throws DalException {
		Connection cnx = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
			cnx = dataSource.getConnection();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DalException(DalException.DAL_CANNOT_OPEN_CONECTION);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new DalException(DalException.DAL_NAMING_EXCEPTION);
		}
		return cnx;
	}
}
