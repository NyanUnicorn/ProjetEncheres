package fr.eni.encheres.dal.specifique;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.general.CrrudDAO;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.DalException;
public interface RetraitDAO extends CrrudDAO<Retrait>{
	ArticleVendu getArticleVendu(Retrait retrait) throws DalException;
}
