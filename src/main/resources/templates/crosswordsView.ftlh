<!DOCTYPE html>
<html lang="ru">
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
</#list>
</body>
</html>