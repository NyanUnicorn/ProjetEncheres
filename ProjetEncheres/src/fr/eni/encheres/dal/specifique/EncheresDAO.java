package fr.eni.encheres.dal.specifique;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.general.DeleteDAO;
import fr.eni.encheres.dal.general.InsertDAO;
import fr.eni.encheres.dal.general.SelectByIdPairDAO;
import fr.eni.encheres.dal.general.SelectDAO;
import fr.eni.encheres.dal.general.UpdateDAO;
import fr.eni.encheres.dal.DalException;

public interface EncheresDAO extends InsertDAO<Enchere>, SelectDAO<Enchere>, SelectByIdPairDAO<Enchere>, UpdateDAO<Enchere>, DeleteDAO<Enchere> {
	
	Utilisateur getUtilisateur(Enchere enchere)throws DalException;
	ArticleVendu getArticle(Enchere enchere) throws DalException;
	/*
	List<Enchere> selectEnchereEnCours() throws DalException;
	List<Enchere> selectEnchereEnCours(int _categ, String _name_frag) throws DalException;
*/
}
