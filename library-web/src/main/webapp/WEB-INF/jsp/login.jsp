<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="title" value="${\"Library\"}"/>

<%-- call my own tag defined in /WEB-INF/tags/pagetemplate.tag, provide title attribute --%>
<my:pagetemplate title="${title}">
<jsp:attribute name="body"><%-- provide page-fragment attribute to be rendered by the my:layout tag --%>

    <c:if test="${param.error != null}">
        <p class="text-danger">
            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
        </p>
    </c:if>
    <c:if test="${param.logout != null}">
        <p class="text-success">You have been logged out.</p>
    </c:if>

    <sec:authorize access="isAuthenticated()">
        <p>You are logged in as <strong><c:out value="${pageContext.request.userPrincipal.name}"/></strong></p>
        <a class="btn btn-info" href="${pageContext.request.contextPath}/logout">Logout</a>
    </sec:authorize>

    <sec:authorize access="isAnonymous()">
        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/login">
            <fieldset>
                <legend>Please login to start using the system.</legend>
                <h4>List of users</h4>
                <p><a href="https://github.com/formanek/PA165-Library/wiki#use-case-diagram" target="_blank">Here</a> you can see roles and permissions</p>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Roles</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>admin</td>
                            <td>123</td>
                            <td>ADMIN</td>
                        </tr>
                        <tr>
                            <td>librarian</td>
                            <td>123</td>
                            <td>LIBRARIAN</td>
                        </tr>
                        <tr>
                            <td>both</td>
                            <td>123</td>
                            <td>ADMIN, LIBRARIAN</td>
                        </tr>
                    </tbody>
                </table>
                <div class="form-group">
                    <label for="textInput" class="col-lg-2 control-label">Username</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" id="textInput" placeholder="Username" name="username">
                    </div>
                    <div class="col-lg-6">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-lg-2 control-label">Password</label>
                    <div class="col-lg-4">
                        <input type="password" class="form-control" id="inputPassword" placeholder="Password"
                               name="password">
                    </div>
                    <div class="col-lg-6">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </sec:authorize>
</jsp:attribute>
</my:pagetemplate>
