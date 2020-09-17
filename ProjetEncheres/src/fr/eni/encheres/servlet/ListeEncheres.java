package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ListeEncheres
 */
@WebServlet("/Encheres")
public class ListeEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeEncheres() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "Liste des Enchères");
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
		boolean connected = user != null;
		request.setAttribute("connected", connected);
		ArticleVenduManager amgr = ArticleVenduManager.GetInstace();
		CategorieManager cmgr = CategorieManager.GetInstace();
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		List<ArticleVendu> articleEnCours = new ArrayList<>();
		List<ArticleVendu> mesArtEnCours = new ArrayList<>();
		List<ArticleVendu> mesArtRemp = new ArrayList<>();
		List<ArticleVendu> mesVentesEnCours = new ArrayList<>();
		List<ArticleVendu> mesVentesNonDebut = new ArrayList<>();
		List<ArticleVendu> mesVentesTermin = new ArrayList<>();
		
		
		String fragmenNom = request.getParameter("frag_name");
		if(fragmenNom == null) {
			fragmenNom = "";				
		}
		int noCateg= 0;
		String sNoCateg = request.getParameter("categ");
		if(sNoCateg != null && sNoCateg != "") {
			noCateg = Integer.parseInt(sNoCateg);
		}
		boolean achatvente = true;
		String sAchatVente = request.getParameter("achatvente");
		if(sAchatVente != null) {
			achatvente = sAchatVente != null ? Integer.parseInt(sAchatVente) == 0 : false;
		}
			
		if(connected && achatvente) {
			request.setAttribute("rdachat", achatvente);
			boolean encheresOuverte = !request.getParameterNames().hasMoreElements();
			boolean mesEncheresEnCours = false;
			boolean mesEncheresRemportees = false;
			{
				String sencheresOuverte = request.getParameter("encheresOuverte");
				if(sencheresOuverte != null && sencheresOuverte != "") {
					encheresOuverte = cbxToBool(sencheresOuverte);
				}
				String smesEncheresEnCours = request.getParameter("mesEncheresEnCours");
				if(smesEncheresEnCours != null && smesEncheresEnCours != "") {
					mesEncheresEnCours = cbxToBool(smesEncheresEnCours);
				}
				String smesEncheresRemportees = request.getParameter("mesEncheresRemportees");
				if(smesEncheresRemportees != null && smesEncheresRemportees != "") {
					mesEncheresRemportees = cbxToBool(smesEncheresRemportees);
				}
			}
			if(encheresOuverte) {
				getLast(articleEnCours,noCateg, fragmenNom);
				request.setAttribute("cxencheresOuverte", encheresOuverte);
			}
			if(mesEncheresEnCours) {
				getMesEncheresEnCours(mesArtEnCours, noCateg, fragmenNom, user);
				request.setAttribute("cxmesEncheresEnCours", mesEncheresEnCours);
			}
			if(mesEncheresRemportees) {
				getMesEncheresRemportees(mesArtRemp, noCateg, fragmenNom, user);
				request.setAttribute("cxmesEncheresRemportees", mesEncheresRemportees);
				//sort out duplicates
			}
		}else if(connected && !achatvente) {
			request.setAttribute("rdvente", !achatvente);
			boolean mesVenteEnCours = false;
			boolean mesVenteNonDebutees = false;
			boolean mesVentesTerminees = false;
			{
				String smesVenteEnCours = request.getParameter("mesVenteEnCours");
				if(smesVenteEnCours != null && smesVenteEnCours != "") {
					mesVenteEnCours = cbxToBool(smesVenteEnCours);
				}
				String smesVenteNonDebutees = request.getParameter("mesVenteNonDebutees");
				if(smesVenteNonDebutees != null && smesVenteNonDebutees != "") {
					mesVenteNonDebutees = cbxToBool(smesVenteNonDebutees);
				}
				String smesVentesTerminees = request.getParameter("mesVentesTerminees");
				if(smesVentesTerminees != null && smesVentesTerminees != "") {
					mesVentesTerminees = cbxToBool(smesVentesTerminees);
				}
			}
			if(mesVenteEnCours) {
				getMesVentesEnCours(mesVentesEnCours, noCateg, fragmenNom, user);
				request.setAttribute("cxmesVenteEnCours", mesVenteEnCours);
			}
			if(mesVenteNonDebutees) {
				getMesVentesNonDebutees(mesVentesNonDebut, noCateg, fragmenNom, user);
				request.setAttribute("cxmesVenteNonDebutees", mesVenteNonDebutees);
			}
			if(mesVentesTerminees) {
				getMesVentesTerminees(mesVentesTermin, noCateg, fragmenNom, user);
				request.setAttribute("cxmesVentesTerminees", mesVentesTerminees);
			}
		}else {
			//select all articles with basic filters
			getLast(articleEnCours,noCateg, fragmenNom);
		}
		if(articleEnCours != null) {
			if(articleEnCours.size() > 0) {
				for(ArticleVendu a : articleEnCours) {
					try {
						if(a.getVendeur() != null) {
							a.setVendeur(umgr.getUtilisateur(a.getVendeur().getNoUtilisateur()));
						}
					} catch (BllException e) {
						e.printStackTrace();
					}
				}
			}
		}

		request.setAttribute("fragmenNom", fragmenNom);
		request.setAttribute("noCateg", noCateg);
		request.setAttribute("articles_encheres",articleEnCours);
		request.setAttribute("articles_encheres_ec",mesArtEnCours);
		request.setAttribute("articles_encheres_r",mesArtRemp);
		request.setAttribute("articles_ventes_ec",mesVentesEnCours);
		request.setAttribute("articles_ventes_nd",mesVentesNonDebut);
		request.setAttribute("articles_ventes_t",mesVentesTermin);
		
		try {
			request.setAttribute("categories", cmgr.Get());
		} catch (BllException e) {
			e.printStackTrace();
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/listeEncheres.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void organiseDuplicateArticle(List<ArticleVendu> base, List<ArticleVendu> insert) {
		if(insert != null) {
			//find and handle duplicat
			ArticleVendu dup;
			for(ArticleVendu arti : insert) {
				dup = null;
				for(ArticleVendu artb : base) {
					if(arti.getNoArticle() == artb.getNoArticle()) {
						dup = arti;
						//deal with duplicate
						break;
					}
				}
				if(dup == null) {
					base.add(arti);
				}
			}
		}
	}
	
	protected void getLast(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom){
		ArticleVenduManager amgr = ArticleVenduManager.GetInstace();
		try {
			organiseDuplicateArticle(articleEnCours, amgr.GetLast(noCateg, fragmenNom));
			
		} catch (BllException e) {
			e.printStackTrace();
		}
	}

	protected void getMesEncheresEnCours(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user){
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		try {
			organiseDuplicateArticle(articleEnCours, umgr.getEncheresEnCours(articleEnCours, noCateg, fragmenNom, user));
		} catch (BllException e) {
			e.printStackTrace();
		}
	}
	
	protected void getMesEncheresRemportees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user){
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		try {
			organiseDuplicateArticle(articleEnCours, umgr.getEncheresRemportees(articleEnCours, noCateg, fragmenNom, user));
		} catch (BllException e) {
			e.printStackTrace();
		}
	}
	
	protected void getMesVentesEnCours(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user){
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		try {
			organiseDuplicateArticle(articleEnCours, umgr.getVentesEnCours(articleEnCours, noCateg, fragmenNom, user));
		} catch (BllException e) {
			e.printStackTrace();
		}
	}
	
	protected void getMesVentesNonDebutees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user){
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		try {
			organiseDuplicateArticle(articleEnCours, umgr.getVentesNonDebutees(articleEnCours, noCateg, fragmenNom, user));
		} catch (BllException e) {
			e.printStackTrace();
		}
	}
	
	protected void getMesVentesTerminees(List<ArticleVendu> articleEnCours, int noCateg, String fragmenNom, Utilisateur user){
		UtilisateurManager umgr = UtilisateurManager.getInstance();
		try {
			organiseDuplicateArticle(articleEnCours, umgr.getVentesTerminees(articleEnCours, noCateg, fragmenNom, user));
		} catch (BllException e) {
			e.printStackTrace();
		}
	}
	
	protected static boolean cbxToBool(String str) {
		return str.equals("on");
	}
}
