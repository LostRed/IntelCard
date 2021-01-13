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
    <title>人员管理</title>
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
                <td class="mark">人员姓名：</td>
                <td>
                    <label>
                        <input id="name" type="text"/>
                    </label>
                </td>
                <td class="blank"></td>
                <td class="mark">科室：</td>
                <td>
                    <label>
                        <select id="section">
                            <option>全部</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">角色：</td>
                <td>
                    <label>
                        <select id="role">
                            <option>全部</option>
                        </select>
                    </label>
                </td>
                <td class="blank"></td>
                <td class="mark">状态：</td>
                <td>
                    <label>
                        <select id="userState">
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
                <button id="delete">删除</button>
                <button id="insert">添加</button>
            </caption>
            <thead>
            <tr>
                <td class="table-col select">
                    <label>
                        <input id="selectAll" type="checkbox"/>
                    </label>
                </td>
                <td class="table-col index">序号</td>
                <td class="table-col">人员姓名</td>
                <td class="table-col">科室</td>
                <td class="table-col">角色</td>
                <td class="table-col">状态</td>
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
            </tr>
            <tr>
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
            </tr>
            <tr>
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
        <h3>新增人员</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">人员姓名：</td>
                <td>
                    <label>
                        <input id="insert-name" type="text" placeholder="请输入人员姓名"/>
                    </label>
                </td>
                <td>
                    <span id="insert-nameTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">密码：</td>
                <td>
                    <label>
                        <input id="password" type="password" placeholder="请输入6-8位英文或数字"/>
                    </label>
                </td>
                <td>
                    <span id="passwordTip"></span>
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
            <tr>
                <td class="mark">所属科室：</td>
                <td>
                    <label>
                        <select id="insert-section">

                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">所属角色：</td>
                <td>
                    <label>
                        <select id="insert-role">

                        </select>
                    </label>
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
        <h3>修改人员</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">人员姓名：</td>
                <td>
                    <label>
                        <input id="update-name" type="text" readonly="readonly" placeholder="请输入人员姓名"/>
                    </label>
                </td>
                <td>
                    <span id="update-nameTip"></span>
                </td>
            </tr>
            <tr>
                <td class="mark">所属科室：</td>
                <td>
                    <label>
                        <select id="update-section">

                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">所属角色：</td>
                <td>
                    <label>
                        <select id="update-role">

                        </select>
                    </label>
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
<script src="../../static/js/user.js"></script>
</body>
</html>
