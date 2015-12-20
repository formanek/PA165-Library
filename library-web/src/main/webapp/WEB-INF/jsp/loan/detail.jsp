<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="Loan detail">
    <jsp:attribute name="body">
        
        <h2>Basic info</h2>
        <table class="table">
            <tr>
                <td class="col-md-2"><b>Loaned</b></td>
                <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.loanTimestamp}"/><br></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Returned</b></td>
                <td>
                    <c:choose>
                        <c:when test="${not empty loan.returnTimestamp}">
                            <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.returnTimestamp}"/>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/loan/return/${loan.id}" class="btn btn-default btn-sm">Return</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Member</b></td>
                <td><a href="${pageContext.request.contextPath}/member/detail/${loan.member.id}">
                        <c:out value="${loan.member.email}"/>
                </a></td>
            </tr>
        </table>
        
        <h2>Loan items</h2>
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th>Book</th>
                    <th>Condition before</th>
                    <th>Condition after</th>
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
                    <td>${item.conditionBefore.toString()}</td>
                    <td>
                <c:choose>
                    <c:when test="${not empty item.conditionAfter}">
                        ${item.conditionAfter.toString()}
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
