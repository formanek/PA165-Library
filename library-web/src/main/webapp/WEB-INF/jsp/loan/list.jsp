<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="List of loans">
    <jsp:attribute name="body">
        <table class="table">
            <thead>
                <tr>
                    <th>loaned</th>
                    <th>returned</th>
                    <th>member</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${loans}" var="loan">
                <tr>
                <td>
                    <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.loanTimestamp}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty loan.returnTimestamp}">
                        <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.returnTimestamp}"/>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/loan/return/${loan.id}">return</a>
                    </c:otherwise>
                </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/member/detail/${loan.member.id}">
                        <c:out value="${loan.member.email}"/>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/loan/detail/${loan.id}">detail</a>
                </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:pagetemplate>
