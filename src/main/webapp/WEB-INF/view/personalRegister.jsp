<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>个人绑卡注册事例</title>
<%
  String contextPath = request.getContextPath();
  request.setAttribute("contextPath", contextPath);
%>
<link rel="stylesheet"
  href="${contextPath}/static/common/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
  href="${contextPath}/static/common/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet"
  href="${contextPath}/static/common/bootstrap/css/button.css">
<script src="${contextPath}/static/common/jquery1.11/jquery.min.js"></script>
<script src="${contextPath}/static/common/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <h3>个人绑卡注册</h3>
      <form id="form" role="form" method="post" action="${contextPath}/gateway/doPersonalRegister">
        <div class="form-group">
          <label for="platformUserNo">platformUserNo</label>
          <input type="text" class="form-control" id="platformUserNo"
            name="platformUserNo" value="123"/>
        </div>
        <div class="form-group">
          <label for="realName">realName</label>
          <input type="text" class="form-control" id="realName" name="realName" value="小易" />
        </div>
        <div class="form-group">
          <label for="idCardType">idCardType</label>
          <input type="text" class="form-control" id="idCardType" name="idCardType" value="PRC_ID" />
        </div>
        <div class="form-group">
          <label for="idCardNo">idCardNo</label>
          <input type="text" class="form-control" id="idCardNo" name="idCardNo" value="131122196203288683" />
        </div>
        <div class="form-group">
          <label for="mobile">mobile</label>
          <input type="text" class="form-control" id="mobile" name="mobile" value="15811513425" />
        </div>
        <div class="form-group">
          <label for="bankcardNo">bankcardNo</label>
          <input type="text" class="form-control" id="bankcardNo" name="bankcardNo" value="6212860000289999" />
        </div>
        <div class="form-group">
          <label for="callbackUrl">callbackUrl</label>
          <input type="text" class="form-control" id="callbackUrl" name="callbackUrl" value="callbackUrl" />
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
