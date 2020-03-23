<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="creditcards">
    <jsp:body>
        <h2>New CreditCard</h2>
       <form:form modelAttribute="creditcard" class="form-horizontal">
            <input type="hidden" name="id" value="${creditcard.id}"/>
            <div class="form-group has-feedback">
            <petclinic:inputField label="Holder" name="holder"/>
            <petclinic:inputField label="Brand" name="brand"/>
            <petclinic:inputField label="Number" name="number"/>
                <div class="control-group">
                    <petclinic:selectField name="expMonth" label="Expiration Month" names="${expMonth}" size="1"/>
                </div>
                <petclinic:inputField label="Expiration Year" name="expYear"/>
                <petclinic:inputField label="Security Code" name="securityCode"/>
            </div>
           <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${creditcard['new']}">
                            <button class="btn btn-default" type="submit">Add CreditCard</button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!creditcard['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>