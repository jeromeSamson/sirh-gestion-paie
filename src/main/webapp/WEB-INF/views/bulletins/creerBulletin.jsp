<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/res/bootstrap-3.3.7-dist/css/bootstrap.css">
<title>Creer bulletin</title>
</head>
<body>
	<footer style="margin-top: 20px;">
		<!-- Barra de Navegacion -->
		<nav class="navbar navbar-default">
			<div class="collapse navbar-collapse" id="maNavBar">
				<ul class="nav navbar-nav">
					<li><a href='<c:url value="/mvc/employes/creer/"></c:url>'><span
							aria-hidden="true"></span> Creer Employe</a></li>
					<li><a href='<c:url value="/mvc/employes/lister"></c:url>'><span
							aria-hidden="true"></span> Lister Employés</a></li>
					<li><a href='<c:url value="/mvc/bulletins/creer"></c:url>'><span
							aria-hidden="true"></span> Creer Bulletin</a></li>
					<li><a href='<c:url value="/mvc/bulletins/lister/"></c:url>'><span
							aria-hidden="true"></span> Lister Bulletins</a></li>
				</ul>
			</div>
		</nav>
	</footer>
	<form method="post" modelAttribute="remEmp"
		class="form-horizontal">
		<fieldset>
			<legend style="text-align: center;">
				<h1>Créer bulletin de Salaire</h1>
			</legend>
			
			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="periode">Période</label>
				<div class="col-md-4">
					<select id="periode" name="periode" class="form-control">
						<c:forEach items="${listPer}" var="per">
							<option>${per.dateDebut}/${per.dateFin}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="remuneration">Matricule</label>
				<div class="col-md-4">
					<select id="remuneration" name="remuneration"
						class="form-control">
						<c:forEach items="${listRem}" var="rem">
							<option>${rem.matricule}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="prime">Prime exceptionnelle</label>
				<div class="col-md-4">
					<input id="prime" type="text" name="prime"
						placeholder="prime" class="form-control input-md" required="" />
				</div>
			</div>
			

			<div class="form-group col-md-8 control-label">
				<button type="submit" id="singlebutton" name="ajouter"
					class="btn btn-primary">Ajouter</button>
			</div>
		</fieldset>
		<sec:csrfInput />
	</form>
</body>
</html>