<%--
  Created by IntelliJ IDEA.
  User: wicker
  Date: 2017/3/31
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>登陆页</title>
    <script type="text/javascript" src="js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript" src="js/index.js"></script>
  </head>
  <body>
     <form>
       <table>
         <tr>
           <td>username</td>
           <td><input type="text" id="username" name="username"/></td>
         </tr>
         <tr>
           <td>userpwd</td>
           <td><input type="text" id="userpwd" name="userpwd"/></td>
         </tr>
       </table>
     </form>
     <input type="button" value="submit" onclick="doLogin()"/>
  </body>
</html>