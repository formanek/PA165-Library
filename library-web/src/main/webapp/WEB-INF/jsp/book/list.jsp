<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Books">
    <jsp:attribute name="body">

        <a href="${pageContext.request.contextPath}/book/new" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            New Book
        </a>

        <table class="table">
            <thead>
            <tr>
                <th>isbn</th>
                <th>author</th>
                <th>title</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td><c:out value="${book.isbn}"/></td>
                    <td><c:out value="${book.author}"/></td>
                    <td><c:out value="${book.title}"/></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/book/remove/${book.id}">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


    </jsp:attribute>
</my:pagetemplate>