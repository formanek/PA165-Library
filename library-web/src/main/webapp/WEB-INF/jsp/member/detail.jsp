<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${\"Member details\"}"/>

<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        
        
        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/member/update/${member.id}" modelAttribute="member">
            <legend>View or edit member details</legend>
            <div class="form-group">
                <a href="${pageContext.request.contextPath}/loan/new/${member.id}" class="btn btn-primary col-lg-4 col-lg-offset-2">
                    Create new loan for this member
                </a>
            </div>
            <div class="form-group">
                <a href="${pageContext.request.contextPath}/loan" class="btn btn-primary col-lg-4 col-lg-offset-2">
                    Show unreturned loans of this member
                </a>
            </div>            
            <div class="form-group ${givenName_error?'has-error':''}">
                <form:label class="col-lg-2 control-label" path="givenName">Name</form:label>
                <div class="col-lg-4">
                    <form:input class="form-control" path="givenName"/>
                    <form:errors path="givenName" cssclass="help-block"></form:errors>
                </div>
            </div>
            <div class="form-group ${surname_error?'has-error':''}">
                <form:label class="col-lg-2 control-label" path="surname">Surname</form:label>
                <div class="col-lg-4">
                    <form:input class="form-control" path="surname"/>
                    <form:errors path="surname" cssclass="help-block"></form:errors>
                </div>
            </div>
            <div class="form-group ${email_error?'has-error':''}">
                <form:label class="col-lg-2 control-label" path="email">Email</form:label>
                <div class="col-lg-4">
                    <form:input class="form-control" path="email"/>
                    <form:errors path="email" cssclass="help-block"></form:errors>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-10 col-lg-offset-2">
                    <span onclick="history.go(-1)" class="btn btn-default">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        Go back
                    </span>
                    <button type="submit" class="btn btn-primary">Update member</button>
                </div>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>
