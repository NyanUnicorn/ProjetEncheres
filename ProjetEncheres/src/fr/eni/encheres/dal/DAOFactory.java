package fr.eni.encheres.dal;

import fr.eni.encheres.dal.mssqlimp.Mssql_ArticleVenduDAOimp;
import fr.eni.encheres.dal.mssqlimp.Mssql_CategorieDAOimp;
import fr.eni.encheres.dal.mssqlimp.Mssql_EncheresDAOimp;
import fr.eni.encheres.dal.mssqlimp.Mssql_RetraitDAOimp;
import fr.eni.encheres.dal.specifique.ArticleVenduDAO;
import fr.eni.encheres.dal.specifique.CategorieDAO;
import fr.eni.encheres.dal.specifique.EncheresDAO;
import fr.eni.encheres.dal.specifique.RetraitDAO;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;

public class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		return null;
	}
	public static CategorieDAO getCategorieDAO() {
		return new Mssql_CategorieDAOimp();
	}
	public static ArticleVenduDAO getArticleVenduDAO() {
		return new Mssql_ArticleVenduDAOimp();
	}
	public static EncheresDAO getEncheresDAO() {
		return new Mssql_EncheresDAOimp();
	}
	public static RetraitDAO getRetraitDAO() {
		return new Mssql_RetraitDAOimp();
	}
}
