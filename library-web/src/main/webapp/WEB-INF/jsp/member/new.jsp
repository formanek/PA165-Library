<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="${\"Register member\"}"/>

<%-- note: <tr class="active"> makes the row highlighted --%>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1>${title}</h1>
    </jsp:attribute>
</my:pagetemplate>