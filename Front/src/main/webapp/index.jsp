<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/11
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>欢迎使用一卡通自助终端</title>
    <link rel="icon" href="static/icon/title.ico"/>
    <link href="static/css/form.css" rel="stylesheet"/>
    <link href="static/css/button.css" rel="stylesheet"/>
    <link href="static/css/index.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="content-wrap">
        <div class="index-block">
            <div><h2>欢迎使用一卡通自助终端</h2></div>
            <div class="index-page">
                <div class="form-wrap">
                    <div class="button-wrap">
                        <button id="recharge">卡充值</button>
                    </div>
                    <div class="button-wrap">
                        <button id="check">卡对账</button>
                    </div>
                    <div class="button-wrap">
                        <button id="register">预约挂号</button>
                    </div>
                    <div class="button-wrap">
                        <button id="offer">预约取号</button>
                    </div>
                    <div class="button-wrap">
                        <button id="presQuery">处方查询</button>
                    </div>
                </div>
                <div class="a-wrap">
                    <a href="/JF200605Back/login.jsp">前往后台系统</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="static/js/common/jquery-3.5.1.min.js"></script>
<script src="static/js/index.js"></script>
</body>
</html>
