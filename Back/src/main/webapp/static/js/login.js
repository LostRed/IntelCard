//绑定事件
$('#approve').click(login);
$('#imageCode').click(changeCode);

//登录校验
function login() {
    $("#approve").off("click");
    if ($('#username').val() === '') {
        alert('请输入用户名！');
        $('#approve').click(login);
        return;
    }
    if ($('#password').val() === '') {
        alert('请输入密码！');
        $('#approve').click(login);
        return;
    }
    if ($('#verifyCode').val() === '') {
        alert('请输入验证码！');
        $('#approve').click(login);
        return;
    }
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'user.do?method=login',
        type: 'post',
        dataType: 'json',
        data: {
            username: $('#username').val(),
            password: $('#password').val(),
            verifyCode: $('#verifyCode').val()
        },
        success: function (resp) {
            $('#loading').hide();
            $('#approve').click(login);
            if (resp.state === 0) {
                location.href = 'user/main.jsp';
            } else {
                alert(resp.msgInfo);
            }
        },
        error: function () {
            $('#loading').hide();
        }
    });
}

//更换验证码图片
function changeCode() {
    $('#imageCode').attr('src', 'imageCode?=' + new Date());
}