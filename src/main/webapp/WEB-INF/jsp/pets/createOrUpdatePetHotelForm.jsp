<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#start").datepicker({dateFormat: 'yy/mm/dd'});
                $("#finish").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${petHotel['new']}">New </c:if>PetHotel</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${petHotel.pet.name}"/></td>
                <td><petclinic:localDate date="${petHotel.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${petHotel.pet.type.name}"/></td>
                <td><c:out value="${petHotel.pet.owner.firstName} ${petHotel.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="petHotel" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Start" name="start" />
                <petclinic:inputField label="Finish" name="finish" />
                <petclinic:inputField label="Description" name="description"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${petHotel.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add Booking</button>
                </div>
            </div>
        </form:form>

        <br/>
        <b>Previous Bookings</b>
        <table class="table table-striped">
            <tr>
                <th>Start</th>
                <th>Finish</th>
                <th>Description</th>
            </tr>
            <c:forEach var="petHotel" items="${petHotel.pet.petHotels}">
                <c:if test="${!petHotel['new']}">
                    <tr>
                        <td><petclinic:localDate date="${petHotel.start}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${petHotel.finish}" pattern="yyyy/MM/dd"/></td>
                        <td><c:out value="${petHotel.description}"/></td>
                        <td>
                            <spring:url value="/owners/{ownerId}/pets/{petId}/petHotels/{petHotelId}/delete" var = "deletePetHotelURL">
                            <spring:param name="petHotelId" value="${petHotel.id}"/>  
                            <spring:param name="ownerId" value="${petHotel.pet.owner.id}"/>
                            <spring:param name="petId" value="${petHotel.pet.id}"/> 
                            </spring:url>
                        <a href="${fn:escapeXml(deletePetHotelURL)}" onclick="return confirm('Are you sure?')" >Delete Booking</a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>
