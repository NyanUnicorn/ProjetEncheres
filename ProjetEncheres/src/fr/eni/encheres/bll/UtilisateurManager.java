package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bo.Utilisateur;;

public class UtilisateurManager {
	private static UtilisateurDAO utilisateurDAO;
	
	
	public UtilisateurManager()throws BllException {
		utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public Utilisateur getUtilisateur(String identifiant, String password) {
		Utilisateur user = null;
		
		return user;
	}
}
