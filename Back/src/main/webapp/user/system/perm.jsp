<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/6
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>权限配置</title>
    <link href="../../static/css/perm.css" rel="stylesheet"/>
    <link href="../../static/css/button.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="content-wrap">
        <div id="role-wrap" class="menu-wrap">
            <ul>
                <li>
                    <a><label></label><span>角色</span></a>
                    <ul id="role">

                    </ul>
                </li>
            </ul>
        </div>
        <div class="menu-wrap">
            <ul>
                <li>
                    <a><label></label><span>已分配菜单</span></a>
                    <ul id="hasMenu">

                    </ul>
                </li>
            </ul>
        </div>
        <div class="button-wrap">
            <div>
                <button id="btnAddAll"><<</button>
            </div>
            <div>
                <button id="btnAdd"><</button>
            </div>
            <div>
                <button id="btnRemoveAll">>></button>
            </div>
            <div>
                <button id="btnRemove">></button>
            </div>
        </div>
        <div class="menu-wrap">
            <ul>
                <li>
                    <a><label></label><span>未分配菜单</span></a>
                    <ul id="hasNotMenu">

                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <div id="loading">
        <div class="spinner">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>
    </div>
</div>
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/perm.js"></script>
</body>
</html>
