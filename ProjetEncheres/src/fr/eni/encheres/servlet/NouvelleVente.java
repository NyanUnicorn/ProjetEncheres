package fr.eni.encheres.servlet;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.catalina.tribes.util.UUIDGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import fr.eni.encheres.bll.ArticleVenduManager;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class NouvelleVente
 */
@WebServlet("/NouvelleVente")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
	maxFileSize = 1024 * 1024 * 5, 
	maxRequestSize = 1024 * 1024 * 5 * 5 )
public class NouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NouvelleVente() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
        File uploadDir = new File( "" );
        if ( ! uploadDir.exists() ) uploadDir.mkdir();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/article.jsp");
		request.setAttribute("editableArticle", true);
		request.setAttribute("title", "Nouvelle Vente");
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
		boolean connected = user != null;
		request.setAttribute("connected", connected);
		CategorieManager cmgr = CategorieManager.GetInstace();
		//set attributes for article based on parameters from previous input
		

		
		try {
			request.setAttribute("categories", cmgr.Get());
		} catch (BllException e) {
			e.printStackTrace();
		}
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String,String[]> params = request.getParameterMap();
		ArticleVenduManager amgr = ArticleVenduManager.GetInstace();
		RetraitManager rmgr = RetraitManager.GetInstace();
		Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
		

		
		String imageDir = "";
		String imageName = "";
		String fileType = "";
		try {
			imageDir = (NouvelleVente.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
			Path p = Paths.get(imageDir.substring(1, imageDir.length()-1)).getParent().getParent();
			imageDir = p.toString();
			Collection<Part> parts = request.getParts();
			Part filePart = request.getPart("image");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			InputStream fileContent = filePart.getInputStream();
			imageName = UUID.randomUUID().toString();
			File file = new File(imageDir+"\\assets\\image\\article\\" + imageName + "." + fileType);
			file.createNewFile();
			OutputStream stream = new DataOutputStream(new FileOutputStream(file));
			IOUtils.copy(fileContent,stream);
			stream.close();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		ArticleVendu art = s_fromRequestArticleMapper(params, user);
		if(!imageName.equals("") && !fileType.equals("")) {
			art.setImageName(imageName+"."+fileType);			
		}
		boolean success = false;
		if(art!= null) {
			try {
				amgr.Add(art);
				Retrait ret = s_fromRequestRetraitMapper(params, art.getNoArticle());
				if(ret != null) {
					rmgr.Add(ret);
				}
				success = true;
			} catch (BllException e) {
				e.printStackTrace();
			}
		}
		
		if(success) {
			response.sendRedirect(request.getContextPath());			
		}else {
			doGet(request, response);			
		}
	}
	
	protected static ArticleVendu s_fromRequestArticleMapper(Map<String,String[]> _params, Utilisateur _util) {
		ArticleVendu art = null;
		String nom;
		String description;
		Date dateDebutEncheres;
		Date dateFinEncheres;
		int miseAPrix;
		int categorie;
		
		try {
			nom = _params.get("nom")[0];
			description = _params.get("description")[0];
			dateDebutEncheres = Date.valueOf(_params.get("sdate")[0]);
			dateFinEncheres = Date.valueOf(_params.get("edate")[0]);
			miseAPrix = Integer.parseInt(_params.get("prixinitiale")[0]);
			categorie = Integer.parseInt(_params.get("categorie")[0]);
			art = new ArticleVendu(nom,description,dateDebutEncheres,dateFinEncheres,miseAPrix,_util,categorie);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return art;
	}
	
	protected static Retrait s_fromRequestRetraitMapper(Map<String,String[]> _params, int _noArt) {
		Retrait ret = null;
		String rue;
		String cp;
		String ville;

		try {			
			rue = _params.get("rue")[0];
			cp = _params.get("cp")[0];
			ville = _params.get("ville")[0];
			ret = new Retrait(_noArt, rue, cp, ville);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	

}
