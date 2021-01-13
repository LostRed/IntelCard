//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
//绑定事件
$('#selectAll').change(selectAll);
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);
$('#delete').click(delBatch);
$('#delete').attr('disabled', true);
$('#insert').click(showInsertDiv);
//新增div控件
$('#insert-commit').click(insertCommit);
$('#insert-back').click(function () {
    $('#insert-div').hide();
    $('#mask').hide();
});
$('#insert-name').focus(function () {
    $(this).removeClass('null');
    $('#insert-nameTip').html("");
    $('#insert-nameTip').css('color', 'black');
});
$('#insert-name').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#insert-nameTip').html("请输入人员姓名！");
        $('#insert-nameTip').css('color', 'red');
    }
});
$('#password').focus(function () {
    $(this).removeClass('null');
    $('#passwordTip').html("");
    $('#passwordTip').css('color', 'black');
});
$('#password').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z0-9]{6,8}$/.test($(this).val())) {
        $(this).addClass('null');
        $('#passwordTip').html("请输入正确的密码！");
        $('#passwordTip').css('color', 'red');
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
    } else if ($(this).val() !== $('#password').val()) {
        $(this).addClass('null');
        $('#confirmPwdTip').html("两次输入的密码不相同！");
        $('#confirmPwdTip').css('color', 'red');
    }
});

//更改div控件
$('#update-commit').click(updateCommit);
$('#update-back').click(function () {
    $('#update-div').hide();
    $('#mask').hide();
});

$('#update-name').focus(function () {
    $(this).removeClass('null');
    $('#update-nameTip').html("");
    $('#update-nameTip').css('color', 'black');
});
$('#update-name').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#update-nameTip').html("请输入人员姓名！");
        $('#update-nameTip').css('color', 'red');
    }
});

//刷新科室下拉框
refreshSectionList();
//刷新角色下拉框
refreshRoleList();
//刷新用户状态下拉框
refreshUserStateList(1);
//查询数据
search();

//以下是封装函数
//查找数据
function search() {
    let name = $('#name').val();
    let sectionString = $("#section").find("option:selected").attr("param");
    let roleString = $("#role").find("option:selected").attr("role");
    let stateString = $("#userState").find("option:selected").attr("param");
    let sectionId = "";
    let roleId = "";
    let stateId = "";
    if (sectionString !== undefined) {
        sectionId = JSON.parse(sectionString).paramId;
    }
    if (roleString !== undefined) {
        roleId = JSON.parse(roleString).roleId;
    }
    if (stateString !== undefined) {
        stateId = JSON.parse(stateString).paramId;
    }
    let queryBeans = [
        {field: 'PARAM_STATE_ID', operator: '!=', value: 3},
        {field: 'NAME', operator: 'LIKE', value: name},
        {field: 'PARAM_SECTION_ID', operator: '=', value: sectionId},
        {field: 'ROLE_ID', operator: '=', value: roleId},
        {field: 'PARAM_STATE_ID', operator: '=', value: stateId}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'user.do?method=findByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "NAME",
            desc: false
        },
        success: function (resp) {
            if (resp.state === 0) {
                refreshPage(resp.pageBean);
                refreshTable(resp.data);
            }
        }
    });
}

