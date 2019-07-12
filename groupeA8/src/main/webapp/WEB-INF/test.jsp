<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="fr" xmlns="http://www.w3.org/1999/xhtml"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:ui="http://java.sun.com/jsf/facelets"
    xmlns:b="http://bootsfaces.net/ui">
    
<h:head>
<meta charset="UTF-8" />
    <title>Connexion</title>
    <link rel="shortcut icon" href="images/login.png"/>
</h:head>
<h:body>

<h:outputScript rendered="${UtilisateurController.alerte}">
   alert("YourErrorMessagfe");
</h:outputScript>

   	<div class="container"
		style="position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);">
		<b:jumbotron>
    
    <b:panel id="pan">
        <h:form id="test">
        <h1>Connexion</h1>
            <div class="form-group">
                <h:outputLabel for="utilisateur">Nom d'utilisateur :</h:outputLabel>
                <b:inputText type="text" styleClass="form-control" id="uti" onchange="javascript:alert('after the AJAX call');"
                     required="true" requiredMessage="Entrer un nom d'utilisateur"/>
                    <b:message for="uti"></b:message>
            </div>
            <div class="form-group">
                <h:outputLabel for="mdp">Mot de passe :</h:outputLabel>
                <b:inputSecret type="password" styleClass="form-control" id="mdp"
                    required="true" requiredMessage="Entrer un mot de passe"/>
					 <b:message for="mdp"></b:message>
                
            </div>
            <b:commandButton styleClass="btn btn-success" value="Se connecter"
                 /> 
               <b:focus target="@previous" />
        </h:form>
       </b:panel>
       </b:jumbotron>
    </div>
</h:body>
</html>