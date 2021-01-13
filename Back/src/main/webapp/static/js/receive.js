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
$('#insert-applyName').focus(function () {
    $(this).removeClass('null');
    $('#insert-applyNameTip').html("");
    $('#insert-applyNameTip').css('color', 'black');
});
$('#insert-applyName').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#insert-applyNameTip').html("请输入人员姓名！");
        $('#insert-applyNameTip').css('color', 'red');
    }
});
$('#insert-applyNum').focus(function () {
    $(this).removeClass('null');
    $('#insert-applyNumTip').html("");
    $('#insert-applyNumTip').css('color', 'black');
});
$('#insert-applyNum').blur(function () {
    if ($(this).val() === '' || !/^[1-9]\d*$/.test($(this).val())) {
        $(this).addClass('null');
        $('#insert-applyNumTip').html("请输入正确的数量！");
        $('#insert-applyNumTip').css('color', 'red');
    }
});

//更改div控件
$('#update-commit').click(updateCommit);
$('#update-back').click(function () {
    $('#update-div').hide();
    $('#mask').hide();
});

$('#update-applyName').focus(function () {
    $(this).removeClass('null');
    $('#update-applyNameTip').html("");
    $('#update-applyNameTip').css('color', 'black');
});
$('#update-applyName').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#update-applyNameTip').html("请输入人员姓名！");
        $('#update-applyNameTip').css('color', 'red');
    }
});
$('#update-applyNum').focus(function () {
    $(this).removeClass('null');
    $('#update-applyNumTip').html("");
    $('#update-applyNumTip').css('color', 'black');
});
$('#update-applyNum').blur(function () {
    if ($(this).val() === '' || !/^[1-9]\d*$/.test($(this).val())) {
        $(this).addClass('null');
        $('#update-applyNumTip').html("请输入正确的数量！");
        $('#update-applyNumTip').css('color', 'red');
    }
});

//刷新申请下拉框
refreshApplyStateList();
//查询数据
search();




//以下是封装函数
//查找数据
function search() {
    let date1 = $('#date1').val();
    let date2 = $('#date2').val();
    if (date1 > date2 && date1 !== "" && date2 !== "") {
        $('#date2').addClass('null');
    } else {
        $('#date2').removeClass('null');
    }
    let stateString = $("#applyState").find("option:selected").attr("param");
    let stateId = "";
    if (stateString !== undefined) {
        stateId = JSON.parse(stateString).paramId;
    }
    let queryBeans = [
        {field: "TO_CHAR(APPLY_TIME,'YYYY-MM-DD')", operator: '>=', value: date1},
        {field: "TO_CHAR(APPLY_TIME,'YYYY-MM-DD')", operator: '<=', value: date2},
        {field: 'PARAM_STATE_ID', operator: '=', value: stateId},
        {field: 'APPLY_USER_ID', operator: '=', value: 'loginUser'}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'apply.do?method=findByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "APPLY_TIME",
            desc: true
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
        let apply = list[i];
        let $tr = $('<tr></tr>');
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('applyId', apply.applyId);
        if (apply.state.paramName !== "待审核") {
            checkbox.attr('disabled', 'disabled');
        }
        $td.append(checkbox);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //申请卡日期
        $td = $('<td></td>');
        $td.append(formatDate(apply.applyTime));
        $tr.append($td);
        //申请卡日期
        $td = $('<td></td>');
        $td.append(apply.applyNum);
        $tr.append($td);
        //申请人
        $td = $('<td></td>');
        $td.append(apply.applyUser.name);
        $tr.append($td);
        //审核状态
        $td = $('<td></td>');
        $td.append(apply.state.paramName);
        $tr.append($td);
        //审核人
        $td = $('<td></td>');
        if (apply.auditUser == null) {
            $td.append('无');
        } else {
            $td.append(apply.auditUser.name);
        }
        $tr.append($td);
        //审核时间
        $td = $('<td></td>');
        if (apply.auditTime == null) {
            $td.append('无');
        } else {
            $td.append(formatDate(apply.auditTime));
        }
        $tr.append($td);
        //操作
        $td = $('<td></td>');
        if (apply.state.paramName === "待审核") {
            let $update = $('<a>修改</a>');
            $update.attr('apply', JSON.stringify(apply));
            $update.click(showUpdateDiv);
            $td.append($update);
            let $delete = $('<a>撤销</a>');
            $delete.attr('applyId', apply.applyId);
            $delete.click(del);
            $td.append($delete);
        }
        $tr.append($td);
        $('#data').append($tr);
    }
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 9; j++) {
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
    $('#insert-applyName').removeClass('null');
    $('#insert-applyNameTip').html("");
    $('#insert-applyNameTip').css('color', 'black');
    $('#insert-date').attr('value', now());
    $('#insert-applyNum').val("");
    $('#insert-applyNum').removeClass('null');
    $('#insert-applyNumTip').html("");
    $('#insert-applyNumTip').css('color', 'black');
    $.ajax({
        url: 'apply.do?method=loginUser',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            if (resp.state === 0) {
                $('#insert-applyName').val(resp.data.name);
            }
        }
    });
}

