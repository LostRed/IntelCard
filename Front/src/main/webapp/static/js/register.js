//定义当前周的第一天
let currentFirstDate;
//读卡的一卡通
let cardBean;
//绑定事件
$('#lastWeek').attr('disabled', true);
$('#lastWeek').css('background-color', 'gray');
$('#appoint-back').click(function () {
    $('#appoint-div').hide();
    $('#mask').hide();
});
$('#appoint-commit').click(appointCommit);
$('#cancel-commit').click(cancelCommit);
$('#cardCode').focus(function () {
    $(this).removeClass('null');
});
$('#cardCode').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($(this).val())) {
        $(this).addClass('null');
    }
});

//数据初始化
$(function () {
    let formatDate = function (date) {
        let month = (date.getMonth() + 1);
        let day = date.getDate();
        if (month < 10) {
            month = '0' + month;
        }
        if (day < 10) {
            day = '0' + day;
        }
        let week = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'][date.getDay()];
        return month + '月' + day + '日/' + week;
    };
    let addDate = function (date, n) {
        date.setDate(date.getDate() + n);
        return date;
    };
    let setDate = function (date) {
        let week = date.getDay();
        date = addDate(date, week * -1);
        currentFirstDate = new Date(date);
        $('#week td').each(function (i) {
            if (i !== 0) {
                let str = formatDate(i === 1 ? date : addDate(date, 1));
                $(this).empty();
                for (let string of str.split('/')) {
                    let $div = $('<div></div>');
                    $div.append(string);
                    $(this).append($div);
                }
            }
        });
    };
    //绑定上一周按钮事件
    $('#lastWeek').click(function () {
        setDate(addDate(currentFirstDate, -7));
        if (currentFirstDate <= new Date()) {
            $('#lastWeek').attr('disabled', true);
            $('#lastWeek').css('background-color', 'gray');
        }
        findSection();
    });
    //绑定下一周按钮事件
    $('#nextWeek').click(function () {
        setDate(addDate(currentFirstDate, 7));
        if (currentFirstDate > new Date()) {
            $('#lastWeek').attr('disabled', false);
            $('#lastWeek').css('background-color', '#1cade4');
        }
        findSection();
    });
    setDate(new Date());
});
//绑定事件
$('#readCard').click(readCard);
$('#back').click(function () {
    location.href = "index.jsp";
});
//查询科室
findSection();


//以下是封装函数
//查询科室
function findSection() {
    $.ajax({
        url: 'param.do?method=findByType',
        type: 'post',
        dataType: 'json',
        data: {
            paramType: "科室"
        },
        success: function (resp) {
            if (resp.state === 0) {
                addRow(resp.data);
                findAppoint();
            }
        }
    });
}

//根据科室添加行
function addRow(list) {
    $('#data').empty();
    for (let i = 0; i < list.length; i++) {
        let param = list[i];
        let $tr = $('<tr></tr>');
        let $td = $('<td></td>');
        $td.text(param.paramName);
        $tr.append($td);
        for (let j = 0; j < 7; j++) {
            $td = $('<td></td>');
            $tr.append($td);
        }
        $('#data').append($tr);
    }
}

//读卡
function readCard() {
    if ($('#cardCode').val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($('#cardCode').val())) {
        alert("请输入一卡通卡号！");
        return;
    }
    $("#readCard").off("click");
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'card.do?method=findSoldOut',
        type: 'post',
        dataType: 'json',
        data: {
            keyword: $('#cardCode').val().trim().toUpperCase()
        },
        success: function (resp) {
            $('#readCard').click(readCard);
            $('#loading').hide();
            resetForm();
            if (resp.state === 0) {
                cardBean = resp.data;
                $('#name').val(resp.data.patientBean.name);
                $('#age').val(resp.data.patientBean.age);
                $('#sex').val(resp.data.patientBean.sex);
                $('#native-place').val(resp.data.patientBean.nativePlace);
                $('#idCard').val(resp.data.patientBean.idCard);
                $('#phone').val(resp.data.patientBean.phone);
                $('#addr').val(resp.data.patientBean.addr);
                $('#amount').val(resp.data.amount);
                $('#deposit').val(resp.data.deposit);
            } else {
                alert("未读取到一卡通卡片信息！该卡不是已出售状态或不存在该卡。");
            }
        }
    });
}

//重置表单
function resetForm() {
    $('#name').val('');
    $('#age').val('');
    $('#sex').val('');
    $('#native-place').val('');
    $('#idCard').val('');
    $('#phone').val('');
    $('#addr').val('');
    $('#amount').val('');
    $('#deposit').val('');
}

