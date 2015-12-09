<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="New Book">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/book/create"
                   modelAttribute="bookCreate" cssClass="form-horizontal">
            <div class="form-group ${isbn_error?'has-error':''}">
                <form:label path="isbn" cssClass="col-sm-2 control-label">Isbn</form:label>
                <div class="col-sm-10">
                    <form:input path="isbn" cssClass="form-control"/>
                    <form:errors path="isbn" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${author_error?'has-error':''}">
                <form:label path="author" cssClass="col-sm-2 control-label">Author</form:label>
                <div class="col-sm-10">
                    <form:input path="author" cssClass="form-control"/>
                    <form:errors path="author" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${title_error?'has-error':''}">
                <form:label path="title" cssClass="col-sm-2 control-label">Title</form:label>
                <div class="col-sm-10">
                    <form:input path="title" cssClass="form-control"/>
                    <form:errors path="title" cssClass="help-block"/>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Create book</button>
        </form:form>        

    </jsp:attribute>
</my:pagetemplate>