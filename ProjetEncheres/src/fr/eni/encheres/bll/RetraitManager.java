package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.RetraitDAO;

public class RetraitManager {
	private static RetraitManager instance;
	private RetraitDAO adapter;
	
	 
	public static RetraitManager GetInstace() {
		if(instance == null)
			new RetraitManager();
		return instance;
	}
	protected RetraitDAO getAdapter() {
		if(adapter == null)
			adapter = DAOFactory.getRetraitDAO();
		return adapter;
	}
	
	protected RetraitManager(){
		instance = instance == null ? this : instance;
	}
	
	public void Add(Retrait _ret) throws BllException{
		try {
			getAdapter().Insert(_ret);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
	}
	
	public List<Retrait> get()throws BllException{
		List<Retrait> lst = null;
		try {
			lst = getAdapter().Select();
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return lst;
	}
	
	public Retrait get(int _id)throws BllException{
		Retrait ret = null;
		try {
			ret = getAdapter().SelectById(_id);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return ret;
	}
	
	public void update(Retrait _ret)throws BllException{
		try {
			getAdapter().Update(_ret);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
	}
	
	public void delete(Retrait _ret)throws BllException{
		try {
			getAdapter().Delete(_ret);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
	}
	
	public ArticleVendu getArticle(Retrait _ret)throws BllException{
		ArticleVendu art = null;
		try {
			art = getAdapter().getArticleVendu(_ret);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return art;
	}
}
