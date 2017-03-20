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
        <h3>创建标的</h3>
        <form id="form" role="form" action="${contextPath}/service/doEstablishProject.do" method="post">
          <div class="form-group">
            <label for="platformUserNo">platformUserNo</label>
            <input type="text" class="form-control" id="platformUserNo"
              name="platformUserNo" value="tender0" />
          </div>
          <div class="form-group">
            <label for="projectNo">projectNo</label>
            <input type="text" class="form-control" id="projectNo" name="projectNo" value="111" />
          </div>
          <div class="form-group">
            <label for="projectType">projectType</label>
            <input type="text" class="form-control" id="projectType" name="projectType" value="STANDARDPOWDER" />
          </div>
          <div class="form-group">
            <label for="projectName">projectName</label>
            <input type="text" class="form-control" id="projectName" name="projectName" value="标的名称" />
          </div>
          <div class="form-group">
            <label for="projectAmount">projectAmount</label>
            <input type="text" class="form-control" id="projectAmount" name="projectAmount" value="1000" />
          </div>
          <div class="form-group">
            <label for="annnualInterestRate">annnualInterestRate</label>
            <input type="text" class="form-control" id="annnualInterestRate" name="annnualInterestRate" value="0.07" />
          </div>
          <div class="form-group">
            <label for="repaymentWay">repaymentWay</label>
            <input type="text" class="form-control" id="repaymentWay" name="repaymentWay" value="FIXED_BASIS_MORTGAGE" />
          </div>
          <button type="submit" class="btn btn-default">Submit</button>
          </form>
      </div>
    </div>
  </div>
</body>
</html>