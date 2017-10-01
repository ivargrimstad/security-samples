<%-- 
    Document   : login
    Created on : Feb 14, 2016, 2:05:07 PM
    Author     : Ivar Grimstad (ivar.grimstad@gmail.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Soteria MVC</title>
    </head>
    <body>
        <h1>Login</h1>
        <form name="form" action="${mvc.contextPath}/j_security_check" method="post">
            <p>
                <label id="name">Name:</label>
                <input type="text" name="j_username" value="" />
            </p>
            <p>
                <label id="password">Password:</label>
                <input type="password" name="j_password" value=""/>
            </p>
            <p>
                <input type="submit" value="Login" name="button"/>
            </p>
            ${msg}
        </form>
    </body>
</html>
