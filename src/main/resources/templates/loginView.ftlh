<!DOCTYPE html>
<html>
<head>
    <title>CrosswordGenerator</title>
</head>
<body>
<h1>CrosswordGenerator</h1>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Nickname: <input type="text" name="username"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Enter">
</form>
<a href="/registration">Зарегистрироваться</a>
</body>
</html>