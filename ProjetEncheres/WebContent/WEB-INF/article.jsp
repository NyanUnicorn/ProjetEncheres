<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
<jsp:include page="/WEB-INF/fragments/navbar.jsp"></jsp:include>
    <main>
        <div class="container">
            <h2>
                ${title}
            </h2>
            <form action="" method="post" class="fill-form  fill-form-1-2">
                <div class="image">
                    <img src="" alt="">
                </div>
                <div class="infos" >
<c:choose>
<c:when test="${editableArticle == true }">
                    <div class="txtinput">
                        <label for="nomArticle">Article : </label>
                        <input type="text" name="nom" id="nomArticle" placeholder="">
                    </div>
                    <div class="txtinput article-description">
                        <label for="descriptionArticle">Decription : </label>
                        <textarea id="descriptionArticle" cols="30" rows="10" name="description" ><c:out value="${article.description}"/></textarea>
                    </div>
                    <div class="txtinput">
                        <label for="categorieArticle">Categorie : </label>
                        <select name="categorie" id="categorieArticle">
                        <c:forEach items="${categories}" var="categ">
                            <option value="${categ.noCategorie}">${categ.libelle}</option>
                        </c:forEach>
                        </select>
                    </div>
                    <div class="txtinput">
                        <label for="imageArticle">Photo de l'article : </label>
                        <input type="file" name="image" id="imageArticle" accept="image/png, image/jpeg">
                    </div>
                    <div class="txtinput">
                        <label for="prixInitialeArticle">Mise à prix : </label>
                        <input type="number" name="prixinitiale" id="prixInitialeArticle" min="0" value="0">
                    </div>
                    <div class="txtinput">
                        <label for="sdateArticle">Début de l'enchère : </label>
                        <input type="date" name="sdate" id="sdateArticle">
                    </div>
                    <div class="txtinput">
                        <label for="edateArticle">Fin de  l'enchère : </label>
                        <input type="date" name="edate" id="edateArticle">
                    </div>
</c:when>
<c:otherwise>
					<h3><c:out value="${article.nomArticle}"/></h3>
                    <div class="form-detail">
                        <label>Decription : </label>
                        <p>
                            <c:out value="${article.description}"/>
                        </p>
                    </div>
                    <div class="form-detail">
                        <label>Categorie : </label>
                        <span><c:out value="${article.categorie.libelle}"/></span>
                    </div>
<c:if test="${not empty meilleurOffre}">
                    <div class="form-detail">
                        <label>Meilleur offre : </label>
                        <span><c:out value="${meilleurOffre.montantEnchere}"/> points par <c:out value="${meilleurOffre.utilisateur.pseudo}"/></span>
                    </div>
</c:if>
                    <div class="form-detail">
                        <label>Mise à prix : </label>
                        <span><c:out value="${article.miseAPrix}"/> points</span>
                    </div>
                    <div class="form-detail">
                        <label>Fin de  l'enchère : </label>
                        <input type="date" name="" id="" disabled value="${article.dateFinEncheres}">
                    </div>
</c:otherwise>
</c:choose>
                    
                    
                    
                    
<c:choose>
<c:when test="${editableArticle == true }">
                    <div class="grouping">
                        <h5>Retrait</h5>
                        <div class="txtinput">
                            <label for="rueRetrait">Rue : </label>
                            <input type="text" name="rue" id="rueRetrait" placeholder="">
                        </div>
                        <div class="txtinput">
                            <label for="cpRetrait">Code Postale : </label>
                            <input type="text" name="cp" id="cpRetrait" placeholder="">
                        </div>
                        <div class="txtinput">
                            <label for="villeRetrait">Ville : </label>
                            <input type="text" name="ville" id="villeRetrait" placeholder="">
                        </div>
                    </div>
</c:when>
<c:when test="${not empty article.lieuRetrait}">
                    <div class="grouping">
                        <h5>Retrait</h5>
                       <div class="form-detail">
                            <label>Rue : </label>
                            <span>4 rue de la coccinelle</span>
                        </div>
                        <div class="form-detail">
                            <label>Code Postale : </label>
                            <span>91000</span>
                        </div>
                        <div class="form-detail">
                            <label>Ville : </label>
                            <span>Chernobil</span>
                        </div>
                    </div>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose> 
                    <div class="grid-col-span-2 center-items">
<c:choose>
<c:when test="${editableArticle == true }">
                        <input class="button" type="submit" value="Enregistrer">
                        <a href="${pageContext.request.contextPath}" class="button">annuler</a>
</c:when>
<c:otherwise>
                        <div class="txtinput">
                            <label>Ma Proposition : </label>
                            <input type="number" name="" id="" placeholder="" min="${empty meilleurOffre ? article.miseAPrix : (meilleurOffre + 1)}" value="${empty meilleurOffre ? article.miseAPrix : (meilleurOffre + 1)}">
                        </div>
                        <input class="button" type="submit" value="Enchérir">
</c:otherwise>
</c:choose> 
                    </div>
                    

                </div>
            </form>

        </div>
    </main>
</body>
</html>