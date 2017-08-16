<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/res/bootstrap-3.3.7-dist/css/bootstrap.css">
<title>Lister employé</title>
</head>
	<body>
	<footer style="margin-top: 20px;">
		<!-- Barra de Navegacion -->
		<nav class="navbar navbar-default">
			<div class="collapse navbar-collapse" id="maNavBar">
				<ul class="nav navbar-nav">
					<li><a href='<c:url value="/mvc/employes/creer"></c:url>'><span
							aria-hidden="true"></span> Creer Employe</a></li>
					<li><a href='<c:url value="/mvc/employes/lister"></c:url>'><span
							aria-hidden="true"></span> Lister Employés</a></li>
					<li><a href='<c:url value="/mvc/bulletins/creer"></c:url>'><span
							aria-hidden="true"></span> Creer Bulletin</a></li>
					<li><a href='<c:url value="/mvc/bulletins/lister"></c:url>'><span
							aria-hidden="true"></span> Lister Bulletins</a></li>
				</ul>
			</div>
		</nav>
		<div class="container fluid">
			<div class="row">
				<h1>Lister des bulletins</h1>
			</div>
			<div class="row">
				<div class="col-md-offset-8 col-md-4 col-xs-offset-8 col-xs-4">
					<a href="<c:url value='/mvc/bulletins/creer/'/>" class="btn btn-primary pull-right">Créer un nouveau bulletin</a>
				</div>
			</div>
			<br>
			<div class="row">
				<table class="table table-hover table-striped" border="2px black solid">
					<thead>
						<tr>
							<th>Date/heure création</th>
							<th> Période</th>
							<th> Matricule </th>
							<th> Salaire brut</th>
							<th>Net imposable</th>
							<th>Net a payer</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${listBul}" var="bul">
						<tr>
							<td>${bul.key.dateHeure}</td>
							<td>${bul.key.periode.dateDebut} - ${bul.key.periode.dateFin}</td>
							<td>${bul.key.remunerationEmploye.matricule}</td>
							<td>${bul.value.salaireBrut}</td>
							<td>${bul.value.netImposable}</td>
							<td>${bul.value.netAPayer}</td>
							<td> <a href='<c:url value="/mvc/bulletins/${bul.key.id}"></c:url>'>Visualiser</a></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			
		</div>
	</footer>
	<sec:csrfInput/>
	</body>
</html>