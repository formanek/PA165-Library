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
        <hr>

        <table class="table table-hover table-condensed fixed">
            <thead>
            <tr>
                <th>Isbn</th>
                <th>Author</th>
                <th>Title</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td class="col-md-2"><c:out value="${book.isbn}"/></td>
                    <td class="col-md-3"><c:out value="${book.author}"/></td>
                    <td class="col-md-3"><c:out value="${book.title}"/></td>
                    <td class="col-md-1">
                        <a href="${pageContext.request.contextPath}/book/detail/${book.id}" class="btn btn-info">View</a>
                    </td>
                    <td class="col-md-1">
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