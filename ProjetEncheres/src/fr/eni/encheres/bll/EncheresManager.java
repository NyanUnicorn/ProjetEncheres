package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.EncheresDAO;

public class EncheresManager {
	private static EncheresManager instance;
	private EncheresDAO adapter;
	
	 
	public static EncheresManager GetInstace() {
		if(instance == null)
			new EncheresManager();
		return instance;
	}
	protected EncheresDAO getAdapter() {
		if(adapter == null)
			adapter = DAOFactory.getEncheresDAO();
		return adapter;
	}
	
	protected EncheresManager(){
		instance = instance == null ? this : instance;
	}
	
	public void Add(Enchere _ench) throws BllException {
		try {
			getAdapter().Insert(_ench);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
	}
	
	public List<Enchere> Get() throws BllException {
		List<Enchere> lst = null;
		try {
			lst = getAdapter().Select();
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	
	public Enchere Get(int _id0, int _id1) throws BllException {
		Enchere ench = null;
		try {
			ench = getAdapter().SelectByIdPair(_id0, _id1);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return ench;
	}
	/*
	public List<Enchere> GetLast() throws BllException{
		List<Enchere> lst = null;
		try {
			lst = getAdapter().selectEnchereEnCours();
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	
	public List<Enchere> GetLast(int _categ, String _name_frag) throws BllException{
		List<Enchere> lst = null;
		try {
			lst = getAdapter().selectEnchereEnCours(_categ, _name_frag);
		}catch (DalException dale) {
			throw new BllException(dale.getMessage());
		}
		return lst;
	}
	*/
}
