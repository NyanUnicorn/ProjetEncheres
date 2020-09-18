package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.UtilisateurManager;

/**
 * Servlet implementation class CreerProfile
 */
@WebServlet("/CreerProfile")
public class CreerProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreerProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getAttribute("pseudo")!= null) {
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");
			rd.forward(request, response);
		}else {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil.jsp");
		rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager user = UtilisateurManager.getInstance();
		
		HashMap<String, String> formulaire = new HashMap<String, String>();
		
		formulaire.put("pseudo",request.getParameter("pseudo") );
		formulaire.put("nom", request.getParameter("nom"));
		formulaire.put("prenom", request.getParameter("prenom"));
		formulaire.put("telephone", request.getParameter("telephone"));
		formulaire.put("rue", request.getParameter("rue"));
		formulaire.put("codePostal", request.getParameter("codePostal"));
		formulaire.put("ville", request.getParameter("ville"));
		formulaire.put("password", request.getParameter("password"));
		formulaire.put("confirmation", request.getParameter("confirmation"));
		formulaire.put("email", request.getParameter("email"));
		
		
		
		try {
				user.creerCompte(formulaire);
				doGet(request, response);
			} catch (BllException e) {
			// TODO Auto-generated catch block
			request.setAttribute("erreurCreation", true);
			request.setAttribute("messageErreurCrea", e.getMessage());
			request.setAttribute("pseudo", formulaire.get("pseudo"));
			request.setAttribute("nom", formulaire.get("nom"));
			request.setAttribute("prenom", formulaire.get("prenom"));
			request.setAttribute("telephone", formulaire.get("telephone"));
			request.setAttribute("rue", formulaire.get("rue"));
			request.setAttribute("codePostal", formulaire.get("codePostal"));
			request.setAttribute("ville", formulaire.get("ville"));
			request.setAttribute("password", formulaire.get("password"));
			request.setAttribute("email", formulaire.get("email"));
			request.setAttribute("confirmation", formulaire.get("password"));
			doGet(request, response);
		}
		
	}
	


}
