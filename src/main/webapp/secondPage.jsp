<%--
  Created by IntelliJ IDEA.
  User: wicker
  Date: 2017/4/26
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
<html>
<head>
    <title>登录成功页</title>
    <script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript" src="js/second.js"></script>
</head>
<body>
<form>
    <table>
        <tr>
            <td>username</td>
            <td><input type="text" id="condition" name="condition"/></td>
        </tr>
    </table>
</form>
<input type="button" value="submit" onclick="queryMessage()"/>
</body>
</html>