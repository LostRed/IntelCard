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
    <title>领卡</title>
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
                <td class="mark">申请日期：</td>
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
                <td class="blank"></td>
                <td class="mark">审核状态：</td>
                <td>
                    <label>
                        <select id="applyState">
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
                <button id="delete">撤销</button>
                <button id="insert">新申请</button>
            </caption>
            <thead>
            <tr>
                <td class="table-col select">
                    <label>
                        <input id="selectAll" type="checkbox"/>
                    </label>
                </td>
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
<div id="insert-div">
    <div id="insert-drag" class="title-wrap drag">
        <h3>申请单</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">申请人：</td>
                <td colspan="2">
                    <label>
                        <input id="insert-applyName" type="text" readonly="readonly"/>
                    </label>
                </td>
                <td>
                    <span id="insert-applyNameTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">申请时间：</td>
                <td colspan="2">
                    <label>
                        <input id="insert-date" type="date" readonly="readonly"/>
                    </label>
                </td>
                <td>
                    <span id="insert-dateTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">申请卡数量：</td>
                <td>
                    <label>
                        <input id="insert-applyNum" type="text" placeholder="请输入大于0的整数"/>
                    </label>
                </td>
                <td>张</td>
                <td>
                    <span id="insert-applyNumTip"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="button-wrap">
        <button id="insert-commit">提交</button>
        <button id="insert-back">返回</button>
    </div>
</div>
<div id="update-div">
    <div id="update-drag" class="title-wrap drag">
        <h3>修改申请</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">申请人：</td>
                <td colspan="2">
                    <label>
                        <input id="update-applyName" type="text" readonly="readonly"/>
                    </label>
                </td>
                <td>
                    <span id="update-applyNameTip"></span>
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
                        <input id="update-applyNum" type="text" placeholder="请输入数字"/>
                    </label>
                </td>
                <td>张</td>
                <td>
                    <span id="update-applyNumTip"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="button-wrap">
        <button id="update-commit">提交</button>
        <button id="update-back">返回</button>
    </div>
</div>
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/select.js"></script>
<script src="../../static/js/common/table.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/receive.js"></script>
</body>
</html>
