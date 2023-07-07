<%@ page import="java.utils.*" %>
<%
    List<String> data = (List<String>)request.getAttribute("data");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>test    </h1>
    <%
        for(String s : data){ %>
            <p><%= s %></p>
        <% }
    %>
</body>
</html>