package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.ArticleVenduDAO;

public class ArticleVenduManager {
	private static ArticleVenduManager instance;
	private ArticleVenduDAO adapter;
	
	 
	public static ArticleVenduManager GetInstace() {
		if(instance == null)
			new ArticleVenduManager();
		return instance;
	}
	protected ArticleVenduDAO getAdapter() {
		if(adapter == null)
			adapter = DAOFactory.getArticleVenduDAO();
		return adapter;
	}
	
	protected ArticleVenduManager(){
		instance = instance == null ? this : instance;
	}
	
	public void Add(ArticleVendu _ench) throws BllException {
		try {
			getAdapter().Insert(_ench);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
	}
	
	public List<ArticleVendu> Get() throws BllException {
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().Select();
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	
	public ArticleVendu Get(int _id0) throws BllException {
		ArticleVendu ench = null;
		try {
			ench = getAdapter().SelectById(_id0);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return ench;
	}
	
	public List<ArticleVendu> GetLast() throws BllException {
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectEnchereEnCours();
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	
	public List<ArticleVendu> GetLast(int _categ, String _name_frag) throws BllException {
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectEnchereEnCours(_categ, _name_frag);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	
	
}
