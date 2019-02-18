<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Binding Form</title>
</head>
<body>
<h1 id="error">${error}</h1>
<form id="form" action="binding" method="post">
    <input id="valAInput" type="text" name="valA">
    <input id="valBInput" type="text" name="valB">
    <input type="submit" name="submit" value="Click here"/>
</form>
</body>
</html>