//刷新表格
function refreshTable(list) {
    for (let i = 0; i < list.length; i++) {
        let dayWork = list[i];
        let day = new Date(dayWork.date).getDay();
        $('#data>tr').each(function () {
            if ($(this).children(":first").html() === dayWork.doctor.section.paramName) {
                $(this).find($('td')).each(function (j) {
                    if (j === day + 1) {
                        let $div = $('<div></div>');
                        $div.append(dayWork.doctor.name);
                        $div.attr('doctor', JSON.stringify(dayWork.doctor));
                        $div.attr('date', JSON.stringify(dayWork.date));
                        $div.click(showAppointDiv);
                        $(this).append($div);
                    }
                });
            }
        });
    }
}

//查询排班
function findAppoint() {
    $("#readCard").off("click");
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'appoint.do?method=findByWeek',
        type: 'post',
        dataType: 'json',
        data: {
            currentFirstDate: JSON.stringify(currentFirstDate)
        },
        success: function (resp) {
            $('#readCard').click(readCard);
            $('#loading').hide();
            if (resp.state === 0) {
                refreshTable(resp.data);
            }
        }
    });
}

//显示预约窗口
function showAppointDiv() {
    if (cardBean == null) {
        alert("请先读卡！");
        return;
    }
    $('#appoint-div').show();
    $('#mask').show();
    let doctor = JSON.parse($(this).attr('doctor'));
    $('#appoint-drag h3').html(doctor.name + "医生门诊时间");
    $.ajax({
        url: 'appoint.do?method=findByDoctorAndDate',
        type: 'post',
        dataType: 'json',
        data: {
            doctor: $(this).attr('doctor'),
            date: $(this).attr('date')
        },
        success: function (resp) {
            if (resp.state === 0) {
                refreshWorkTimeTable(resp.data);
            }
        }
    });
}

/**
 * 刷新医生工作时间表格
 * @param list 医生当天的排班数据集合
 */
function refreshWorkTimeTable(list) {
    $('#workTime').empty();
    for (let i = 0; i < list.length; i++) {
        let appoint = list[i];
        let $tr = $('<tr></tr>');
        let $td = $('<td></td>');
        let radio = $('<input name="time" type ="radio"/>');
        radio.attr('appoint', JSON.stringify(appoint));
        if ((appoint.state.paramId !== 19 && appoint.patientBean.patientId !== cardBean.patientBean.patientId)
            || appoint.workTime <= new Date() || appoint.state.paramId === 21) {
            radio.attr('disabled', 'disabled');
        }
        $td.append(radio);
        $tr.append($td);
        $td = $('<td></td>');
        let workTime = new Date(appoint.workTime);
        $td.html(workTime.getHours() + ":00-" + (workTime.getHours() + 1) + ":00");
        $tr.append($td);
        $td = $('<td></td>');
        if (appoint.state.paramId !== 19) {
            $td.html(appoint.patientBean.name);
            $tr.css('color', 'red');
        } else {
            $td.html("");
            $tr.css('color', 'green');
        }
        $tr.append($td);
        $('#workTime').append($tr);
    }
}

//提交预约
function appointCommit() {
    if ($('#appoint-div :radio[name=time]:checked').attr('appoint') == null) {
        alert("未选中记录！");
        return;
    }
    let patient = JSON.parse($('#appoint-div :radio[name=time]:checked').attr('appoint')).patientBean;
    if (patient !== null && patient.patientId === cardBean.patientBean.patientId) {
        alert("您已预约，请不要重复预约！如果要撤销，请点击撤销按钮。");
        return;
    }
    if (confirm("确定预约吗？")) {
        $('#appoint-commit').off('click');
        $.ajax({
            url: 'appoint.do?method=register',
            type: 'post',
            dataType: 'json',
            data: {
                card: JSON.stringify(cardBean),
                appoint: $('#appoint-div :radio[name=time]:checked').attr('appoint')
            },
            success: function (resp) {
                $('#appoint-commit').click(appointCommit);
                if (resp.state === 0) {
                    $('#appoint-div').hide();
                    $('#mask').hide();
                    alert("预约成功！");
                    readCard();
                } else {
                    alert("预约失败！卡余额不足。");
                }
            }
        });
    }
}

//撤销预约
function cancelCommit() {
    if ($('#appoint-div :radio[name=time]:checked').attr('appoint') == null) {
        alert("未选中记录！");
        return;
    }
    if (JSON.parse($('#appoint-div :radio[name=time]:checked').attr('appoint')).patientBean == null) {
        alert("请先预约！");
        return;
    }
    if (confirm("确定撤销预约吗？")) {
        $('#cancel-commit').off('click');
        $.ajax({
            url: 'appoint.do?method=cancel',
            type: 'post',
            dataType: 'json',
            data: {
                card: JSON.stringify(cardBean),
                appoint: $('#appoint-div :radio[name=time]:checked').attr('appoint')
            },
            success: function (resp) {
                $('#cancel-commit').click(cancelCommit);
                if (resp.state === 0) {
                    $('#appoint-div').hide();
                    $('#mask').hide();
                    alert("撤销成功！");
                    readCard();
                } else {
                    alert("撤销失败！");
                }
            }
        });
    }
}