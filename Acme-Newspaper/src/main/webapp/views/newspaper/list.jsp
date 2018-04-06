

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->



<display:table pagesize="5" class="displaytag" 
	name="newspapers" requestURI="newspaper${uri}/list.do" id="row">
	
	
	<!-- title -->
	<spring:message code="newspaper.title"
		var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
	
	<!-- publicationDate -->
	<spring:message code="newspaper.publicationDate"
		var="publicationDateHeader" />
	<display:column property="publicationDate" title="${publicationDateHeader}"
		sortable="true" />	
		
	<!-- description -->
	<spring:message code="newspaper.description"
		var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />
		
	<!-- pictureURL -->
	<spring:message code="newspaper.pictureURL" var="pictureHeader" />
	<spring:message code="newspaper.pictureError" var="pictureError" />
	<display:column title="${pictureHeader}" sortable="true" > <img src="${row.pictureURL}"  width="auto" height="200"></display:column> 

	<!-- isPublished -->
		<spring:message code="newspaper.isPublished"
		var="isPublishedHeader" />
	<display:column property="isPublished" title="${isPublishedHeader}"
		sortable="true" />
		
	<!-- isPrivate -->
		<spring:message code="newspaper.isPrivate"
		var="isPrivateHeader" />
	<display:column property="isPrivate" title="${isPrivateHeader}"
		sortable="true" />
		
	<!-- articles -->
	<spring:message code="newspaper.articles"
  	var="articles" />
	<display:column title="${articles}">
	<ul>
		<jstl:forEach items="${row.articles}" var="article"> 
			 <li>
				 <a href="article/display.do?articleId=${article.id}">
				 	<jstl:out value ="${article.title}" />
				 </a>
			 </li>
		</jstl:forEach>
	</ul>
	</display:column>
	
	<!-- Publisher -->
	<spring:message code="newspaper.user"
		var="userHeader" />
	<display:column property="user.name" title="${userHeader}"
		sortable="true" />
		
</display:table>
