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
    <title>一卡通系统自助终端</title>
    <link rel="icon" href="static/icon/title.ico"/>
    <link href="static/css/form.css" rel="stylesheet"/>
    <link href="static/css/table.css" rel="stylesheet"/>
    <link href="static/css/button.css" rel="stylesheet"/>
    <link href="static/css/main.css" rel="stylesheet"/>
    <link href="static/css/navigator.css" rel="stylesheet"/>
    <link href="static/css/register.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="navigator">
        <div class="left">
            <h2>智能一卡通系统</h2>
            <div>欢迎使用一卡通系统自助终端</div>
        </div>
        <div class="right">
            <%--            <div class="four-char"><a>个人中心</a></div>--%>
            <%--            <div class="two-char"><a>帮助</a></div>--%>
            <div class="two-char"><a id="back">返回</a></div>
        </div>
    </div>
    <div class="middle-wrap">
        <div class="content-wrap">
            <div class="form-wrap">
                <table>
                    <caption>
                        <h2>预约挂号</h2>
                    </caption>
                    <tbody>
                    <tr>
                        <td class="mark">请输入卡号：</td>
                        <td>
                            <label>
                                <input id="cardCode" type="text" placeholder="例：FZ12345678"/>
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
                    </tbody>
                </table>
            </div>
            <div class="table-wrap">
                <table>
                    <caption>
                        <div class="button-wrap">
                            <h3>门诊排班表</h3>
                            <div>
                                <button id="lastWeek">上一周</button>
                                <button id="nextWeek">下一周</button>
                            </div>
                        </div>
                    </caption>
                    <thead id="week">
                    <tr>
                        <td class="table-col">科室</td>
                        <td class="table-col"></td>
                        <td class="table-col"></td>
                        <td class="table-col"></td>
                        <td class="table-col"></td>
                        <td class="table-col"></td>
                        <td class="table-col"></td>
                        <td class="table-col"></td>
                    </tr>
                    </thead>
                    <tbody id="data">

                    </tbody>
                </table>
            </div>
            <div id="loading">
                <div class="spinner">
                    <div class="bounce1"></div>
                    <div class="bounce2"></div>
                    <div class="bounce3"></div>
                </div>
            </div>
        </div>
    </div>
    <div id="mask"></div>
    <div id="appoint-div">
        <div id="appoint-drag" class="title-wrap drag">
            <h3></h3>
        </div>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <td class="table-col">选择</td>
                    <td class="table-col">门诊时间</td>
                    <td class="table-col">预约人</td>
                </tr>
                </thead>
                <tbody id="workTime">


                </tbody>
            </table>
        </div>
        <div class="button-wrap">
            <button id="appoint-commit">预约</button>
            <button id="cancel-commit">撤销</button>
            <button id="appoint-back">返回</button>
        </div>
    </div>
</div>
<script src="static/js/common/jquery-3.5.1.min.js"></script>
<script src="static/js/common/form.js"></script>
<script src="static/js/common/table.js"></script>
<script src="static/js/register.js"></script>
</body>
</html>