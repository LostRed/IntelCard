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
    <title>卡查询</title>
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
                <td class="mark">卡号：</td>
                <td>
                    <label>
                        <input id="cardCode" type="text"/>
                    </label>
                </td>
                <td class="blank"></td>
                <td class="mark">卡状态：</td>
                <td>
                    <label>
                        <select id="cardState">
                            <option>全部</option>
                        </select>
                    </label>
                </td>
                <td class="blank"></td>
                <td class="mark">领用人：</td>
                <td>
                    <label>
                        <select id="applyUser">
                            <option>全部</option>
                        </select>
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
                <td class="table-col">卡状态</td>
                <td class="table-col">领用人</td>
                <td class="table-col">就诊人</td>
                <td class="table-col operation">操作</td>
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
            </tr>
            <tr>
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
            </tr>
            <tr>
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
<div id="mask"></div>
<div id="query-div">
    <div id="query-drag" class="title-wrap drag">
        <h3>查看卡信息</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">卡号：</td>
                <td colspan="2">
                    <label>
                        <input id="query-cardCode" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">卡状态：</td>
                <td colspan="2">
                    <label>
                        <input id="query-cardState" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">卡余额：</td>
                <td>
                    <label>
                        <input id="query-amount" type="text" readonly="readonly"/>
                    </label>
                </td>
                <td>元</td>
            </tr>
            <tr>
                <td class="mark">就诊人：</td>
                <td colspan="2">
                    <label>
                        <input id="query-patient" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">领用人：</td>
                <td colspan="2">
                    <label>
                        <input id="query-applyUser" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">领用时间：</td>
                <td colspan="2">
                    <label>
                        <input id="query-auditDate" type="date" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">售卡人：</td>
                <td colspan="2">
                    <label>
                        <input id="query-saleUser" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">售卡时间：</td>
                <td colspan="2">
                    <label>
                        <input id="query-saleDate" type="date" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="button-wrap">
        <button id="query-back">返回</button>
    </div>
</div>
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/select.js"></script>
<script src="../../static/js/common/table.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/query.js"></script>
</body>
</html>
