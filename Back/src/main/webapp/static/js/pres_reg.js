//绑定事件
$('#readCard').click(readCard);
$('#save').attr('disabled', true);
$('#save').css('background-color', 'grey');
$('#save').click(save);
$('#cardCode').focus(function () {
    $(this).removeClass('null');
});
$('#cardCode').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($(this).val())) {
        $(this).addClass('null');
    }
});
$('#pres-price').blur(function () {
    let num = $('#pres-num').val();
    let price = $('#pres-price').val();
    $('#pres-cal').val(num * price);
});
$('#pres-num').blur(function () {
    let num = $('#pres-num').val();
    let price = $('#pres-price').val();
    $('#pres-cal').val(num * price);
});

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
                $('#save').attr('disabled', false);
                $('#save').css('background-color', '#1cade4');
                $('#save').attr('oldCard', JSON.stringify(resp.data))
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
function refreshTable() {
    let $tr = $('<tr>');
    let $td = $('<td>');
    $td.html($('#pres-name').val());
    $tr.append($td);
    $td = $('<td>');
    $td.html($('#pres-take').val());
    $tr.append($td);
    $td = $('<td>');
    $td.html($('#pres-num').val());
    $tr.append($td);
    $td = $('<td>');
    $td.html($('#pres-unit').val());
    $tr.append($td);
    $td = $('<td>');
    $td.html($('#pres-price').val());
    $tr.append($td);
    $td = $('<td>');
    $td.html($('#pres-cal').val());
    $td.addClass('pres-cal');
    $tr.append($td);
    $('#data').append($tr);
    $('#pres-name').val('');
    $('#pres-take').val('');
    $('#pres-num').val('');
    $('#pres-unit').val('');
    $('#pres-price').val('');
    $('#pres-cal').val('');
}

//保存处方
function save() {
    let cardBean = JSON.parse($(this).attr('oldCard'));
    let name = $('#pres-name').val();
    let take = $('#pres-take').val();
    let num = $('#pres-num').val();
    let unit = $('#pres-unit').val();
    let price = $('#pres-price').val();
    if (name === '' || take === '' || num === '' || unit === '' || price === '') {
        alert('请输入完整的处方信息！');
        return;
    }
    if (!/^[0-9]*$/.test(num)) {
        alert("数量请输入数字！");
        return;
    }
    if (!/^[0-9]*$/.test(price)) {
        alert("单价请输入数字！");
        return;
    }
    let presBean = {
        presId: -1,
        cardBean: cardBean,
        name: name,
        take: take,
        num: num,
        unit: unit,
        price: price,
        createTime: null,
        state: null
    };
    $('#save').off("click");
    $.ajax({
        url: 'pres.do?method=insertPres',
        type: 'post',
        dataType: 'json',
        data: {
            pres: JSON.stringify(presBean)
        },
        success: function (resp) {
            $('#save').click(save);
            if (resp.state === 0) {
                alert("保存成功！");
                refreshTable();
                calTotal();
                readCard();
            } else {
                alert("保存失败！卡余额不足。");
            }
        }
    });
}

//计算当前处方总价
function calTotal() {
    let total = 0;
    $('#data').find('.pres-cal').each(function () {
        total += parseInt($(this).html());
    })
    $('#total').empty();
    $('#total').html(total);
}