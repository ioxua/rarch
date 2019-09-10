<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<t:commonTemplate pageName="Employees">
    <jsp:attribute name="styles">
        stylees
    </jsp:attribute>
    <jsp:body>
        <C:set var="" value="asad" />
        <p>
        <h1>Employees</h1>
        ${requestScope['javax.servlet.forward.request_uri']}
        ${request.getAttribute("javax.servlet.forward.request_uri")}
        ${request.getContextPath()}
        <c:set var="req" value="${pageContext.request}" />
        <c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}" />
        <c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
        <c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
        <c:set var="pageUrl" value="${ baseURL.concat(requestPath )}"/>
        ${pageContext.request.requestURI}
        </p>
    </jsp:body>
</t:commonTemplate>