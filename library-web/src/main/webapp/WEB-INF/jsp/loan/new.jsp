<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Loan books">
    <jsp:attribute name="body">

        <h3>Loan books to member
            <a href="${pageContext.request.contextPath}/member/detail/${member.id}">
                <c:out value="${member.givenName}"/>&nbsp;<c:out value="${member.surname}"/>
            </a>
        </h3>

        <form action="${pageContext.request.contextPath}/loan/new/${member.id}" method="post">
            <br>
            Available books:
            <table class="table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Condition</th>
                        <th>Want to loan</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <%-- Book Title --%>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/detail/${book.id}">
                                    <c:out value="${book.title}"/>
                                </a>
                            </td>

                            <%-- Book Condition --%>
                            <td>
                                <select name="condition${book.id}" disabled class="disabled">
                                    <c:forEach items="${conditions}" var="condition">
                                        <option value="${condition}">
                                                ${condition.toString()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>

                            <%-- Want to loan - checkbox --%>
                            <td>
                                <input type="checkbox" name="requiredBooks" value="${book.id}" id="requiredBooks${book.id}"
                                       onClick="$('[name=\'condition${book.id}\']').toggleClass('disabled');
                                           $('#requiredBooks${book.id}').prop('checked') ? $('[name=\'condition${book.id}\']').prop('disabled', false)
                                            : $('[name=\'condition${book.id}\']').prop('disabled', true);">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <button class="btn btn-primary" type="submit">Loan books</button>

        </form>

    </jsp:attribute>
</my:pagetemplate>
