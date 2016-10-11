<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: en
  Date: 2016/8/20
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<s:property value="message"/>
${pageContext}
<s:iterator value="userlist">
    <s:property value="id"/>
    <s:property value="username"/>
    <s:property value="password"/>
   <br/>
</s:iterator>
</body>
</html>
