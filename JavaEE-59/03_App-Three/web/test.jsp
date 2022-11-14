<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: sanu
  Date: 2022-11-14
  Time: 2:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello JSP - How are you..?</h1>

<%--Scriplet--%>
<%
    String name="IJSE";
    int age=16;
    System.out.println("Hello JSP");
%>

<%--Scriplet--%>
<%
    ArrayList<String> arrayList= new ArrayList<>();
    arrayList.add("IJSE");
    arrayList.add("CMJD");
%>

<%--Expression--%>
<%=name%>

<%--Declaration--%>
<%! String address="Galle";%>
<%! int salary=1000;%>

<h1><%=name%></h1>
<h1><%=age%></h1>

</body>
</html>
