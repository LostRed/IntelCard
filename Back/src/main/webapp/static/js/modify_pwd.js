//绑定事件
$('#approve').click(commit);
$('#oldPwd').focus(function () {
    $(this).removeClass('null');
    $('#oldPwdTip').html("");
    $('#oldPwdTip').css('color', 'black');
});
$('#oldPwd').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z0-9]{6,8}$/.test($(this).val())) {
        $(this).addClass('null');
        $('#oldPwdTip').html("请输入正确的密码！");
        $('#oldPwdTip').css('color', 'red');
    }
});
$('#newPwd').focus(function () {
    $(this).removeClass('null');
    $('#newPwdTip').html("");
    $('#newPwdTip').css('color', 'black');
});
$('#newPwd').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z0-9]{6,8}$/.test($(this).val())) {
        $(this).addClass('null');
        $('#newPwdTip').html("请输入正确的密码！");
        $('#newPwdTip').css('color', 'red');
    }
});
$('#confirmPwd').focus(function () {
    $(this).removeClass('null');
    $('#confirmPwdTip').html("");
    $('#confirmPwdTip').css('color', 'black');
});
$('#confirmPwd').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#confirmPwdTip').html("请输入重复密码！");
        $('#confirmPwdTip').css('color', 'red');
    } else if ($(this).val() !== $('#newPwd').val()) {
        $(this).addClass('null');
        $('#confirmPwdTip').html("两次输入的密码不相同！");
        $('#confirmPwdTip').css('color', 'red');
    }
});

//以下是封装函数
//提交
function commit() {
    if ($('#oldPwd').val() === "" || !/^[a-zA-Z0-9]{6,8}$/.test($('#oldPwd').val())) {
        return;
    }
    if ($('#newPwd').val() === "" || !/^[a-zA-Z0-9]{6,8}$/.test($('#newPwd').val())) {
        return;
    }
    if ($('#confirmPwd').val() === "" || $('#confirmPwd').val() !== $('#newPwd').val()) {
        return;
    }
    if (confirm("确定提交吗？")) {
        $("#approve").off("click");
        $.ajax({
            url: 'user.do?method=modifyPwd',
            type: 'post',
            dataType: 'json',
            data: {
                oldPwd: $('#oldPwd').val(),
                newPwd: $('#newPwd').val(),
            },
            success: function (resp) {
                $('#approve').click(commit);
                if (resp.state === 0) {
                    alert("密码修改成功！请重新登录。");
                    top.location.reload();
                } else {
                    alert("旧密码输入错误！修改失败。");
                }
            }
        });
    }
}