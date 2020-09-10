<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
<body>
    <nav class="navbar">
        <div class="nav-left">
            <a href="">
                <h3>
                    ENI - Enchères
                </h3>
            </a>
        </div>
        <div class="nav-right">
            <a href="">
                s'inscrir - se connecter
            </a>
        </div>
    </nav>
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
                            <label><i class="fa fa-search" aria-hidden="true"></i></label>
                            <input type="text" name="" id="" placeholder="Le nom de l'article contient">
                        </div>
                        <div>
                            <label>Categorie :</label>
                            <select name="">
                            <c:forEach items="${categories}" var="categ">
                                <option>${categ.libelle}</option>
                            </c:forEach>
                            </select>
                        </div>

                    </div>
                </div>
                <div class="container-item enchere-filtre">
                    <input class="button" type="submit" value="Rechercher">
                </div>
            </form>
            <div class="spacer">

            </div>
          <c:forEach items="${articles_encheres}" var="article">
            <div class="container-item ">
                <div class="article-list-item">
                    <div class="img">

                    </div>
                    <div class="info">
                        <span class="name">${article.nomArticle}</span>
                        <span class="price">${article.miseAPrix}</span>
                        <span class="dtend">${article.dateFinEncheres}</span>
                        <span class="seller">${article.vendeur.pseudo}</span>
                    </div>
                </div>
            </div>
          </c:forEach>
            <div class="container-item ">
                aa
            </div>
            <div class="container-item ">
                aa
            </div>
            <div class="container-item ">
                aa
            </div>

        </div>
    </main>
</body>
</html>