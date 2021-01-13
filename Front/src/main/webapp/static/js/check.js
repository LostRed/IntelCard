//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
let cardBean;
//绑定事件
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
                findRecord();
            } else {
                alert("未读取到一卡通卡片信息！该卡不是已出售状态或不存在该卡。");
                resetTable();
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
function resetTable() {
    $('#data').empty();
    for (let i = 0; i < 5; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 5; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
}

//刷新表格
function refreshTable(list) {
    $('#data').empty();
    for (let i = 0; i < list.length; i++) {
        let record = list[i];
        let $tr = $('<tr></tr>');
        //序号
        let $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //消费记录时间
        $td = $('<td></td>');
        $td.append(formatTime(record.recordTime));
        $tr.append($td);
        //记录事项
        $td = $('<td></td>');
        $td.append(record.name.paramName);
        $tr.append($td);
        //记录金额变动
        $td = $('<td></td>');
        $td.append(record.amount);
        $tr.append($td);
        //执行人
        $td = $('<td></td>');
        if (record.actionUser.name !== null) {
            $td.append(record.actionUser.name);
        } else {
            $td.append(cardBean.patientBean.name);
        }
        $tr.append($td);
        $('#data').append($tr);
    }
    //填充剩余行数
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 5; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
}

//查询消费记录
function findRecord() {
    $("#readCard").off("click");
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'record.do?method=findByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            cardId: cardBean.cardId
        },
        success: function (resp) {
            $('#readCard').click(readCard);
            $('#loading').hide();
            if (resp.state === 0) {
                refreshPage(resp.pageBean);
                refreshTable(resp.data);
            }
        }
    });
}