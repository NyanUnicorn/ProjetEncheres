package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bo.Utilisateur;;

public class UtilisateurManager {
	private static UtilisateurDAO utilisateurDAO;
	
	
	public UtilisateurManager()throws BllException {
		utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public Utilisateur getUtilisateur(String identifiant, String password) throws DalException {
		Utilisateur user = DAOFactory.getUtilisateurDAO().getUtilisateur(identifiant, password);
		
		
		return user;
	}
}
