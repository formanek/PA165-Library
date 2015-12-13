<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Loan return">
    <jsp:attribute name="body">
        <c:choose>
            <c:when test="${empty loan.returnTimestamp}">
                <form method="post" action="${pageContext.request.contextPath}/loan/return/${loan.id}">
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
                                                error: already set to ${item.conditionAfter}
                                            </c:when>
                                            <c:otherwise>
                                                <select name="condition${item.book.id}">
                                                    <c:forEach items="${conditions}" var="condition">
                                                        <option value="${condition}">
                                                            ${condition}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <button type="submit">Return loan</button>
                </form>
            </c:when>
            <c:otherwise>
                Loan already returned <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${loan.returnTimestamp}"/>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</my:pagetemplate>
