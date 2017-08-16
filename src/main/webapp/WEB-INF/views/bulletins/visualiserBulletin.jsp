<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	</footer>
	<div class="container-fluid" item="${bul}" var="bul">
		<div class="row">
			<div class="col-md-1">
				<!-- Flèche gauche icon by Icons8 -->
				<a href='<c:url value="/mvc/bulletins/lister"></c:url>'><img
					src="https://png.icons8.com/back-arrow/nolan/64"
					title="Flèche gauche" width="64" height="64" /></a>
			</div>
			<div class="col-md-offset-2 col-md-4">
				<h1>Bulletin de salaire</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-8 col-md-4">
				<strong>Période</strong> <br> <span>Du
					${bul.periode.dateDebut} au ${bul.periode.dateFin}</span>

			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<strong>Entrprise</strong> <br> <span>${bul.remunerationEmploye.entreprise.denomination }</span>
				<br> <span>Siret :
					${bul.remunerationEmploye.entreprise.siret }</span>
			</div>
			<div class="col-md-offset-5 col-md-4">
				<br> <span>Matricule :
					${bul.remunerationEmploye.matricule }</span>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-3">
				<strong>Salaire</strong>
			</div>
		</div>
		<table class="table table-striped table-bordered" item="${result}"	var="result">
			<thead>
				<tr>
					<th>Rubriques</th>
					<th>Base</th>
					<th>Taux salarial</th>
					<th>Montant Salarial</th>
					<th>Taux Patronal</th>
					<th>Cot patronales</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Salaire de base</td>
					<td>${bul.remunerationEmploye.grade.tauxBase}</td>
					<td></td>
					<td>${result.salaireDeBase }</td>
					<td></td>
					<td></td>

				</tr>
				<tr>
					<td>Prime Exce</td>
					<td></td>
					<td></td>
					<td>${bul.primeExceptionnelle}</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Salaire Brut</td>
					<td></td>
					<td></td>
					<td>${result.salaireBrut }</td>
					<td></td>
					<td></td>
					
			</tbody>
		</table>
		<br>
			<div class="row">
			<div class="col-md-3">
				<strong>Cotisation</strong>
			</div>
		</div>
		<table class="table table-striped table-bordered" item="${result}" var="result">
			<thead>
				<tr>
					<th>Rubriques</th>
					<th>Base</th>
					<th>Taux salarial</th>
					<th>Montant Salarial</th>
					<th>Taux Patronal</th>
					<th>Cot patronales</th> 
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bul.remunerationEmploye.profilRemuneration.cotisationsNonImposables }" var="cot">
				<tr>
					<td>${cot.libelle}</td>
						<td>${result.salaireBrut}</td>
						<td>${cot.tauxSalarial}</td>
						<td><c:if test="${cot.tauxSalarial != null}">
								<fmt:formatNumber type="number"
									value="${result.salaireBrut * cot.tauxSalarial}"
									maxFractionDigits="0" pattern="#" />
							</c:if></td>
						<td>${cot.tauxPatronal}</td>
						<td><c:if test="${cot.tauxPatronal != null}">
								<fmt:formatNumber type="number"
									value="${result.salaireBrut * cot.tauxPatronal}"
									maxFractionDigits="0" pattern="#" />
							</c:if></td>
							</tr>
				</c:forEach>
				<tr>
					<td>Total Retenue</td>
					<td></td>
					<td></td>
					<td><fmt:formatNumber type="number"
									value="${totalRetenueMontantSalarial}"
									maxFractionDigits="0" pattern="#" /></td>
					<td></td>
					<td><fmt:formatNumber type="number"
									value="${totalRetenueCotisationsPatronales}"
									maxFractionDigits="0" pattern="#" /></td>
				</tr>
			</tbody>
		</table>
		<br>
			<div class="row">
			<div class="col-md-3">
				<strong>Net Imposable</strong>
			</div>
		</div>
		<table class="table table-striped table-bordered" item="${result}" var="result">
			<thead>
				<tr>
					<th>Rubriques</th>
					<th>Base</th>
					<th>Taux salarial</th>
					<th>Montant Salarial</th>
					<th>Taux Patronal</th>
					<th>Cot patronales</th> 
				</tr>
			</thead>
			<tbody>
				<c:forEach
					items="${bul.remunerationEmploye.profilRemuneration.cotisationsImposables}"
					var="cotisation">
					<tr>
						<td>${cotisation.libelle}</td>
						<td>${result.salaireBrut}</td>
						<td>${cotisation.tauxSalarial}</td>
						<td><c:if test="${cotisation.tauxSalarial != null}">
								<fmt:formatNumber type="number"
									value="${result.salaireBrut * cotisation.tauxSalarial}"
									maxFractionDigits="0" pattern="#" />
							</c:if></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
			<div class="row">
		<strong class="pull-right">NET A PAYER:
			${result.netAPayer} </strong>
	</div>
</body>
</html>