<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/11
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>医生排班</title>
    <link href='../../static/css/calendar.css' rel='stylesheet'/>
    <link href="../../static/css/table.css" rel="stylesheet"/>
    <link href="../../static/css/form.css" rel="stylesheet"/>
    <link href="../../static/css/button.css" rel="stylesheet"/>
    <link href="../../static/css/view.css" rel="stylesheet"/>
</head>
<body>
<div class="container">
    <div class="content-wrap">
        <div id='calendar'></div>
    </div>
    <div id="loading" style="z-index: 10">
        <div class="spinner">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>
    </div>
</div>
<div id="mask" style="z-index: 10"></div>
<div id="insert-div" style="z-index: 10">
    <div id="insert-drag" class="title-wrap drag">
        <h3>新增医生排班</h3>
    </div>
    <div class="form-wrap">
        <table>
            <tbody>
            <tr>
                <td class="mark">当前日期：</td>
                <td>
                    <label id="date"></label>
                </td>
            </tr>
            <tr>
                <td class="mark">医生姓名：</td>
                <td>
                    <label>
                        <select id="doctor">

                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td class="mark">上班时间：</td>
                <td>
                    <label>
                        <select id="workTime">
                            <option value="8:00">8:00-9:00</option>
                            <option value="9:00">9:00-10:00</option>
                            <option value="10:00">10:00-11:00</option>
                            <option value="11:00">11:00-12:00</option>
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
<script src="../../static/js/common/jquery-3.5.1.min.js"></script>
<script src="../../static/js/common/table.js"></script>
<script src="../../static/js/common/select.js"></script>
<script src='../../static/js/common/calendar.min.js'></script>
<script src='../../static/js/common/zh-cn.js'></script>
<script src="../../static/js/doctor.js"></script>
</body>
</html>
