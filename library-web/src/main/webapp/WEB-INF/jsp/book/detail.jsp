<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Book ${book.id} detail">
    <jsp:attribute name="body">
        <table class="table">
            <tr>
                <td class="col-md-2"><b>Id</b></td>
                <td>${book.id}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Isbn</b></td>
                <td>${book.isbn}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Author</b></td>
                <td>${book.author}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Title</b></td>
                <td><c:out value="${book.title}"/></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Loanable?</b></td>
                <td><c:out value="${book.loanable ? \"Yes \" : \"No \"}"/></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Available?</b></td>
                <td><c:out value="${book.loanable && available ? \"Yes \" : \"No \"}"/></td>
            </tr>
            <tr>
                <td><input type="button" value="Back" onclick="history.go(-1)" class="btn btn-danger"></td>
            </tr>
        </table>
    </jsp:attribute>
</my:pagetemplate>