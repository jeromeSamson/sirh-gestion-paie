<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/res/bootstrap-3.3.7-dist/css/bootstrap.css">
<title>Creer employé</title>
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
	<form:form method="post" modelAttribute="remEmp"
		class="form-horizontal">
		<fieldset>
			<legend style="text-align: center;">
				<h1>Ajouter un employé</h1>
			</legend>
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="matricule">Matricule</label>
				<div class="col-md-4">
					<form:input id="matricule" path="matricule" type="text"
						placeholder="Matricule" class="form-control input-md" required="" />

				</div>
			</div>

			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="entreprise">Entreprise</label>
				<div class="col-md-4">
					<select id="entreprise" name="entreprise" class="form-control">
						<c:forEach items="${listEntreprise}" var="ent">
							<option>${ent.denomination}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="profil">Profil</label>
				<div class="col-md-4">
					<select id="profil" name="profil"
						class="form-control">
						<c:forEach items="${listProfil}" var="profil">
							<option>${profil.code}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="grade">Grade</label>
				<div class="col-md-4">
					<select id="grade" name="grade" class="form-control">
						<c:forEach items="${listGrade}" var="grade">
							<option>${grade.code}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group col-md-8 control-label">
				<button type="submit" id="singlebutton" name="ajouter"
					class="btn btn-primary">Ajouter</button>
			</div>
		</fieldset>
		<sec:csrfInput />
	</form:form>
</body>
</html>