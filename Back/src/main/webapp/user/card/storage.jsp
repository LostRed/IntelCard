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
    <title>卡入库</title>
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
                <td class="mark">卡状态：</td>
                <td>
                    <label>
                        <select id="cardState">
                            <option>全部</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">入库日期：</td>
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
                <td colspan="3"></td>
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
                <button id="delete">删除</button>
                <button id="insert">卡入库</button>
            </caption>
            <thead>
            <tr>
                <td class="table-col select">
                    <label>
                        <input id="selectAll" type="checkbox"/>
                    </label>
                </td>
                <td class="table-col index">序号</td>
                <td class="table-col">卡号</td>
                <td class="table-col">入库日期</td>
                <td class="table-col">卡状态</td>
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
<div id="insert-div">
    <div id="insert-drag" class="title-wrap drag">
        <h3>卡入库</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">开始卡号：</td>
                <td>
                    <label>
                        <input id="cardCodeStart" type="text" placeholder="请输入1-8位数字"/>
                    </label>
                </td>
                <td>
                    <span id="cardCodeStartTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">截止卡号：</td>
                <td>
                    <label>
                        <input id="cardCodeEnd" type="text" placeholder="请输入1-8位数字"/>
                    </label>
                </td>
                <td>
                    <span id="cardCodeEndTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">前缀：</td>
                <td>
                    <label>
                        <input id="prefix" type="text" placeholder="请输入1-5位英文字母"/>
                    </label>
                </td>
                <td>
                    <span id="prefixTip"></span>
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
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/select.js"></script>
<script src="../../static/js/common/table.js"></script>
<script src="../../static/js/common/form.js"></script>
<script src="../../static/js/storage.js"></script>
</body>
</html>
