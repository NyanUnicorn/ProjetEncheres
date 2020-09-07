package fr.eni.encheres.bo;

import java.util.List;

public class Utilisateur {
	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String codePostal;
	private String rue;
	private String ville;
	private String motDePasse;
	private int credit;
	private List<Enchere> encheres;
	private List<ArticleVendu> articlesVendu;
	private List<ArticleVendu> articlesAchat;
	
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String codePostal, String rue, String ville, String motDePasse, int credit) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.codePostal = codePostal;
		this.rue = rue;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String codePostal,
			String rue, String ville, String motDePasse, int credit) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.codePostal = codePostal;
		this.rue = rue;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
	}

	public Utilisateur() {
		super();
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public List<ArticleVendu> getArticlesVendu() {
		return articlesVendu;
	}

	public void setArticlesVendu(List<ArticleVendu> articlesVendu) {
		this.articlesVendu = articlesVendu;
	}

	public List<ArticleVendu> getArticlesAchat() {
		return articlesAchat;
	}

	public void setArticlesAchat(List<ArticleVendu> articlesAchat) {
		this.articlesAchat = articlesAchat;
	}
	
	
	
	

}
