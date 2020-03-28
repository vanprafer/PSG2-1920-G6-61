<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<petclinic:layout pageName="causes">
    <h2>Causas</h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Budget Target</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes.causeList}" var="causes">
            <tr>
                <td>
                    <c:out value="${causes.name}"/>
                </td>
                <td>
                    <c:out value="${causes.budgetTarget}"/>
                </td>
                <td>
                    <spring:url value="/vets/{vetId}/edit" var="editUrl">
                            <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(editUrl)}">Editar Veterinario</a>
                    
                 </td>
                 <td>
                 <spring:url value="/vets/delete/{vetId}" var="deleteVetUrl">
                            <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    	<a class="btn btn-default" href="${fn:escapeXml(deleteVetUrl)}">Borrar Veterinario</a>
                 </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/causes.xml" htmlEscape="true" />">Ver como XML</a>
            </td>            
        </tr>
    </table>
    
    <br/>
    <a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'>A�adir Veterinario</a>
    
   
</petclinic:layout>