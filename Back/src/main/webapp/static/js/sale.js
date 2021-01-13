//绑定事件
$('#approve').click(sale);
$('#name').focus(function () {
    $(this).removeClass('null');
});
$('#name').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
    }
});
$('#age').focus(function () {
    $(this).removeClass('null');
});
$('#age').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
    }
});
$('#week').focus(function () {
    $(this).removeClass('null');
});
$('#week').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
    }
});
$('#idCard').focus(function () {
    $(this).removeClass('null');
});
$('#idCard').blur(function () {
    if ($(this).val() === '' ||
        !/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test($(this).val())) {
        $(this).addClass('null');
    } else {
        checkIdCard();
    }
});
$('#phone').focus(function () {
    $(this).removeClass('null');
});
$('#phone').blur(function () {
    if ($(this).val() === '' || !/^1[3456789]\d{9}$/.test($(this).val())) {
        $(this).addClass('null');
    } else {
        checkPhone();
    }
});
$('#amount').focus(function () {
    $(this).removeClass('null');
});
$('#amount').blur(function () {
    if ($(this).val() === '' || !/^[1-9][0-9]*/.test($(this).val())) {
        $(this).addClass('null');
    }
});
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

//出售一卡通
function sale() {
    if ($('#name').val() === '') {
        alert("请输入就诊人姓名！");
        $('#name').addClass('null');
        return;
    }
    if ($('#age').val() === '') {
        $('#age').addClass('null');
        alert("请输入就诊人年龄！");
        return;
    }
    if ($('#week').val() === '') {
        $('#week').addClass('null');
        alert("请输入就诊人周！");
        return;
    }
    if ($('#idCard').val() === '' ||
        !/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test($('#idCard').val())) {
        $('#idCard').addClass('null');
        alert("请输入就诊人身份证号码！");
        return;
    }
    if ($('#phone').val() === '' || !/^1[3456789]\d{9}$/.test($('#phone').val())) {
        $('#phone').addClass('null');
        alert("请输入就诊人手机号码！");
        return;
    }
    if ($('#amount').val() === '' || !/^[1-9][0-9]*/.test($('#amount').val())) {
        $('#amount').addClass('null');
        alert("请输入预存金额！");
        return;
    }
    if ($('#cardCode').val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($('#cardCode').val())) {
        $('#cardCode').addClass('null');
        alert("请输入出售的一卡通卡号！");
        return;
    }
    let name = $('#name').val();
    let age = $('#age').val();
    let week = $('#age').val();
    let sex = $(":radio[name=sex]:checked").val();
    let nativePlace = $("#native-place").find("option:selected").text();
    let idCard = $('#idCard').val().trim().toUpperCase();
    let phone = $('#phone').val();
    let addr = $('#addr').val();
    let amount = $('#amount').val();
    let cardCode = $('#cardCode').val();
    let patient = {
        patientId: -1,
        name: name,
        age: age,
        week: week,
        sex: sex,
        nativePlace: nativePlace,
        idCard: idCard,
        phone: phone,
        addr: addr,
        descInfo: ''
    };
    $("#approve").off("click");
    $.ajax({
        url: 'card.do?method=saleCard',
        type: 'post',
        dataType: 'json',
        data: {
            patient: JSON.stringify(patient),
            amount: amount,
            cardCode: cardCode.trim().toUpperCase()
        },
        success: function (resp) {
            $('#approve').click(sale);
            if (resp.state === 0) {
                resetForm();
                alert("出售成功！");
            }
        }
    });
}

//检查身份证是否已经登记
function checkIdCard() {
    $.ajax({
        url: 'patient.do?method=findIdCard',
        type: 'post',
        dataType: 'json',
        data: {
            idCard: $('#idCard').val().trim().toUpperCase()
        },
        success: function (resp) {
            if (resp.state !== 0) {
                $('#approve').click(sale);
                $('#idCard').val('');
                $('#idCard').addClass('null');
                alert("该身份证号已存在!");
            }
        }
    });
}

//检查手机号是否已经登记
function checkPhone() {
    $.ajax({
        url: 'patient.do?method=findPhone',
        type: 'post',
        dataType: 'json',
        data: {
            phone: $('#phone').val()
        },
        success: function (resp) {
            if (resp.state !== 0) {
                $('#approve').click(sale);
                $('#phone').val('');
                $('#phone').addClass('null');
                alert("该手机号已存在!");
            }
        }
    });
}

//检查一卡通卡号是否为待出售
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
                $('#approve').click(sale);
                $('#cardCode').val('');
                $('#cardCode').addClass('null');
                alert("该卡不是待出售状态或不存在该卡！");
            }
        }
    });
}

//重置表单
function resetForm() {
    $('#name').val('');
    $('#age').val('');
    $('#week').val('');
    $(":radio[value='男']").prop('checked', 'checked');
    $("#native-place").prop('selectedIndex', 0);
    $('#idCard').val('');
    $('#phone').val('');
    $('#addr').val('');
    $('#amount').val('');
    $('#cardCode').val('');
}