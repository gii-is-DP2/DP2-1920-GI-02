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
            <td><b><c:out value="${visit.moment}"/></b></td>
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

	<jstl:if test="${visit.diagnosis==null}">
    <spring:url value="{visitId}/diagnosis/new" var="addUrl">
        <spring:param name="diagnosisId" value="${diagnosis.id}"/>
		<spring:param name="visitId" value="${visit.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add diagnosis</a>
    </jstl:if>

<jstl:if test="${visit.diagnosis!=null}">
    <spring:url value="{visitId}/diagnostics/{diagnosisId}/prescriptions/new" var="addUrl">
		<spring:param name="prescriptionId" value="${prescription.id}"/>
        <spring:param name="diagnosisId" value="${diagnosis.id}"/>
		<spring:param name="visitId" value="${visit.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add prescription</a>
    </jstl:if>

</petclinic:layout>
