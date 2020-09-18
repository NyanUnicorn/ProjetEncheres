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
            
            <div class="container">
						<c:if test="${erreurCreation}"> <p><c:out value="${messageErreurCrea}" />  </p></c:if>
					</div>	
            <form method="post" action="CreerProfile" class="fill-form">
                    <div class="txtinput">
                        <label>Pseudo : </label>
                        <input type="text" name="pseudo" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${pseudo}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Nom : </label>
                        <input type="text" name="nom" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${nom}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Prénom : </label>
                        <input type="text" name="prenom" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${prenom}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Email : </label>
                        <input type="text" name="email" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${email}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Téléphone : </label>
                        <input type="text" name="telephone" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${telephone}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Rue : </label>
                        <input type="text" name="rue" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${rue}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Code Postal : </label>
                        <input type="text" name="codePostal" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${codePostal}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Ville : </label>
                        <input type="text" name="ville" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${ville}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Mot de passe : </label>
                        <input type="password" name="password" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${password}"></c:out> </c:if>">
                    </div>
                    <div class="txtinput">
                        <label>Confirmation : </label>
                        <input type="password" name="confirmation" id="" placeholder="" value ="<c:if test="${erreurCreation}"> <c:out value="${confirmation}"></c:out> </c:if>">
                    </div>
                    <div class="grid-col-span-2 center-items">
                        <input class="button" type="submit" value="Créer">
                        <a href="${pageContext.request.contextPath}" class="button">annuler</a>
                    </div>
            </form>

        </div>
    </main>
</body>
</html>