//显示修改div
function showUpdateDiv() {
    $('#update-div').show();
    $('#mask').show();
    let apply = JSON.parse($(this).attr('apply'));
    $('#update-commit').attr('apply', $(this).attr('apply'));
    $('#update-applyName').val(apply.applyUser.name);
    $('#update-date').val(formatDate(apply.applyTime));
    $('#update-applyNum').val(apply.applyNum);
}

//提交删除
function del() {
    if (confirm("确定撤销吗？")) {
        let applyIds = [];
        applyIds.push($(this).attr("applyId"));
        $.ajax({
            url: 'apply.do?method=deleteApply',
            type: 'post',
            dataType: 'json',
            data: {
                applyIds: JSON.stringify(applyIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert("成功撤销了" + resp.data + "条申请！");
                    search();
                } else {
                    alert("撤销失败！");
                }
            }
        });
    }
}

//提交批量删除
function delBatch() {
    if (confirm("确定撤销吗？")) {
        let applyIds = [];
        $(':checkbox[name=select]').each(function () {
            if ($(this).is(':checked')) {
                applyIds.push($(this).attr("applyId"));
            }
        });
        $.ajax({
            url: 'apply.do?method=deleteApply',
            type: 'post',
            dataType: 'json',
            data: {
                applyIds: JSON.stringify(applyIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    $('#selectAll').prop('checked', false);
                    alert("成功撤销了" + resp.data + "条申请！");
                    search();
                } else {
                    alert("撤销失败！");
                }
            }
        });
    }
}

//提交新增
function insertCommit() {
    if ($('#insert-applyNum').val() === "" || !/^[1-9]\d*$/.test($('#insert-applyNum').val())) {
        return;
    }
    if (confirm("确定提交吗？")) {
        $("#insert-commit").off("click");
        $.ajax({
            url: 'apply.do?method=insertApply',
            type: 'post',
            dataType: 'json',
            data: {
                applyNum: $('#insert-applyNum').val()
            },
            success: function (resp) {
                $('#insert-commit').click(insertCommit);
                if (resp.state === 0) {
                    $('#insert-div').hide();
                    $('#mask').hide();
                    alert("申请成功！");
                    search();
                }
            }
        });
    }
}

//提交修改
function updateCommit() {
    if ($('#update-applyNum').val() === "" || !/^[1-9]\d*$/.test($('#update-applyNum').val())) {
        return;
    }
    let apply = JSON.parse($(this).attr('apply'));
    apply.applyNum = $('#update-applyNum').val();
    if (confirm("确定提交吗？")) {
        $("#update-commit").off("click");
        $.ajax({
            url: 'apply.do?method=updateApply',
            type: 'post',
            dataType: 'json',
            data: {
                apply: JSON.stringify(apply)
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
