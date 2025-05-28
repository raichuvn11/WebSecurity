<%--
    Document   : enterOtp
    Created on : Nov 11, 2024, 9:34:23 PM
    Author     : MyPC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String cspNonce = (String) request.getAttribute("cspNonce"); %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Enter OTP</title>
    <link
            href="../css/bootstrap.mine.css"
            rel="stylesheet" id="bootstrap-css">

    <script
            src="../js/bootstrap.min.js"></script>
    <script src="../js/jquery-1.11.1.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <link rel="stylesheet"
          href="../css/font-awesome.min.css">

    <style type="text/css" nonce="<%= cspNonce %>">
        .form-gap {
            padding-top: 70px;
        }

    </style>
</head>
<body>
<div class="form-gap"></div>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center">
                        <h3>
                            <i class="fa fa-lock fa-4x"></i>
                        </h3>
                        <h2 class="text-center">Nhập OTP</h2>
                        <%
                            if (request.getAttribute("message") != null) {
                                out.print("<div class='alert alert-danger mt-3'>" + request.getAttribute("message") + "</div>");
                            }
                        %>

                        <div class="panel-body">
                            <form id="register-form" action="../ValidateOtp" role="form" autocomplete="off" class="form" method="post">
                                <input type="hidden" name="action" value="XacThucOTP">
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                                        <input id="opt" name="otp" placeholder="Enter OTP" class="form-control" type="text" required="required">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="Đổi mật khẩu " type="submit">
                                </div>
                                <input type="hidden" class="hide" name="token" id="token" value="">
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
