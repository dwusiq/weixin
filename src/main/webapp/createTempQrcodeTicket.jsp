<%--
  Created by IntelliJ IDEA.
  User: wicker
  Date: 2017/4/28
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
<html>
<head>
    <title>生成二维码ticket</title>
    <script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript" src="js/createTempQrcodeTicket.js"></script>
</head>
<body>
<table>
    <tr>
        <td><a href="index.jsp"> 返回首页</a></td>
    </tr>

</table>
<br/>
<input type="button" value="submit" onclick="createTempQrcodeTicket()"/>
<h4/>
    <div id="content"></div>
</body>
</html>
