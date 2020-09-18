package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;

import java.util.List;

import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;;

public class UtilisateurManager {
	private static UtilisateurManager instance;
	private UtilisateurDAO adapter;
	private static UtilisateurDAO utilisateurDAO;
	
	private UtilisateurDAO getAdapter(){
		if(adapter == null) {
			adapter = DAOFactory.getUtilisateurDAO();
		}
		return adapter;
	}

	public static UtilisateurManager getInstance() {
		if(instance == null) {
			new UtilisateurManager();
		}
		return instance;
	}
	
	
	public UtilisateurManager(){
		utilisateurDAO = DAOFactory.getUtilisateurDAO();
		instance = instance == null ? this : instance;
	}
	
	public Utilisateur getUtilisateur(String identifiant, String password) throws DalException {
		Utilisateur user = DAOFactory.getUtilisateurDAO().getUtilisateur(identifiant, password);
		
		
		return user;
	}
	
	public List<ArticleVendu> getEncheresEnCours(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user) throws BllException{
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectEncheresEnCours(articleEnCours, noCateg, fragmenNom, user);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return lst;
	}

	public Utilisateur getUtilisateur(int id) throws BllException{
		Utilisateur usr = null;
		try {
			usr = getAdapter().SelectById(id);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return usr;
	}
	
	public List<ArticleVendu> getEncheresRemportees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user) throws BllException{
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectEncheresRemportees(articleEnCours, noCateg, fragmenNom, user);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return lst;
	}
	
	public List<ArticleVendu> getVentesEnCours(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user) throws BllException{
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectVenteEnCours(articleEnCours, noCateg, fragmenNom, user);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return lst;
	}
	
	public List<ArticleVendu> getVentesNonDebutees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user) throws BllException{
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectVentesNonDebutees(articleEnCours, noCateg, fragmenNom, user);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return lst;
	}
	
	public List<ArticleVendu> getVentesTerminees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user) throws BllException{
		List<ArticleVendu> lst = null;
		try {
			lst = getAdapter().selectVentesTerminees(articleEnCours, noCateg, fragmenNom, user);
		} catch (DalException e) {
			e.printStackTrace();
			throw new BllException(e.getMessage());
		}
		return lst;
	}
	
}
