<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="Books">
    <jsp:attribute name="body">

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="form-group row">
                <div class="col-md-2">
                    <a href="${pageContext.request.contextPath}/book/new" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    New Book
                    </a>
                </div>
        </sec:authorize>

            <form action="${pageContext.request.contextPath}/book">
                <div class="col-md-offset-4 col-md-2 col-xs-4">
                    <select class="form-control" name="loanable">
                        <option value="true" ${param.loanable ? 'selected' : ''}>Loanable</option>
                        <option value="false" ${empty param.loanable || param.loanable ? '' : 'selected'}>Unloanable</option>

                    </select>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-primary" type="submit">Filter</button>
                </div>
            </form>
        </div>
        <hr>

        <table class="table table-hover table-striped">
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
                    <td class="col-md-2 col-sm-2 col-xs-2"><c:out value="${book.isbn}"/></td>
                    <td class="col-md-3 col-sm-3 col-xs-5"><c:out value="${book.author}"/></td>
                    <td class="col-md-3 col-sm-4 col-xs-6"><c:out value="${book.title}"/></td>
                    <td class="col-md-2 col-sm-3 col-xs-1 btn-group btn-group-justified">
                        <a href="${pageContext.request.contextPath}/book/detail/${book.id}" class="btn-sm btn-default">Detail</a>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <form class="btn-sm form-just-with-button" method="post" action="${pageContext.request.contextPath}/book/loanability/${book.id}?loanable=${book.loanable}">
                                <c:choose>
                                    <c:when test="${empty param.loanable || param.loanable}">
                                        <button type="submit" class="btn-sm btn-danger form-button">Remove</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn-sm btn-success form-button">Return</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </sec:authorize>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>