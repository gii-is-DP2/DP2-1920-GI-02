<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="vet-visits">

    <h2>Visit Information</h2>

    <table class="table table-striped">
        <tr>
            <th>Moment</th>
            <td><b><petclinic:localDateTime moment="${visit.moment}" pattern="yyyy-MM-dd HH:mm"/></b></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${visit.description}"/></td>
        </tr>
        <tr>
            <th>Diagnosis</th>
            <td><c:out value="${visit.diagnosis.description}"/></td>
        </tr>
    </table>

<jstl:if test="${bool==true}">

	<h2>Prescription Information</h2>

	<table id="prescriptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Frequency</th>
            <th>Duration</th>
            <th>Medicine</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${visit.diagnosis.prescriptions}" var="prescription">
        	<input type="hidden" name="id" value="${prescription.id}"/>
            <tr>
				<td>
                    <c:out value="${prescription.frequency}"/>
                </td>
                <td>
                    <c:out value="${prescription.duration}"/>
                </td>
                <td>
                    <c:out value="${prescription.medicine.name}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
	</jstl:if>
    </table>
	
	<jstl:if test="${bool==true}">
	<jstl:if test="${visit.diagnosis==null}">
    <spring:url value="{visitId}/diagnosis/new" var="addUrl">
		<spring:param name="visitId" value="${visit.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add diagnosis</a>
    </jstl:if>
	</jstl:if>

	<jstl:if test="${bool==true}">
	<jstl:if test="${visit.diagnosis!=null}">
    <spring:url value="{visitId}/prescriptions/new" var="addUrl">
		<spring:param name="visitId" value="${visit.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add prescription</a>
    </jstl:if>
	</jstl:if>

</petclinic:layout>
