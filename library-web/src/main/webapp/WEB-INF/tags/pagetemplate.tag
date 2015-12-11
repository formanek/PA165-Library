<%-- 
    Document   : pagetemplate
    Created on : 8.12.2015, 11:37:10
    Author     : Milan Skipala
--%>
<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title><c:out value="${title}"/></title>
        <!-- Bootstrap -->        
        <link rel="stylesheet" type="text/css" href="https://bootswatch.com/flatly/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <div class="header-image"></div>
        <!--page navigation starts here-->
        <nav class="navbar navbar-inverse /*navbar-fixed-top*/" id="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Library</a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="${pageContext.request.requestURI.contains("/member") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/member">Members</a></li>
                        <li class="${pageContext.request.requestURI.contains("/book") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/book">Books</a></li>
                        <li class="${pageContext.request.requestURI.contains("/loan") ? 'active' : ''}"><a href="${pageContext.request.contextPath}/loan">Loans</a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Logout (username)</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!--page headline starts here-->
        <div class="container-fluid">
            <div class="page-header" id="banner">
                <div class="row">
                    <div class="col-lg-8 col-md-7 col-sm-6">
                        <h1><c:out value="${title}"/></h1>
                        <p class="lead"></p>
                    </div>
                    <div class="col-lg-4 col-md-5 col-sm-6">
                        <div class="sponsor">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- alerts -->
        <c:if test="${not empty alert_info}">
            <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
        </c:if>

        <!--page content starts here-->

        <div class="container-fluid" id="content">
            <jsp:invoke fragment="body"/>
        </div>
        <!--footer can be placed here--> 
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>