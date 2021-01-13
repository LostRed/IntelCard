<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/5
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>智能一卡通系统</title>
    <link rel="icon" href="static/icon/title.ico"/>
    <link href="static/css/form.css" rel="stylesheet"/>
    <link href="static/css/button.css" rel="stylesheet"/>
    <link href="static/css/login.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="content-wrap">
        <div class="login-block">
            <div><h2>智能一卡通系统</h2></div>
            <div class="login-page">
                <div class="form-wrap tab">
                    <table>
                        <tbody>
                        <tr>
                            <td class="mark">用户名：</td>
                            <td>
                                <label>
                                    <input id="username" type="text" placeholder="请输入用户名"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td class="mark">密码：</td>
                            <td>
                                <label>
                                    <input id="password" type="password" placeholder="请输入密码"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td class="mark">验证码：</td>
                            <td>
                                <label>
                                    <input id="verifyCode" type="text" placeholder="请输入验证码"/>
                                </label>
                                <label>
                                    <img id="imageCode" src="imageCode" alt="验证码">
                                </label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="button-wrap">
                        <button id="approve">登录</button>
                    </div>
                    <div class="a-wrap">
                        <a href="/JF200605Front/index.jsp">前往前台系统</a>
                    </div>
                </div>
                <div id="loading">
                    <div class="spinner">
                        <div class="spinner-container container1">
                            <div class="circle1"></div>
                            <div class="circle2"></div>
                            <div class="circle3"></div>
                            <div class="circle4"></div>
                        </div>
                        <div class="spinner-container container2">
                            <div class="circle1"></div>
                            <div class="circle2"></div>
                            <div class="circle3"></div>
                            <div class="circle4"></div>
                        </div>
                        <div class="spinner-container container3">
                            <div class="circle1"></div>
                            <div class="circle2"></div>
                            <div class="circle3"></div>
                            <div class="circle4"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="static/js/common/jquery-3.5.1.min.js"></script>
<script src="static/js/common/form.js"></script>
<script src="static/js/login.js"></script>
</body>
</html>
