<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                                <option>opt0</option>
                                <option>opt1</option>
                                <option>opt2</option>
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