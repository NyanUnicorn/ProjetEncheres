package fr.eni.encheres.bo;

import java.sql.Date;

public class Enchere {
	private Date dateEnchere;
	private int montantEnchere;
	private Utilisateur utilisateur;
	private ArticleVendu articleVendu;
	
	public Enchere(int noUtilisateur, int noArticleVendu, Date dateEnchere, int montantEnchere) {
		super();
		this.getUtilisateur().setNoUtilisateur(noUtilisateur);
		this.getArticleVendu().setNoArticle(noArticleVendu);
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}
	
	public Enchere() {
		super();
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getUtilisateur() {
		if(utilisateur == null)
			utilisateur = new Utilisateur();
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public ArticleVendu getArticleVendu() {
		if(articleVendu == null)
			articleVendu = new ArticleVendu();
		return articleVendu;
	}

	public void setArticleVendu(ArticleVendu articleVendu) {
		this.articleVendu = articleVendu;
	}
	
	 
	
}
