<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/6
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>处方登记</title>
    <link href="../../static/css/table.css" rel="stylesheet"/>
    <link href="../../static/css/form.css" rel="stylesheet"/>
    <link href="../../static/css/button.css" rel="stylesheet"/>
    <link href="../../static/css/view.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="form-wrap">

        <table>
            <caption>
                <h2>处方登记</h2>
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
                <div class="pres-wrap">
                    <h3>处方内容</h3>
                    <div>
                        <span>处方合计：</span>
                        <span id="total">0</span>
                        <span>元</span>
                    </div>
                </div>
            </caption>
            <thead>
            <tr>
                <td class="table-col">药品名称</td>
                <td class="table-col">服用方式</td>
                <td class="table-col">数量</td>
                <td class="table-col">单位</td>
                <td class="table-col">单价(元)</td>
                <td class="table-col">小计(元)</td>
            </tr>
            <tr>
                <td>
                    <label>
                        <input id="pres-name" type="text"/>
                    </label>
                </td>
                <td>
                    <label>
                        <input id="pres-take" type="text"/>
                    </label>
                </td>
                <td>
                    <label>
                        <input id="pres-num" type="text"/>
                    </label>
                </td>
                <td>
                    <label>
                        <input id="pres-unit" type="text"/>
                    </label>
                </td>
                <td>
                    <label>
                        <input id="pres-price" type="text"/>
                    </label>
                </td>
                <td>
                    <label>
                        <input id="pres-cal" type="text" disabled="disabled"/>
                    </label>
                </td>
            </tr>
            </thead>
            <tbody id="data">

            </tbody>
        </table>
    </div>
    <div class="save-wrap">
        <button id="save">保存处方</button>
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
<script src="../../static/js/common/select.js"></script>
<script src="../../static/js/common/table.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/pres_reg.js"></script>
</body>
</html>
