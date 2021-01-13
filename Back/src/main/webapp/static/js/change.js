//绑定事件
$('#approve').click(change);
$('#approve').attr('disabled', true);
$('#approve').css('background-color', 'gray');
$('#readCard').click(readCard);
$('#cardCode').focus(function () {
    $(this).removeClass('null');
});
$('#cardCode').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($(this).val())) {
        $(this).addClass('null');
    } else {
        checkCardCode();
    }
});

/**
 * 读卡
 */
function readCard() {
    if ($('#keyword').val() === '') {
        alert("请先输入有效信息！");
        return;
    }
    if ($('#keyword').val().length > 18) {
        alert("请输入18位以内的字符！");
        return;
    }
    $("#readCard").off("click");
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'card.do?method=findSoldOut',
        type: 'post',
        dataType: 'json',
        data: {
            keyword: $('#keyword').val().trim().toUpperCase()
        },
        success: function (resp) {
            $('#readCard').click(readCard);
            $('#loading').hide();
            resetForm();
            if (resp.state === 0) {
                $('#approve').attr('disabled', false);
                $('#approve').css('background-color', '#1cade4');
                $('#approve').attr('oldCard', JSON.stringify(resp.data))
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

/**
 * 换卡
 */
function change() {
    if ($('#cardCode').val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($('#cardCode').val())) {
        $('#cardCode').addClass('null');
        alert("请输入待出售的一卡通卡号！");
        return;
    }
    let cardCode = $('#cardCode').val();
    $("#approve").off("click");
    $.ajax({
        url: 'card.do?method=changeCard',
        type: 'post',
        dataType: 'json',
        data: {
            oldCard: $('#approve').attr('oldCard'),
            cardCode: cardCode.trim().toUpperCase()
        },
        success: function (resp) {
            $('#approve').click(change);
            if (resp.state === 0) {
                resetForm();
                $('#approve').attr('disabled', true);
                $('#approve').css('background-color', 'gray');
                $('#keyword').val('');
                $('#cardCode').val('');
                alert("换卡成功！");
            }
        }
    });
}

/**
 * 检查一卡通卡号
 */
function checkCardCode() {
    $.ajax({
        url: 'card.do?method=findSaleableByCardCode',
        type: 'post',
        dataType: 'json',
        data: {
            cardCode: $('#cardCode').val().trim().toUpperCase()
        },
        success: function (resp) {
            if (resp.state !== 0) {
                $('#approve').click(change);
                $('#cardCode').val('');
                $('#cardCode').addClass('null');
                alert("该卡不是待出售状态或不存在该卡！");
            }
        }
    });
}

/**
 * 重置表单
 */
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
