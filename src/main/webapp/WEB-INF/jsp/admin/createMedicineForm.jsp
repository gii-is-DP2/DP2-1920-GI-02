<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="medicines">
    <jsp:body>
        <h2><c:if test="${medicine['new']}">New </c:if>Medicine</h2>


        <form:form modelAttribute="medicine" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Brand" name="brand"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="medicine.id" value="${medicine.id}"/>
                    <button class="btn btn-default" type="submit">Add medicine</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
