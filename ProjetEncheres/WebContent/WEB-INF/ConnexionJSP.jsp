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
						<c:if test="${erreurConnexion}"> <p><c:out value="${BllException}"></c:out> </p></c:if>
					</div>	
		
			<form method ="post" action="Connexion" class="center-items">
				
				<div class="containerConnexion ">
										
					<div class = "Identifiant txtinput" >
						<label for="identifiant" class="labelIdentification">Identifiant:</label>
						<input type="text" name="identifiant" name id placeholder="" <c:if test="${!empty identifiant }"> value ="<c:out value="${ identifiant}"/>"</c:if>/>
					</div>
					<div class ="password txtinput">
						<label for="password" >Mot de passe:</label>
						<input type="password"  name="password" name id placeholder="" <c:if test="${!empty password }"> value ="<c:out value="${ password}"/>"</c:if>/>
					</div>
					<div class="optionConnexion">
						<div class="btconnexion">
							<input class="button" type="submit" value="Connexion"/>
						</div>
						<div class="container-connexionPwd">
							<div class="container-coSouvenir">
								<input type="checkbox" name="souvenir_mdp"/>
								<label for="souvenir_mdp">Se souvenir de moi</label>
							</div>
							<div class="container-coMdpOublie"> 
								<a href=""> Mot de passe oublié</a>
							</div>												
						</div >
					</div>
					<div class="creation-compte">
						<a class="button" href="${pageContext.request.contextPath}/CreerProfile"> Créer un compte </a>
					</div>
				</div>
			</form>
		</main>

</body>
</html>