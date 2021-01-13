<%--
  Created by IntelliJ IDEA.
  User: LostRed
  Date: 2020/11/5
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>智能一卡通系统</title>
    <link rel="icon" href="../static/icon/title.ico"/>
    <link href="../static/css/button.css" rel="stylesheet"/>
    <link href="../static/css/main.css" rel="stylesheet"/>
    <link href="../static/css/menu.css" rel="stylesheet"/>
    <link href="../static/css/navigator.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="navigator">
        <div class="left">
            <h2>智能一卡通系统</h2>
            <div>欢迎回来，${loginUser.name}</div>
        </div>
        <div class="right">
<%--            <div class="four-char"><a>个人中心</a></div>--%>
<%--            <div class="two-char"><a>帮助</a></div>--%>
            <div class="two-char"><a id="exit">退出</a></div>
        </div>
    </div>
    <div class="middle-wrap">
        <div class="menu-wrap">
            <ul id="menu">

            </ul>
        </div>
        <div class="content-wrap">
            <iframe id="iframe" name="view" src=""></iframe>
            <div id="loading">
                <div class="spinner">
                    <div class="bounce1"></div>
                    <div class="bounce2"></div>
                    <div class="bounce3"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="mask"></div>
<div id="exit-div">
    <div id="exit-drag" class="title-wrap drag">
        <h3>是否确定退出系统？</h3>
    </div>
    <div class="button-wrap">
        <button id="sure">确定</button>
        <button id="cancel">取消</button>
    </div>
</div>
<script src="../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../static/js/common/select.js"></script>
<script src="../static/js/menu.js"></script>
<script src="../static/js/main.js"></script>
</body>
</html>