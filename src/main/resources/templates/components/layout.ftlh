<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">

    <#macro title>
        <#nested>
    </#macro>

    <#macro css>
        <#nested>
    </#macro>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&display=swap" rel="stylesheet">
</head>

<body>
    <#include "header.ftlh">

    <main>
        <#macro layout>
            <#nested>
        </#macro>
    </main>

    <#include "footer.ftlh">
</body>

<#macro js>
    <#nested>
</#macro>

</html>