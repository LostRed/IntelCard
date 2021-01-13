//定义全局一卡通对象
let cardBean;
//初始化按钮
$('#offer').attr('disabled', true);
$('#offer').css('background-color', 'gray');
//绑定事件
$('#offer').click(offer);
$('#selectAll').change(selectAll);
$('#readCard').click(readCard);
$('#back').click(function () {
    location.href = "index.jsp";
});
$('#cardCode').focus(function () {
    $(this).removeClass('null');
});
$('#cardCode').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($(this).val())) {
        $(this).addClass('null');
    }
});

//以下是封装函数
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
                findAppoint();
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
    $('#data').empty();
    for (let i = 0; i < list.length; i++) {
        let appoint = list[i];
        let $tr = $('<tr></tr>');
        //复选框
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('appointId', appoint.appointId);
        $td.append(checkbox);
        $tr.append($td);
        //预约时间
        $td = $('<td></td>');
        $td.append(formatDate(appoint.appointTime));
        $tr.append($td);
        //工作时间
        $td = $('<td></td>');
        $td.append(formatDate(appoint.workTime));
        $tr.append($td);
        //医生姓名
        $td = $('<td></td>');
        $td.append(appoint.doctor.name);
        $tr.append($td);
        //科室
        $td = $('<td></td>');
        $td.append(appoint.doctor.section.paramName);
        $tr.append($td);
        $('#data').append($tr);
    }
}

//查询排班
function findAppoint() {
    $("#readCard").off("click");
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'appoint.do?method=findAppointByPatientId',
        type: 'post',
        dataType: 'json',
        data: {
            patientId: cardBean.patientBean.patientId
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

//取号
function offer() {
    let appointIds = [];
    $(':checkbox[name=select]').each(function () {
        if ($(this).is(':checked')) {
            appointIds.push($(this).attr("appointId"));
        }
    });
    if (appointIds.length === 0) {
        alert("未选中预约记录！");
        return;
    }
    if (confirm("确定取号吗？")) {
        $('#offer').off('click');
        $.ajax({
            url: 'appoint.do?method=offer',
            type: 'post',
            dataType: 'json',
            data: {
                appointIds: JSON.stringify(appointIds)
            },
            success: function (resp) {
                $('#offer').click(offer);
                if (resp.state === 0) {
                    $('#selectAll').prop('checked', false);
                    alert("取号成功！");
                    findAppoint();
                } else {
                    alert("取号失败！");
                }
            }
        });
    }
}
