<%-- Author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="title" value="${\"Error \"} ${code}"/>

<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <p><b>Application has encountered an error.</b></p>
    <c:if test="${code == '400'}"><p>The request could not be understood by the server.</p></c:if>
    <c:if test="${code == '401'}"><p>The request requires user authentication.</p></c:if>
    <c:if test="${code == '403'}"><p>Access forbidden.</p></c:if>
    <c:if test="${code == '404'}"><p>Resource was not found.</p></c:if>
    <c:if test="${code == '500'}"><p>Internal server error occured.</p></c:if>
    <c:if test="${code == '503'}"><p>Service is unavailable. Server is overloaded or under maintenance.</p></c:if>
</jsp:attribute>
</my:pagetemplate>
    