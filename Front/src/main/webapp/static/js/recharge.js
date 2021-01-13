//绑定事件
$('#approve').click(recharge);
$('#approve').attr('disabled', true);
$('#approve').css('background-color', 'gray');
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
$('#recharge').focus(function () {
    $(this).removeClass('null');
});
$('#recharge').blur(function () {
    if ($(this).val() === '' || !/^[1-9][0-9]*/.test($(this).val())) {
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

//充值
function recharge() {
    $("#approve").off("click");
    if ($('#recharge').val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($('#cardCode').val())) {
        $('#approve').click(recharge);
        $('#recharge').addClass('null');
        alert("请输入充值金额！");
        return;
    }
    $.ajax({
        url: 'card.do?method=rechargeCard',
        type: 'post',
        dataType: 'json',
        data: {
            oldCard: $('#approve').attr('oldCard'),
            recharge: $('#recharge').val()
        },
        success: function (resp) {
            $('#approve').click(recharge);
            if (resp.state === 0) {
                resetForm();
                $('#approve').attr('disabled', true);
                $('#approve').css('background-color', 'gray');
                $('#cardCode').val('');
                $('#recharge').val('');
                alert("充值成功！");
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