//刷新表格数据
function refreshTable(list) {
    $('#data').empty();
    for (let i = 0; i < list.length; i++) {
        let user = list[i];
        let $tr = $('<tr></tr>');
        //复选框
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('userId', user.userId);
        $td.append(checkbox);
        $tr.append($td);
        //序号
        $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //用户姓名
        $td = $('<td></td>');
        $td.append(user.name);
        $tr.append($td);
        //用户科室
        $td = $('<td></td>');
        $td.append(user.section.paramName);
        $tr.append($td);
        //角色
        $td = $('<td></td>');
        $td.append(user.roleBean.roleName);
        $tr.append($td);
        //用户状态
        $td = $('<td></td>');
        $td.append(user.state.paramName);
        $tr.append($td);
        //操作
        $td = $('<td></td>');
        let $update = $('<a>修改</a>');
        $update.attr('user', JSON.stringify(user));
        $update.click(showUpdateDiv);
        $td.append($update);
        let $change = $('<a></a>');
        if (user.state.paramName === "启用") {
            $change.append("禁用");
        } else {
            $change.append("启用");
        }
        $change.attr('userId', user.userId);
        $change.click(change);
        $td.append($change);
        let $delete = $('<a>删除</a>');
        $delete.attr('userId', user.userId);
        $delete.click(del);
        $td.append($delete);
        let $reset = $('<a>重置密码</a>');
        $reset.attr('userId', user.userId);
        $reset.click(reset);
        $td.append($reset);
        $tr.append($td);
        $('#data').append($tr);
    }
    //填充剩余行数
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

//显示新增div
function showInsertDiv() {
    $('#insert-div').show();
    $('#mask').show();
    $('#insert-name').val("");
    $('#insert-name').removeClass('null');
    $('#insert-nameTip').html("");
    $('#insert-nameTip').css('color', 'black');
    $('#password').val("");
    $('#password').removeClass('null');
    $('#passwordTip').html("");
    $('#passwordTip').css('color', 'black');
    $('#confirmPwd').val("");
    $('#confirmPwd').removeClass('null');
    $('#confirmPwdTip').html("");
    $('#confirmPwdTip').css('color', 'black');
    $("#insert-section").prop('selectedIndex', 0);
    $("#insert-role").prop('selectedIndex', 0);
}

//显示修改div
function showUpdateDiv() {
    $('#update-div').show();
    $('#mask').show();
    $('#update-commit').attr('user', $(this).attr('user'));
    let user = JSON.parse($(this).attr('user'));
    $('#update-name').val(user.name);
    $("#update-section option").each(function () {
        if (JSON.parse($(this).attr('param')).paramId === user.section.paramId) {
            $(this).prop('selected', 'true');
        }
    });
    $("#update-role option").each(function () {
        if (JSON.parse($(this).attr('role')).roleId === user.roleBean.roleId) {
            $(this).prop('selected', 'true');
        }
    });
}

//提交修改状态
function change() {
    if (confirm("确定修改吗？")) {
        let userIds = [];
        userIds.push($(this).attr("userId"));
        $.ajax({
            url: 'user.do?method=changeUser',
            type: 'post',
            dataType: 'json',
            data: {
                paramId: 1,
                userIds: JSON.stringify(userIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert("成功修改了" + resp.data + "位用户！");
                    search();
                } else {
                    alert("修改失败！");
                }
            }
        });
    }
}

//提交删除
function del() {
    if (confirm("确定删除吗？")) {
        let userIds = [];
        userIds.push($(this).attr("userId"));
        $.ajax({
            url: 'user.do?method=changeUser',
            type: 'post',
            dataType: 'json',
            data: {
                paramId: 3,
                userIds: JSON.stringify(userIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert("成功删除了" + resp.data + "位用户！");
                    search();
                } else {
                    alert("删除失败！");
                }
            }
        });
    }
}

//提交批量删除
function delBatch() {
    if (confirm("确定删除吗？")) {
        let userIds = [];
        $(':checkbox[name=select]').each(function () {
            if ($(this).is(':checked')) {
                userIds.push($(this).attr("userId"));
            }
        });
        $.ajax({
            url: 'user.do?method=changeUser',
            type: 'post',
            dataType: 'json',
            data: {
                paramId: 0,
                userIds: JSON.stringify(userIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    $('#selectAll').prop('checked', false);
                    alert("成功删除了" + resp.data + "位用户！");
                    search();
                } else {
                    alert("删除失败！");
                }
            }
        });
    }
}

//提交重置密码
function reset() {
    if (confirm("确定重置密码吗？")) {
        $.ajax({
            url: 'user.do?method=resetUser',
            type: 'post',
            dataType: 'json',
            data: {
                userId: $(this).attr("userId")
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert("重置成功！重置后的密码为：" + resp.data);
                    search();
                }
            }
        });
    }
}

//提交新增
function insertCommit() {
    if ($('#insert-name').val() === "") {
        return;
    }
    if ($('#password').val() === "" || !/^[a-zA-Z0-9]{6,8}$/.test($('#password').val())) {
        return;
    }
    if ($('#confirmPwd').val() === "" || $('#confirmPwd').val() !== $('#password').val()) {
        return;
    }
    if (confirm("确定提交吗？")) {
        $("#insert-commit").off("click");
        $.ajax({
            url: 'user.do?method=insertUser',
            type: 'post',
            dataType: 'json',
            data: {
                name: $('#insert-name').val(),
                password: $('#password').val(),
                section: $("#insert-section").find("option:selected").attr("param"),
                roleBean: $("#insert-role").find("option:selected").attr("role")
            },
            success: function (resp) {
                $('#insert-commit').click(insertCommit);
                if (resp.state === 0) {
                    $('#insert-div').hide();
                    $('#mask').hide();
                    alert("新增成功！该人员的用户名为：" + resp.data);
                    search();
                }
            }
        });
    }
}

//提交修改
function updateCommit() {
    if ($('#update-name').val() === "") {
        return;
    }
    let user = JSON.parse($(this).attr('user'));
    user.name = $('#update-name').val();
    user.section = JSON.parse($("#update-section").find("option:selected").attr("param"));
    user.roleBean = JSON.parse($("#update-role").find("option:selected").attr("role"));
    if (confirm("确定提交吗？")) {
        $("#update-commit").off("click");
        $.ajax({
            url: 'user.do?method=updateUser',
            type: 'post',
            dataType: 'json',
            data: {
                userBean: JSON.stringify(user)
            },
            success: function (resp) {
                $('#update-commit').click(updateCommit);
                if (resp.state === 0) {
                    $('#update-div').hide();
                    $('#mask').hide();
                    alert("修改成功！");
                    search();
                }
            }
        });
    }
}