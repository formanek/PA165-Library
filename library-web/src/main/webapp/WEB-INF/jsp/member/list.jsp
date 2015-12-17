<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="${\"Library members\"}"/>

<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <a href="${pageContext.request.contextPath}/member/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            Register member 
        </a>
        <table class="table table-striped table-hover ">
            <thead>
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${members}" var="member">
                    <tr>
                        <td><c:out value="${member.email}"/></td>
                        <td><c:out value="${member.givenName}"/></td>
                        <td><c:out value="${member.surname}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/member/detail/${member.id}" class="btn-sm btn-default">Detail</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table> 
    </jsp:attribute>
</my:pagetemplate>
