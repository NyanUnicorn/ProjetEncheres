package fr.eni.encheres.dal.specifique;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.general.CrrudDAO;

public interface ArticleVenduDAO extends CrrudDAO<ArticleVendu> {

	Categorie GetCategory(ArticleVendu _art) throws DalException;
	
	Retrait GetRetrait(ArticleVendu _art) throws DalException;
}
