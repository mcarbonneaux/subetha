<%@include file="../inc/taglibs.jspf" %>

<%@ attribute name="title" required="true" %>

<trim:header title="${title}">
	<c:if test="${auth.loggedIn}">
		<div class="sideBox LHS">
			<div>Main Menu</div>
			<a href="<c:url value="/home.jsp"/>">&rsaquo; Home</a>
			<a href="<c:url value="/change_password.jsp"/>">&rsaquo; Change Password</a>
		</div>
	</c:if>

	<c:if test="${auth.siteAdmin}">
		<div class="sideBox LHS">
			<div>Site Admin Menu</div>
			<a href="<c:url value="/site_status.jsp"/>">&rsaquo; Site Status</a>
			<a href="<c:url value="/site_settings.jsp"/>">&rsaquo; Site Settings</a>
			<a href="<c:url value="/list_create.jsp"/>">&rsaquo; Create List</a>
			<a href="<c:url value="/lists.jsp"/>">&rsaquo; List Lists</a>
			<a href="<c:url value="/admins.jsp"/>">&rsaquo; Administrators</a>
		</div>
	</c:if>

	<jsp:doBody/>
</trim:header>