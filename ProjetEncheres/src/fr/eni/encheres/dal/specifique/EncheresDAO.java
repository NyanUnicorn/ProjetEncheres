package fr.eni.encheres.dal.specifique;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.general.CrrudDAO;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.bo.ArticleVendu;

public interface EncheresDAO extends CrrudDAO<Enchere> {
	
	Utilisateur getUtilisateur(Enchere enchere)throws DalException;
	ArticleVendu getArticle(Enchere enchere) throws DalException;

}
