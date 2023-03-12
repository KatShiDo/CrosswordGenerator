<!DOCTYPE html>
<html lang="en">
<head>
    <title>CrosswordGenerator</title>
</head>
<body>
<h1>CrosswordGenerator</h1><hr>
<h4>Кроссворды</h4>
<#list crosswords as crossword>
    <div>
        <p><b>${crossword.title}</b> ${crossword.user} | <a href="/crossword/${crossword.id}">Решить</a></p>
    </div>
    <#else>
    <h3>Кроссвордов нет</h3>
</#list>
</body>
</html>