<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <%
        String id = (String) request.getAttribute("ids");
        String age = (String) request.getAttribute("ages");           
    %>
    <h4> <%= id %> </h4> 
    <h4> <%= age %> </h4> 
</body>
</html>
