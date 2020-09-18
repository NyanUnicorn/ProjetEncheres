CREATE VIEW [VIEW_ARTICLE_VENDUS]
AS
	SELECT a.[no_article]
		,a.[nom_article]
		,a.[date_debut_encheres]
		,a.[date_fin_encheres]
		,a.[prix_initial]
		,a.[prix_vente]
		,a.[no_utilisateur] as [no_vendeur]
		,a.[no_categorie]
		,a.[description]
		,a.[image]
		,(SELECT CASE WHEN t1.no_utilisateur IS NULL THEN (SELECT CASE WHEN a.[date_fin_encheres] <= GETDATE() THEN 2 ELSE 0 END ) ELSE 1 END) as [etat_vente]
		,t1.no_utilisateur [no_acheteur]
	FROM [dbo].[ARTICLES_VENDUS] a
	LEFT JOIN (SELECT 
			u.[no_utilisateur]
			,e.[no_article]
			,e.[montant_enchere]
		FROM [UTILISATEURS] u 
		JOIN [ENCHERES] e ON u.[no_utilisateur] = e.[no_utilisateur]
		RIGHT JOIN [ARTICLES_VENDUS] av ON av.[no_article] = e.[no_article]
		WHERE (SELECT TOP 1
				MAX(en.[montant_enchere])
			FROM [ENCHERES] en
			WHERE en.[no_article] = e.[no_article]
			GROUP BY en.[no_article]
			 ) = e.[montant_enchere]
			 AND av.[date_fin_encheres] <= GETDATE() ) as t1 ON t1.no_article = a.[no_article] 
GO
