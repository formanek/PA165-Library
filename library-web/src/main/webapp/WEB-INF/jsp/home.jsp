<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:pagetemplate title="Welcome to our library application">
<jsp:attribute name="body">
    <p>
        Use the top menu to see the list of members, books or loans and to display more possibilities
        (functionality is restricted based on your user role).
        Only books which can be loaned (or already are loaned) are listed by default.
        Administrators can make books unloanable or return them back to loanable
        (this is used instead of deleting books permanently).
    </p>
    <p>
        To create a new loan, first visit the member detail page.
        After clicking the button, select the books to loan and their current condition.
        Use list of all loans or loans of the specific member to return the loan.
        Do not forget to update the book condition.
    </p>
</jsp:attribute>
</my:pagetemplate>
