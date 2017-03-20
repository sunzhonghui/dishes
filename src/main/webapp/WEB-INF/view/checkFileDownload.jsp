<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

<title>${context}</title>
<jsp:include page="/pages/head.jsp"></jsp:include>

</head>
<body>
  <div class="container">
    <div class="row clearfix">
      <div class="col-md-12 column">
        <h3>对账文件下载</h3>
        <form id="form" action="${contextPath}/service/doCheckFileDownload.do" method="post">
  			 <div class="form-group">
           <label for="fileDate">fileDate</label>
           <input type="text" class="form-control" id="fileDate" name="fileDate"
  					value="20160226" />
         </div>
         <button type="submit" class="btn btn-default">Submit</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>