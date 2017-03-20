<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
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