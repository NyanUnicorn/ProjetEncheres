package fr.eni.encheres.bo;

public class Retrait {
	private String rue;
	private String codePostale;
	private String ville;
	private ArticleVendu articleVendu;
	public Retrait(int noArticle, String rue, String codePostale, String ville) {
		super();
		this.setArticleVendu(new ArticleVendu(noArticle));
		this.rue = rue;
		this.codePostale = codePostale;
		this.ville = ville;
	}
	
	
	public Retrait() {
		super();
	}


	public String getRue() {
		return rue;
	}


	public void setRue(String rue) {
		this.rue = rue;
	}


	public String getCodePostale() {
		return codePostale;
	}


	public void setCodePostale(String codePostale) {
		this.codePostale = codePostale;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public ArticleVendu getArticleVendu() {
		return articleVendu;
	}


	public void setArticleVendu(ArticleVendu articleVendu) {
		this.articleVendu = articleVendu;
	}
	
	
}
