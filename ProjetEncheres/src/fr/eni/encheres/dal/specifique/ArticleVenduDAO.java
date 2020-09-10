package fr.eni.encheres.dal.specifique;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.general.CrrudDAO;

public interface ArticleVenduDAO extends CrrudDAO<ArticleVendu> {

	Categorie getCategory(ArticleVendu _art) throws DalException;
	
	Retrait getRetrait(ArticleVendu _art) throws DalException;
	
	List<ArticleVendu> selectEnchereEnCours() throws DalException;
	
	List<ArticleVendu> selectEnchereEnCours(int _categ, String _name_frag) throws DalException;
}
