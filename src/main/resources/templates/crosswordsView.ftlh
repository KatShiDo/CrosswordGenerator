<#include "components/layout.ftlh">

<@title>
    <title>Создать кроссворд</title>
</@title>

<@css>
    <link type="text/css" rel="stylesheet" href="indexstyle.css">
</@css>

<@layout>
    <h1>CrosswordGenerator ||||| <a href="/login">Войти</a></h1><hr>
    <h4>Кроссворды</h4>
    <#list crosswords as crossword>
        <div>
            <p><b>${crossword.title}</b> ${crossword.user} | <a href="/crossword/${crossword.id}">Решить</a></p>
        </div>
    <#else>
        <h3>Кроссвордов нет</h3>
    </#list>
</@layout>

<@js>
    <script src="indexscript.js"></script>
</@js>