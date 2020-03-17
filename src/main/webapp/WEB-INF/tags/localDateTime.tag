<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ attribute name="moment" required="true" type="java.time.LocalDateTime" %>
<%@ attribute name="pattern" required="true" type="java.lang.String" %>

<c:set var = "momentWithoutT" value = "${fn:replace(moment, 'T', ' ')}" />
<fmt:parseDate value="${momentWithoutT}" pattern="yyyy-MM-dd HH:mm" var="parsedMoment" type="both"/>
<fmt:formatDate value="${parsedMoment}" type="both" pattern="${pattern}"/>
