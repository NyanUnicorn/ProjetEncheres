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
            <form>
                <div class="container-item">
                    <div class="enchere-filtre">
                        <h4>filtres : </h4>
                        <div class="txtinput">
                            <label for="frag_name"><i class="fa fa-search" aria-hidden="true"></i></label>
                            <input type="text" name="frag_name" id="frag_name" placeholder="Le nom de l'article contient" value="<c:out value="${fragmenNom}"/>">
                        </div>
                        <div class="txtinput">
                            <label>Categorie :</label>
                            <select name="categ" >
                            <Option	value="0">Tous</Option>
                          <c:forEach items="${categories}" var="categ">
                              <option <c:if test="${categ.noCategorie==noCateg}">selected</c:if> value="${categ.noCategorie}">${categ.libelle}</option>
                          </c:forEach>
                            </select>
                        </div>
<c:if test="${connected}">
						<div class="col-1-1">
                            <div class="rdo-cbx">
                                <div class="rdoinput">
                                    <input type="radio" name="achatvente" id="rdoAchat" value="0">
                                    <label for="rdoAchat">Achats</label>
                                </div>
                                <div>
                                    <div class="cbxinput">
                                        <input type="checkbox" name="encheresOuverte" id="eo">
                                        <label for="eo">encheres ouvertes</label>
                                    </div>
                                    <div class="cbxinput">
                                        <input type="checkbox" name="mesEncheresEnCours" id="meec">
                                        <label for="meec">mes enchers en cours</label>
                                    </div>
                                    <div class="cbxinput">
                                        <input type="checkbox" name="mesEncheresRemportees" id="mer">
                                        <label for="mer">mes enchères remportées</label>
                                    </div>
                                </div>
                            </div>
                            <div class="rdo-cbx">
                                <div class="rdoinput">
                                    <input type="radio" name="achatvente" id="rdoVente" value="1">
                                    <label for="rdoVente">Vente</label>
                                </div>
                                <div>
                                    <div class="cbxinput">
                                        <input type="checkbox" name="mesVenteEnCours" id="mvec">
                                        <label for="mvec">mes ventes en cours</label>
                                    </div>
                                    <div class="cbxinput">
                                        <input type="checkbox" name="mesVenteNonDebutees" id="mvnd">
                                        <label for="mvnd">ventes non débutées</label>
                                    </div>
                                    <div class="cbxinput">
                                        <input type="checkbox" name="mesVentesTerminees" id="mvt">
                                        <label for="mvt">ventes terminées</label>
                                    </div>
                                </div>
                            </div>
                        </div>
</c:if>

                    </div>
                </div>
                <div class="container-item enchere-filtre">
                    <input class="button" type="submit" value="Rechercher">
                </div>
            </form>
            <div class="spacer">

            </div>
          <c:forEach items="${articles_encheres}" var="article">
            <a href="${pageContext.request.contextPath}/Encherir?noarticle=${article.noArticle}" class="container-item ">
                <div class="article-list-item">
                    <div class="img">
						<img alt="" src="${pageContext.request.contextPath}/assets/image/lamasticot.png">
                    </div>
                    <div class="info">
                        <span class="name"><u>${article.nomArticle}</u></span>
                        <span class="price">${article.miseAPrix}</span>
                        <span class="dtend">${article.dateFinEncheres}</span>
                        <span class="seller">${article.vendeur.pseudo}</span>
                    </div>
                </div>
            </a>
          </c:forEach>

        </div>
    </main>
</body>
</html>