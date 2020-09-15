package fr.eni.encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;

/**
 * Servlet implementation class Encherir
 */
@WebServlet("/Encherir")
public class Encherir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Encherir() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("editableArticle", false);
		request.setAttribute("title", "Encherir");
		ArticleVenduManager amgr = ArticleVenduManager.GetInstace();
		
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
		
		request.setAttribute("article", article);
		request.setAttribute("meilleurOffre", meilleurOffre);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/article.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
