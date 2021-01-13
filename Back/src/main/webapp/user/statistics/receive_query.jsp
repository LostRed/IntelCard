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
    <title>领卡查询</title>
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
                <h2>查询条件</h2>
            </caption>
            <tbody>
            <tr>
                <td class="mark">申请时间：</td>
                <td>
                    <label>
                        <input id="date1" type="date"/>
                    </label>
                </td>
                <td class="mark">至</td>
                <td>
                    <label>
                        <input id="date2" type="date"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">卡号：</td>
                <td>
                    <label>
                        <input id="cardCode1" type="text" placeholder="例：FZ12345678"/>
                    </label>
                </td>
                <td class="mark">至</td>
                <td>
                    <label>
                        <input id="cardCode2" type="text" placeholder="例：FZ12345678"/>
                    </label>
                </td>
                <td class="blank"></td>
                <td>
                    <button id="approve">查询</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="table-wrap">
        <table>
            <caption>

            </caption>
            <thead>
            <tr>
                <td class="table-col index">序号</td>
                <td class="table-col">卡号</td>
                <td class="table-col">申请时间</td>
                <td class="table-col">审批时间</td>
                <td class="table-col">审批人</td>
                <td class="table-col">卡绑定病人</td>
                <td class="table-col">卡余额</td>
                <td class="table-col">卡状态</td>
            </tr>
            </thead>
            <tbody id="data">
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="page-wrap">
        <button id="pageup">上一页</button>
        <label id="page">1/1</label>
        <button id="pagedown">下一页</button>
    </div>
</div>
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/select.js"></script>
<script src="../../static/js/common/table.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/receive_query.js"></script>
</body>
</html>
