<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="schedule-visit">
	<jsp:attribute name="customScript">
		<!-- jquery datetimepicker addon -->
		<script src="/resources/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
		<link rel="stylesheet" href="/resources/css/jquery-ui-timepicker-addon.css">
		<!-- datetimepicker for moment selection -->
        <script>
            $(function () {
                $("#moment").datetimepicker({dateFormat: 'yy/mm/dd',timeFormat:'HH:mm'});
            });
        </script>
    </jsp:attribute>
	<jsp:body>
        <h2>Schedule a new visit</h2>

		<!-- Create visit form -->
        <form:form modelAttribute="visit" class="form-horizontal">
            <div class="form-group has-feedback">
            	 <!-- Pet dropdown -->
               	<div class="control-group">
                    <petclinic:selectField name="pet" label="Pet " names="${petsOfOwner}" size="1"/>
                </div>
            
            	<!-- Description input -->
                <petclinic:inputField label="Description" name="description" />
                
                <!-- VisitType dropdown -->
               	<div class="control-group">
                    <petclinic:selectField name="visitType" label="Visit type " names="${visitTypes}" size="1"/>
                </div>
                
                <!-- Vet dropdown -->
               	<div class="control-group">
                    <petclinic:selectField name="vet" label="Vet " names="${vets}" size="1"/>
                </div>
                
                <!-- Moment input -->
				<petclinic:inputField label="Moment" name="moment" />
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
