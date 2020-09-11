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
                Nouvelle vente
            </h2>
            <form action="" class="fill-form">
                <div class="image">
                    <img src="" alt="">
                </div>
                <div class="infos">
                    <div class="txtinput">
                        <label>Article : </label>
                        <input type="text" name="" id="" placeholder="">
                    </div>
                    <div class="txtinput">
                        <label>Decription : </label>
                        <input type="text" name="" id="" placeholder="">
                    </div>
                    <div class="txtinput">
                        <label>Categorie : </label>
                        <select name="" id="">
                        <c:forEach items="${categories}" var="categ">
                            <option>${categ.libelle}</option>
                        </c:forEach>
                        </select>
                    </div>
                    <div class="txtinput">
                        <label>Photo de l'article : </label>
                        <input type="text" name="" id="" placeholder="">
                    </div>
                    <div class="txtinput">
                        <label>Mise à prix : </label>
                        <input type="number" name="" id="" placeholder="" min="0" value="0">
                    </div>
                    <div class="txtinput">
                        <label>Début de l'enchère : </label>
                        <input type="date" name="" id="">
                    </div>
                    <div class="txtinput">
                        <label>Fin de  l'enchère : </label>
                        <input type="date" name="" id="">
                    </div>
                    <div class="grouping">
                        <h5>Retrait</h5>
                        <div class="txtinput">
                            <label>Rue : </label>
                            <input type="text" name="" id="" placeholder="">
                        </div>
                        <div class="txtinput">
                            <label>Code Postale : </label>
                            <input type="text" name="" id="" placeholder="">
                        </div>
                        <div class="txtinput">
                            <label>Ville : </label>
                            <input type="text" name="" id="" placeholder="">
                        </div>
                        
                    </div>
                    <div class="grid-col-span-2 center-items">
                        <input class="button" type="submit" value="Enregistrer">
                        <a href="#" class="button">annuler</a>
                    </div>

                </div>
            </form>

        </div>
    </main>
</body>
</html>