package fr.eni.encheres.servlet;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.mssqlimp.Mssql_UtilisateurDAOimp;
import fr.eni.encheres.dal.specifique.UtilisateurDAO;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	
	UtilisateurDAO userDAO = new Mssql_UtilisateurDAOimp();
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getAttribute("identifiant")!= null){
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("/WEB-INF/ConnexionJSP.jsp");
			rd.forward(request, response);
			
		}else {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies ) {
			if(cookie.getName().equals("pseudo")) {
				request.setAttribute("identifiant", cookie.getValue());
			} else if(cookie.getName().equals("pwd")){
				request.setAttribute("password", cookie.getValue());

			}
		}
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("/WEB-INF/ConnexionJSP.jsp");
		rd.forward(request, response);
		
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String identifiant;
		String password;
		UtilisateurManager userManager = null;
		
		userManager = UtilisateurManager.getInstance();
		
		Utilisateur user = null; 
		HttpSession session = null;
		
		
		try {
			identifiant = request.getParameter("identifiant");
			password = request.getParameter("password");
			try {
			user = userManager.getUtilisateur(identifiant, password);session = request.getSession();
			session.setAttribute("utilisateur", user);
			String[] value =request.getParameterValues("souvenir_mdp"); 
			if(value!= null && value[0].equals("on")) {
				Cookie cookiePseudo = new Cookie("pseudo", identifiant);
				Cookie cookiePwd = new Cookie("pwd", password);
				cookiePseudo.setMaxAge(60*60*24*30);
				cookiePwd.setMaxAge(60*60*24*30);
				response.addCookie(cookiePseudo);
				response.addCookie(cookiePwd);
			}

			response.sendRedirect(request.getContextPath());	
			} catch (BllException e) {
				request.setAttribute("identifiant", identifiant);
				request.setAttribute("password", password);
				request.setAttribute("erreurConnexion", true);
				request.setAttribute("BllException", e.getMessage());
				doGet(request, response);
			}
			
							
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		//doGet(request, response);
		
	}

}
