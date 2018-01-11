<%-- 
    Document   : hello.jsp
    Created on : Feb 11, 2016, 2:23:16 PM
    Author     : Ivar Grimstad (ivar.grimstad@gmail.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="${mvc.contextPath}/img/favicon.ico" type="image/x-icon" />
        <title>Soteria MVC</title>
    </head>
    <body>
        <h1>Hello</h1>
        
        Web username: <b>${user}</b><br/>
        Web user has role "foo": <b>${hasFoo}</b><br/>
        Web user has role "bar": <b>${hasBar}</b><br/>
        Web user has role "kaz": <b>${hasKaz}</b><br/>
        
    </body>
</html>
