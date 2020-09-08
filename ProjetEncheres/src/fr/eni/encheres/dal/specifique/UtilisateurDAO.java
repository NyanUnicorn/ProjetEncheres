package fr.eni.encheres.dal.specifique;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.general.CrrudDAO;

public interface UtilisateurDAO extends CrrudDAO<Utilisateur> {

	void VendreArticle(Utilisateur _user, ArticleVendu _art) throws DalException;
	List<ArticleVendu> GetArticlesVendu(Utilisateur _user) throws DalException;
	
	List<ArticleVendu> GetArticlesAchat(Utilisateur _user) throws DalException;
	
	List<Enchere> GetEcheres(Utilisateur _user) throws DalException;
}
