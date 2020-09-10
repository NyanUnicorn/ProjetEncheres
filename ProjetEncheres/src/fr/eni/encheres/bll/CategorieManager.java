package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.CategorieDAO;

public class CategorieManager {
	private static CategorieManager instance;
	private CategorieDAO adapter;
	
	 
	public static CategorieManager GetInstace() {
		if(instance == null)
			new CategorieManager();
		return instance;
	}
	protected CategorieDAO getAdapter() {
		if(adapter == null)
			adapter = DAOFactory.getCategorieDAO();
		return adapter;
	}
	
	protected CategorieManager(){
		instance = instance == null ? this : instance;
	}
	
	
	public List<Categorie> Get() throws BllException {
		List<Categorie> lst = null;
		try {
			lst = getAdapter().Select();
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	
	public Categorie Get(int _id0) throws BllException {
		Categorie categ = null;
		try {
			categ = getAdapter().SelectById(_id0);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return categ;
	}
}
