package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Enchere;

/**
 * Servlet implementation class ListeEncheres
 */
@WebServlet("/ListeEncheres")
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
		
		EncheresManager emgr = EncheresManager.GetInstace();
		CategorieManager cmgr = CategorieManager.GetInstace();
		List<Enchere> encheres = null;
		
		try {
			encheres = emgr.Get();
		} catch (BllException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			request.setAttribute("categories", cmgr.Get());
		} catch (BllException e) {
			e.printStackTrace();
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/liste_encheres.jsp");
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
