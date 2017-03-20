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
        <h3>单笔交易查询</h3>
        <form id="form" role="form" action="${contextPath}/service/doTransactionQuery.do" method="post">
          <div class="form-group">
            <label for="requestNo">requestNo</label>
            <input type="text" class="form-control" id="requestNo"
                   name="requestNo" value="" />
          </div>
          <div class="form-group">
            <label for="transactionType">transactionType</label>
            <input type="text" class="form-control" id="transactionType"
              name="transactionType" value="WITHDRAW" />
           </div>  
          <div class="form-group">
          <button type="submit" class="btn btn-default">Submit</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>