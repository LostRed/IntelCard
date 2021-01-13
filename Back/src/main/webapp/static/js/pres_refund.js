//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
let cardBean;
//绑定事件
$('#refund').attr('disabled', true);
$('#refund').css('background-color', 'gray');
$('#refund').click(refund);
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
            resetForm();
            $('#loading').hide();
            if (resp.state === 0) {
                cardBean = resp.data;
                $('#refund').attr('disabled', false);
                $('#refund').css('background-color', '#1cade4');
                $('#refund').attr('oldCard', JSON.stringify(resp.data))
                $('#name').val(resp.data.patientBean.name);
                $('#age').val(resp.data.patientBean.age);
                $('#sex').val(resp.data.patientBean.sex);
                $('#native-place').val(resp.data.patientBean.nativePlace);
                $('#idCard').val(resp.data.patientBean.idCard);
                $('#phone').val(resp.data.patientBean.phone);
                $('#addr').val(resp.data.patientBean.addr);
                $('#amount').val(resp.data.amount);
                $('#deposit').val(resp.data.deposit);
                findPres();
            } else {
                alert("未读取到一卡通卡片信息！该卡不是已出售状态或不存在该卡。");
                resetTable();
            }
        }
    });
}

//重置表格
function resetTable() {
    $('#data').empty();
    for (let i = 0; i < 7; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 7; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
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
        let pres = list[i];
        let $tr = $('<tr></tr>');
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('pres', JSON.stringify(pres));
        $td.append(checkbox);
        $tr.append($td);

        $td = $('<td></td>');
        $td.append(pres.name);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(pres.take);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(pres.num);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(pres.unit);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(pres.price);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(parseInt(pres.num) * parseInt(pres.price));
        $tr.append($td);
        $('#data').append($tr);
    }
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 7; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
}

//查找处方信息
function findPres() {
    $('#loading').css('display', 'flex');
    $("#readCard").off("click");
    $.ajax({
        url: 'pres.do?method=findByCardId',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            cardId: cardBean.cardId
        },
        success: function (resp) {
            $('#loading').hide();
            $('#readCard').click(readCard);
            if (resp.state === 0) {
                refreshPage(resp.pageBean);
                refreshTable(resp.data);
            }
        }
    });
}

//处方退费
function refund() {
    let presBeans = [];
    $(':checkbox[name=select]').each(function () {
        if ($(this).is(':checked')) {
            presBeans.push(JSON.parse($(this).attr("pres")));
        }
    });
    if (presBeans.length === 0) {
        alert("未选中处方记录！");
        return;
    }
    $.ajax({
        url: 'pres.do?method=refund',
        type: 'post',
        dataType: 'json',
        data: {
            card: JSON.stringify(cardBean),
            presBeans: JSON.stringify(presBeans)
        },
        success: function (resp) {
            if (resp.state === 0) {
                $('#selectAll').prop('checked', false);
                alert("退款成功！");
                readCard();
            } else {
                alert("退款失败！");
            }
        }
    });
}