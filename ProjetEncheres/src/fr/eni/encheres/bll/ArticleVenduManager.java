package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
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
	
	public void Add(ArticleVendu _art) throws BllException {
		try {
			getAdapter().Insert(_art);
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
	
	public Retrait GetLieuRetrait(ArticleVendu _av) throws BllException{
		Retrait retrait = null;
		try {
			retrait = getAdapter().getRetrait(_av);
			_av.setLieuRetrait(retrait);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return retrait;
	}
	
	public Categorie GetCategorie(ArticleVendu _av) throws BllException{
		Categorie categ = null;
		try {
			categ = getAdapter().getCategory(_av);
			_av.setCategorie(categ);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return categ;
	}
	
	public Enchere getBestOffer(ArticleVendu _av) throws BllException{
		Enchere enchere = null;
		try {
			enchere = getAdapter().getBestOffer(_av);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return enchere;
	}
	
}
