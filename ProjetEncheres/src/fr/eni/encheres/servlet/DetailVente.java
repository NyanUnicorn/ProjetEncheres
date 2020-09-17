package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class DetailVente
 */
@WebServlet("/DetailVente")
public class DetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailVente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("editableArticle", false);
		request.setAttribute("title", "Detail Vente");
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
		boolean connected = user != null;
		request.setAttribute("connected", connected);
		ArticleVenduManager amgr = ArticleVenduManager.GetInstace();
		EncheresManager emgr = EncheresManager.GetInstace();
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		
		int noArticle = 0;
		String sNoArticle = request.getParameter("noarticle");
		if(sNoArticle != null && sNoArticle != "") {
			noArticle = Integer.parseInt(sNoArticle);
		}
		ArticleVendu article = null;
		Enchere meilleurOffre = null;
		
		if(noArticle != 0) {
			try {
				//article
				article = amgr.Get(noArticle);
				amgr.GetLieuRetrait(article);
				amgr.GetCategorie(article);
				//meilleur offre
				meilleurOffre = amgr.getBestOffer(article);
			} catch (BllException e) {
				e.printStackTrace();
			}
		}
		if(meilleurOffre != null) {
			try {
				meilleurOffre.setUtilisateur(umgr.getUtilisateur(meilleurOffre.getUtilisateur().getNoUtilisateur()));
			} catch (BllException e) {
				e.printStackTrace();
			}
		}
		
		//check if bid-able
		boolean encherable = true;
		encherable = connected ? article.getVendeur().getNoUtilisateur() != user.getNoUtilisateur() : false;
		encherable = !encherable ? encherable : article.getDateDebutEncheres().compareTo(new Date(System.currentTimeMillis())) < 0;
		encherable = !encherable ? encherable : article.getDateFinEncheres().compareTo(new Date(System.currentTimeMillis())) > 0; 
		request.setAttribute("encherable", encherable);
		if(encherable) {
			request.setAttribute("title", "Encherir");
		}
		request.setAttribute("article", article);
		request.setAttribute("meilleurOffre", meilleurOffre);
		
		

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/article.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
		boolean connected = user != null;
		request.setAttribute("connected", connected);
		ArticleVenduManager amgr = ArticleVenduManager.GetInstace();
		EncheresManager emgr = EncheresManager.GetInstace();
		
		String errorMessage = "";
		boolean erroneous = false;
		//bid quantity
		int enchereqt = 0;
		String sEnchereqt = request.getParameter("enchereqt");
		if(sEnchereqt != null) {
			enchereqt = Integer.parseInt(sEnchereqt);
		}
		//no article
		int noArticle = 0;
		String sNoArticle = request.getParameter("noarticle");
		if(sNoArticle != null && sNoArticle != "") {
			noArticle = Integer.parseInt(sNoArticle);
		}
		
		ArticleVendu article = null;
		Enchere meilleurOffre = null;
		//get article
		if(noArticle != 0) {
			try {
				//article
				article = amgr.Get(noArticle);
				amgr.GetLieuRetrait(article);
				amgr.GetCategorie(article);
				//meilleur offre
				meilleurOffre = amgr.getBestOffer(article);
				
			} catch (BllException e) {
				e.printStackTrace();
			}
		}
		//check bid is greater than mas offer
		if(meilleurOffre != null) {
			if(meilleurOffre.getMontantEnchere()  < enchereqt) {
				errorMessage = "Montant inferieur au maximum actuel";
				erroneous = true;
			}			
		}
		//create bid if no errors
		if(!erroneous) {
			Enchere ench = new Enchere(user.getNoUtilisateur(), article.getNoArticle(), new java.sql.Date(System.currentTimeMillis()), enchereqt);
			try {
				emgr.Add(ench);
			} catch (BllException e) {
				erroneous = true;
				errorMessage = "Enchere pas réussie";
				e.printStackTrace();
			}
		}
		//error message if erroneous
		if(erroneous) {
			request.setAttribute("erroneous", erroneous);
			request.setAttribute("errorMessage", errorMessage);			
		}
		doGet(request, response);
	}

}
