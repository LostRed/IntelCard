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
    <title>售卡</title>
    <link href="../../static/css/form.css" rel="stylesheet"/>
    <link href="../../static/css/button.css" rel="stylesheet"/>
    <link href="../../static/css/view.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="content-wrap">
        <div class="form-wrap tab">
            <table>
                <caption>
                    <h2>售卡</h2>
                </caption>
                <tbody>
                <tr>
                    <td class="mark">姓名：</td>
                    <td>
                        <label>
                            <input id="name" type="text" placeholder="请输入就诊人姓名"/>
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">年龄：</td>
                    <td>
                        <label><input id="age" type="text" maxlength="3"
                                      oninput="value=value.replace(/[^0-9]/g,'')"/> 岁</label>
                        <label><input id="week" type="text" maxlength="3"
                                      oninput="value=value.replace(/[^0-9]/g,'')"/> 周</label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">性别：</td>
                    <td>
                        <label><input name="sex" type="radio" value="男" checked="checked"/>男</label>
                        <label><input name="sex" type="radio" value="女"/>女</label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">籍贯：</td>
                    <td>
                        <label>
                            <select id="native-place">
                                <option>福建省</option>
                                <option>浙江省</option>
                                <option>广东省</option>
                            </select>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">证件号码：</td>
                    <td>
                        <label>
                            <input id="idCard" type="text" placeholder="请输入就诊人身份证号码"/>
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">联系电话：</td>
                    <td>
                        <label>
                            <input id="phone" type="text" placeholder="请输入联系电话"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">现住址：</td>
                    <td colspan="4">
                        <label>
                            <input id="addr" type="text"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td class="mark">预存金额：</td>
                    <td>
                        <label>
                            <input id="amount" type="text" oninput="value=value.replace(/^[^1-9]|[^0-9]/g,'')"
                                   placeholder="请输入大于0的整数"/>
                        </label>
                    </td>
                    <td class="blank"></td>
                    <td class="mark">卡号：</td>
                    <td>
                        <label>
                            <input id="cardCode" type="text" placeholder="例：FZ12345678"/>
                        </label>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="button-wrap">
                <button id="approve">出售</button>
            </div>
        </div>
    </div>
</div>
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/sale.js"></script>
</body>
</html>
