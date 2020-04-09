<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="visits">
    <h2>Data payments of that visit</h2>
    <table id="visitsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Method</th>
            <th>Moment</th>
            <th>Final Price</th>
            <th>Secretary name</th>
            <th>Credit card</th>
        </tr>
        </thead>
        <tbody>
            <tr>
            	<td>
                    <c:out value="${visit.payment.method}"/>
                </td>
                <td>
                    <petclinic:localDateTime moment="${visit.payment.moment}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                
                <td>
                    <c:out value="${visit.payment.finalPrice}"/>
                </td>
                <td>
                    <c:out value="${visit.payment.secretary.firstName}"/>
                </td>
                <td>
                    <c:out value="${visit.payment.creditcard.number}"/>
                </td>
            </tr>
        </tbody>
    </table>
</petclinic:layout>