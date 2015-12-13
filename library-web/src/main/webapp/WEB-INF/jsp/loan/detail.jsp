<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="Loan detail">
    <jsp:attribute name="body">
        <h2>Basic info</h2>
        Loaned <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.returnTimestamp}"/><br>
        <c:choose>
            <c:when test="${not empty loan.returnTimestamp}">
                Returned <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.returnTimestamp}"/>
            </c:when>
            <c:otherwise>
                Not returned yet (<a href="${pageContext.request.contextPath}/loan/return/${loan.id}">return</a>)
            </c:otherwise>
        </c:choose><br>
        Member <a href="${pageContext.request.contextPath}/member/detail/${loan.member.id}">
            <c:out value="${loan.member.email}"/>
        </a>
        <h2>Loan items</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>Book</th>
                    <th>condition before</th>
                    <th>condition after</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${loan.loanItems}" var="item">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/book/detail/${item.book.id}">
                            <c:out value="${item.book.title}"/>
                        </a>
                    </td>
                    <td>${item.conditionBefore}</td>
                    <td>
                <c:choose>
                    <c:when test="${not empty item.conditionAfter}">
                        ${item.conditionAfter}
                    </c:when>
                    <c:otherwise>
                        (not set)
                    </c:otherwise>
                </c:choose>
                </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:pagetemplate>
