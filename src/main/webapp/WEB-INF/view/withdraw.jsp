<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

<title>Insert title here</title>
<jsp:include page="/pages/head.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<h3>提现</h3>
				<form id="form" action="${contextPath}/gateway/doWithdraw.do" method="post">
					<div class="form-group">
						<label for="platformUserNo">platformUserNo</label>
						<input type="text" class="form-control" id="platformUserNo"
							name="platformUserNo" value="sun01"/>
					</div>
					<div class="form-group">
						<label for="amount">amount</label>
            <input type="text" class="form-control" id="amount" name="amount" value="1" />
					</div>
					<div class="form-group">
						<label for="feeMode">feeMode</label>
            <input type="text" class="form-control" id="feeMode" name="feeMode" value="PLATFORM" />
					</div>
					<div class="form-group">
						<label for="withdrawType">withdrawType</label>
            <input type="text" class="form-control" id="withdrawType" name="withdrawType" value="NORMAL" />
					</div>
					<div class="form-group">
						<label for="callbackUrl">callbackUrl</label>
            <input type="text" class="form-control" id="callbackUrl" name="callbackUrl"
							value="callbackUrl" />
					</div>
          <button type="submit" class="btn btn-default">Submit</button>
        </form>
			</div>
		</div>
	</div>
</body>
</html>