<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${\"Register member\"}"/>

<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <form:form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/member/create" modelAttribute="memberCreate">
            <legend>Add new member in the system.</legend>
            <div class="form-group ${givenName_error?'has-error':''}">
                <form:label cssClass="col-lg-2 control-label" path="givenName">Name</form:label>
                <div class="col-lg-4">
                    <form:input cssClass="form-control" placeholder="Given name" path="givenName" />
                    <form:errors path="givenName" cssclass="help-block"></form:errors>
                </div>
            </div>
            <div class="form-group ${surname_error?'has-error':''}">
                <form:label cssClass="col-lg-2 control-label" path="surname">Surname</form:label>
                <div class="col-lg-4">
                    <form:input cssClass="form-control" placeholder="Surname" path="surname"/>
                    <form:errors path="surname" cssclass="help-block"></form:errors>
                </div>
            </div>
            <div class="form-group ${email_error?'has-error':''}">
                <form:label cssClass="col-lg-2 control-label" path="email">Email</form:label>
                <div class="col-lg-4">
                    <form:input cssClass="form-control" placeholder="Email" path="email"/>
                    <form:errors path="email" cssclass="help-block"></form:errors>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <button type="submit" class="btn btn-primary">Register member</button>
                </div>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>
