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
    <title>换卡</title>
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
                    <h2>换卡</h2>
                </caption>
                <tbody>
                <tr>
                    <td class="mark">请输入原卡号/手机号/证件号：</td>
                    <td>
                        <label>
                            <input id="keyword" type="text"/>
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td>
                        <button id="readCard">读卡</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <table>
                <tbody>
                <tr>
                    <td class="mark">姓名：</td>
                    <td>
                        <label>
                            <input id="name" type="text" readonly="readonly"/>
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">年龄：</td>
                    <td>
                        <label><input id="age" type="text" readonly="readonly"/> 岁</label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">性别：</td>
                    <td>
                        <label><input id="sex" type="text" readonly="readonly"/></label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">籍贯：</td>
                    <td>
                        <label><input id="native-place" type="text" readonly="readonly"/></label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">证件号码：</td>
                    <td>
                        <label>
                            <input id="idCard" type="text" readonly="readonly"/>
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">联系电话：</td>
                    <td>
                        <label>
                            <input id="phone" type="text" readonly="readonly"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">现住址：</td>
                    <td colspan="4">
                        <label>
                            <input id="addr" type="text" readonly="readonly"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">卡余额：</td>
                    <td>
                        <label>
                            <input id="amount" type="text" readonly="readonly"/> 元
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">卡押金：</td>
                    <td>
                        <label>
                            <input id="deposit" type="text" readonly="readonly"/> 元
                        </label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">请输入新卡号：</td>
                    <td>
                        <label>
                            <input id="cardCode" type="text" placeholder="例：FZ12345678"/>
                        </label>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="button-wrap">
                <button id="approve">换卡</button>
            </div>
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
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/change.js"></script>
</body>
</html>
