<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="${\"Library members\"}"/>

<%-- note: <tr class="active"> makes the row highlighted --%>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1>${title}</h1>

        <table class="table table-striped table-hover ">
            <thead>
                <tr>
                    <%--<th>Id</th>--%>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Detail</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${members}" var="member">
                    <tr>
                        <%--<td>${member.id}</td>--%>
                        <td><fmt:formatDate value="${member.email}"/></td>
                        <td><c:out value="${member.givenName}"/></td>
                        <td><c:out value="${member.surname}"/></td>
                        <td>
                            <a href="/member/detail/${member.id}" class="btn btn-primary">Detail</a>
                        </td>
                        
                    </tr>
                </c:forEach>
            </tbody>
        </table> 
    </jsp:attribute>
</my:pagetemplate>