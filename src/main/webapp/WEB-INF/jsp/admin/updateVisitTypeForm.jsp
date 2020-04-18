<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="visitTypes">
    <jsp:body>
        <h2>Edit visit type</h2>


        <form:form modelAttribute="visitType" class="form-horizontal">
            <div class="form-group has-feedback">
				<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Duration" name="duration"/>
                <petclinic:inputField label="Price" name="price"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">

                    <button class="btn btn-default" type="submit">Save changes</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>
