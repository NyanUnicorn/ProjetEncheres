package fr.eni.encheres.dal.specifique;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.general.SelectByIdDAO;
import fr.eni.encheres.dal.general.SelectDAO;

public interface CategorieDAO extends SelectDAO<Categorie>, SelectByIdDAO<Categorie> {

	List<ArticleVendu> GetArticlesVendu(Categorie _cat) throws DalException;
	
}
