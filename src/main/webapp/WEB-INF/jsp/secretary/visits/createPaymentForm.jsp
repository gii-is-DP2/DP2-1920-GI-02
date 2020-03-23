<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="payments">
    <jsp:body>
        <h2>New Payment</h2>
       <form:form modelAttribute="payment" class="form-horizontal">
            <input type="hidden" name="id" value="${payment.id}"/>
            <div class="form-group has-feedback">
                <div class="control-group">
                    <petclinic:selectField name="method" label="Method" names="${method}" size="1"/>
                </div>
                <petclinic:inputField label="Final Price" name="finalPrice"/>
            </div>
           <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
		                 <c:when test="${payment['new']}">
		                       <button class="btn btn-default" type="submit">Add Payment</button>
		           		 </c:when>
		            </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!payment['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>