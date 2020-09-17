<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}">
            <h3>
                ENI - Enchères
            </h3>
        </a>
    </div>
    <div class="nav-right">
<c:choose>
<c:when test="${connected}">
		<a href="${pageContext.request.contextPath}">
            <span>Enchères</span>
            <i class="fa fa-list" aria-hidden="true"></i>
        </a>
        <a href="${pageContext.request.contextPath}/NouvelleVente">
            <span>Vendre un article</span>
            <i class="fa fa-cart-arrow-down" aria-hidden="true"></i>
        </a>
        <a href="">
            <span>Mon profile</span>
            <i class="fa fa-user" aria-hidden="true"></i>
        </a>
        <a href="${pageContext.request.contextPath}/Deconnexion">
            <span>Déconexion</span>
            <i class="fa fa-sign-out" aria-hidden="true"></i>
        </a>
</c:when>
<c:otherwise>
        <a href="${pageContext.request.contextPath}/Connexion">
        	<span>s'inscrir - se connecter</span>
          	<i class="fa fa-sign-in" aria-hidden="true"></i>
        </a>
</c:otherwise>  
</c:choose>
    </div>
</nav>