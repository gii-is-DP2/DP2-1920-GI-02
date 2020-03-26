<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="visit-diagnostics">
    <jsp:body>
        <h2><c:if test="${prescription['new']}">New </c:if>Prescription</h2>


        <form:form modelAttribute="prescription" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Frequency" name="frequency"/>
                <petclinic:inputField label="Duration" name="duration"/>
				<petclinic:selectField label="Medicine" name="medicine" names="${medicines}" size="1"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="prescription.id" value="${prescription.id}"/>
                    <button class="btn btn-default" type="submit">Add prescription</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
