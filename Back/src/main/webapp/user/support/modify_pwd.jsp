<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/6
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>修改密码</title>
    <link href="../../static/css/form.css" rel="stylesheet"/>
    <link href="../../static/css/button.css" rel="stylesheet"/>
    <link href="../../static/css/view.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="content-wrap">
        <div class="form-wrap">
            <table>
                <caption>
                    <h2>修改密码</h2>
                </caption>
                <tbody>
                <tr>
                    <td class="mark">旧密码：</td>
                    <td>
                        <label>
                            <input id="oldPwd" type="password" placeholder="请输入6-8位英文或数字"/>
                        </label>
                    </td>
                    <td>
                        <span id="oldPwdTip"></span>
                    </td>
                </tr>
                <tr>
                    <td class="mark">新密码：</td>
                    <td>
                        <label>
                            <input id="newPwd" type="password" placeholder="请输入6-8位英文或数字"/>
                        </label>
                    </td>
                    <td>
                        <span id="newPwdTip"></span>
                    </td>
                </tr>
                <tr>
                    <td class="mark">确认密码：</td>
                    <td>
                        <label>
                            <input id="confirmPwd" type="password" placeholder="请重复输入密码"/>
                        </label>
                    </td>
                    <td>
                        <span id="confirmPwdTip"></span>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="button-wrap">
                <button id="approve">提交</button>
            </div>
        </div>
    </div>
</div>
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/modify_pwd.js"></script>
</body>
</html>
