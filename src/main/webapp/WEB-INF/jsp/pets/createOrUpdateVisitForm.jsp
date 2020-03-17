<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">

	<!-- JS and CSS -->
	<jsp:attribute name="customScript">
	
		<!-- jquery datetimepicker addon -->
		<script src="/resources/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
		<link rel="stylesheet" href="/resources/css/jquery-ui-timepicker-addon.css">
		
		<!-- datetimepicker for moment selection -->
        <script>
            $(function () {
                $("#moment").datetimepicker({dateFormat: 'yy/mm/dd',timeFormat:'hh:mm'});
            });
        </script>
    </jsp:attribute>
    
    <!-- body -->
	<jsp:body>
        <h2><c:if test="${visit['new']}">New </c:if>Visit</h2>
		
		<!-- Pet table -->
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
                <td><c:out value="${visit.pet.name}" /></td>
                <td><petclinic:localDate date="${visit.pet.birthDate}" pattern="yyyy/MM/dd" /></td>
                <td><c:out value="${visit.pet.type.name}" /></td>
                <td><c:out value="${visit.pet.owner.firstName} ${visit.pet.owner.lastName}" /></td>
            </tr>
        </table>

		<!-- Create/edit visit form -->
        <form:form modelAttribute="visit" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Moment" name="moment" />
                <petclinic:inputField label="Description" name="description" />
                
                <!-- Vet dropdown -->
               	<div class="control-group">
                    <petclinic:selectField name="vet" label="Vet " names="${vets}" size="1"/>
                </div>
                
                <!-- VisitType dropdown -->
               	<div class="control-group">
                    <petclinic:selectField name="visitType" label="Visit type " names="${visitTypes}" size="1"/>
                </div>
				
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${visit.pet.id}" />
                    <button class="btn btn-default" type="submit">Add Visit</button>
                </div>
            </div>
        </form:form>
        
    </jsp:body>
</petclinic:layout>
