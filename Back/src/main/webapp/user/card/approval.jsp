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
    <title>领卡审批</title>
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
                <td class="mark">申请人：</td>
                <td>
                    <label>
                        <select id="applyUser">
                            <option>全部</option>
                        </select>
                    </label>
                </td>
                <td class="blank"></td>
                <td class="mark">审核状态：</td>
                <td>
                    <label>
                        <select id="applyState">
                            <option>全部</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">申请日期：</td>
                <td>
                    <label>
                        <input id="date1" type="date"/>
                    </label>
                </td>
                <td id="to" class="mark" colspan="2">至</td>
                <td>
                    <label>
                        <input id="date2" type="date"/>
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
                <td class="table-col">申请日期</td>
                <td class="table-col">申请卡数量</td>
                <td class="table-col">申请人</td>
                <td class="table-col">审核状态</td>
                <td class="table-col">审核人</td>
                <td class="table-col">审核时间</td>
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
<div id="mask"></div>
<div id="update-div">
    <div id="update-drag" class="title-wrap drag">
        <h3>领卡审核</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">申请人：</td>
                <td colspan="2">
                    <label>
                        <input id="update-applyUser" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">申请时间：</td>
                <td colspan="2">
                    <label>
                        <input id="update-date" type="date" readonly="readonly"/>
                    </label>
                </td>
                <td>
                    <span id="update-dateTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">申请卡数量：</td>
                <td>
                    <label>
                        <input id="update-applyNum" type="text" readonly="readonly"/>
                    </label>
                </td>
                <td>张</td>
                <td>
                    <span id="update-applyNumTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">请输入开始卡号：</td>
                <td colspan="2">
                    <label>
                        <input id="cardCodeStart" type="text" placeholder="例：FZ12345678"/>
                    </label>
                </td>
                <td>
                    <span id="cardCodeStartTip"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="button-wrap">
        <button id="update-commit">审核通过</button>
        <button id="update-back">返回</button>
    </div>
</div>
<div id="query-div">
    <div id="query-drag" class="title-wrap drag">
        <h3>查看</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">申请人：</td>
                <td colspan="2">
                    <label>
                        <input id="query-applyUser" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">申请时间：</td>
                <td colspan="2">
                    <label>
                        <input id="query-applyDate" type="date" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">申请卡数量：</td>
                <td>
                    <label>
                        <input id="query-applyNum" type="text" readonly="readonly"/>
                    </label>
                </td>
                <td>张</td>
            </tr>
            <tr>
                <td class="mark">审核人：</td>
                <td colspan="2">
                    <label>
                        <input id="query-auditUser" type="text" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">审核时间：</td>
                <td colspan="2">
                    <label>
                        <input id="query-auditDate" type="date" readonly="readonly"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">领用卡号：</td>
                <td colspan="2">
                    <select id="query-cardCode">

                    </select>
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
<script src="../../static/js/approval.js"></script>
</body>
</html>
