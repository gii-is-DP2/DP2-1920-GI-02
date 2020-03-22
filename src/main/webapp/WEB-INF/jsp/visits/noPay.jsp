<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="visits">
    <h2>Visits without payment</h2>
    <table id="visitsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Moment</th>
            <th>Description</th>
            <th>Pet</th>
            <th>Do payment</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${visits}" var="visit">
        	<input type="hidden" name="id" value="${visit.id}"/>
            <tr>
                <td>
                    <petclinic:localDateTime moment="${visit.moment}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                    <c:out value="${visit.description}"/>
                </td>
                <td>
                    <c:out value="${visit.pet.name}"/>
                </td>
                <td>
                	<spring:url value="/visits/{visitId}/payments/new" var="addUrl">
        				<spring:param name="visitId" value="${visit.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add Payment</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>