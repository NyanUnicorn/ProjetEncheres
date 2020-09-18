package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.specifique.EncheresDAO;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;
import java.util.HashMap;
import java.util.Map;
import org.apache.catalina.tribes.tipis.AbstractReplicatedMap.MapEntry;
import java.util.List;

import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;;

public class UtilisateurManager {
	private static UtilisateurManager instance;
	private static UtilisateurDAO adapter;
	private static UtilisateurDAO utilisateurDAO;
	
	private UtilisateurDAO getAdapter(){
		if(adapter == null) {
			adapter = DAOFactory.getUtilisateurDAO();
		}
		return adapter;
	}


	

	private UtilisateurManager()throws BllException {
}
	

	
	public static UtilisateurManager getInstance() {
		if (instance == null) {
			try {
				instance = new UtilisateurManager();
			} catch (BllException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	
	public Utilisateur getUtilisateur(String identifiant, String password) throws DalException, BllException {
		Utilisateur user = getAdapter().getUtilisateur(identifiant, password);
		if(user != null) {
			return user;
		} else {
			throw new BllException("Identifiant ou mot de passe incorrect");
		}
		
	}
	
	public void creerCompte(HashMap<String,String> formulaire) throws BllException {
		int credit = 0;
		int nbParamVide = 0;
		int nbemail = 0;
		StringBuilder mess = new StringBuilder();
		for(Map.Entry mapEntry : formulaire.entrySet()) {
			if (mapEntry.getValue().equals("")){
				if(!mapEntry.getKey().equals("telephone")) {
				if(nbParamVide > 0) {
					mess.append(", ").append(mapEntry.getKey());
				}else {
					mess.append(mapEntry.getKey());
				}
				nbParamVide++;
			}
			}
			
		}
		/**
		 * On vérifie si le mot de passe est identique à la confirmation
		 */
		if(! formulaire.get("password").equals(formulaire.get("confirmation")))
		{
			throw new BllException("Confirmation du mot de passe différent du mot de passe");
		}else {
			// on vérifie si des formulaires autres que le téléphone sont vides
		if(nbParamVide == 0) {
		Utilisateur user = new Utilisateur(formulaire.get("pseudo"), formulaire.get("nom"), formulaire.get("prenom"), formulaire.get("email"), formulaire.get("telephone"), formulaire.get("codePostal"), formulaire.get("rue"), formulaire.get("ville"), formulaire.get("password"),credit);
		
		try {
			/**
			 * On vérifie si le pseudo ou l'email n'existe pas en base
			 * sinon on lève une exception
			 */
			nbemail = getAdapter().getNbEmail(formulaire.get("email"));
			if(nbemail > 0) {
				throw new BllException(" Adresse email déja utilisée");
				
			}else {
				int nbPseudo = getAdapter().getNbPseudo(formulaire.get("pseudo"));
				if(nbPseudo > 0) {
					throw new BllException("Pseudo déja utilisé");
				}
			}
			getAdapter().Insert(user);
			
		} catch (  DalException e) {
			// TODO Auto-generated catch block
			throw new BllException(e.getMessage());
		}
		
		}else if(nbParamVide == 1 ) {
			throw new BllException("le formulaire " + mess.toString()+ " est vide");
		}else {
			throw new BllException("les formulaires " + mess.toString()+ " sont vides");

		}
		}
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
