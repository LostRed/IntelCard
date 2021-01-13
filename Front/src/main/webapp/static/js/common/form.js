/*! 表单通用Javascript */
/**
 * 表单回车切换焦点
 */
$(function () {
    $(".tab input").keypress(function (e) {
        if (e.which === 13) {// 判断所按是否回车键
            let inputs = $(".tab").find(":input[type=text],:input[type=password]"); // 获取表单中的所有输入框
            let idx = inputs.index(this); // 获取当前焦点输入框所处的位置
            if (idx === inputs.length - 1) {// 判断是否是最后一个输入框
                $('#approve').click();
                $('#insert-commit').click();
                $('#update-commit').click();
            } else {
                inputs[idx + 1].focus(); // 设置焦点
            }
            return false;// 取消默认的提交行为
        }
    });
});

/**
 * 获取现在的时间
 * @returns {string} 时间字符串
 */
function now() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1; //一月份为0
    let date = today.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (date < 10) {
        date = '0' + date;
    }
    today = year + "-" + month + "-" + date;
    return today;
}

/**
 * 将时间戳转换成日期(不带时分秒)
 * @param timestamp 时间戳
 * @returns {string} 时间字符串
 */
function formatDate(timestamp) {
    let day = new Date(timestamp);
    let year = day.getFullYear();  //取得4位数的年份
    let month = day.getMonth() + 1;  //取得日期中的月份，其中0表示1月，11表示12月
    let date = day.getDate();      //返回日期月份中的天数（1到31）
    if (month < 10) {
        month = '0' + month;
    }
    if (date < 10) {
        date = '0' + date;
    }
    return year + "-" + month + "-" + date;
}

/**
 * 将时间戳转换成日期
 * @param timestamp 时间戳
 * @returns {string} 时间字符串
 */
function formatTime(timestamp) {
    let day = new Date(timestamp);
    let year = day.getFullYear();  //取得4位数的年份
    let month = day.getMonth() + 1;  //取得日期中的月份，其中0表示1月，11表示12月
    let date = day.getDate();      //返回日期月份中的天数（1到31）
    let hours = day.getHours();
    let min = day.getMinutes();
    let second = day.getSeconds();
    if (month < 10) {
        month = '0' + month;
    }
    if (date < 10) {
        date = '0' + date;
    }
    if (hours < 10) {
        hours = '0' + hours;
    }
    if (min < 10) {
        min = '0' + min;
    }
    if (second < 10) {
        second = '0' + second;
    }
    return year + "-" + month + "-" + date + " " + hours + ":" + min + ":" + second;
}