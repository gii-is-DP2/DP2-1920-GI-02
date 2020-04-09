<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<petclinic:layout pageName="visits">
    <h1>Revenues by month</h1>
    <h2>From the month that earns more to the less one</h2>
    <table id="revenueTable" class="table table-striped">
        <thead>
        <tr>
            <th>Month</th>
            <th>Revenues</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listMonth}" var="listMonth">
            <tr>
                <td>
                    <c:out value="${listMonth.key}"/>
                </td>
                <td>
                    <c:out value="${listMonth.value}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>