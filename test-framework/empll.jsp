<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String" %>

<html>
<head>
    <title>Liste des attributs</title>
</head>
<body>
    <h1>Liste des attributs :</h1>
    <ul>
        <% 
        List<String> attributNoms=(List<String>) request.getAttribute("list");
        for (String nom : attributNoms) {
        %>
        <li><%= nom %></li>
        <% } %>
    </ul>
</body>
</html>
