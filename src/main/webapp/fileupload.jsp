<%--
  Created by IntelliJ IDEA.
  User: hasee
  Date: 2020/6/3
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>1</title>
</head>
<body>
<form action="/book/update" method="post" enctype="multipart/form-data">
    <label>isbn：</label><input type="text" name="isbn"><br>
    <label>书名：</label><input type="text" name="title"><br>
    <label>作者：</label><input type="text" name="author"><br>
    <label>发行日期：</label><input type="date" name="publishDate"><br>
    <label>上传图片：</label><input type="file" name="file"><br>
    <input type="submit">
</form>
</body>
</html>
