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
$('#insert-paramName').focus(function () {
    $(this).removeClass('null');
    $('#insert-paramNameTip').html("");
    $('#insert-paramNameTip').css('color', 'black');
});
$('#insert-paramName').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#insert-paramNameTip').html("请输入参数名称！");
        $('#insert-paramNameTip').css('color', 'red');
    }
});
$('#insert-paramValue').focus(function () {
    $(this).removeClass('null');
    $('#insert-paramValueTip').html("");
    $('#insert-paramValueTip').css('color', 'black');
});
$('#insert-paramValue').blur(function () {
    if ($(this).val() === '' || !/^[0-9]*$/.test($(this).val())) {
        $(this).addClass('null');
        $('#insert-paramValueTip').html("请输入参数值！");
        $('#insert-paramValueTip').css('color', 'red');
    }
});


//更改div控件
$('#update-commit').click(updateCommit);
$('#update-back').click(function () {
    $('#update-div').hide();
    $('#mask').hide();
});

$('#update-paramName').focus(function () {
    $(this).removeClass('null');
    $('#update-paramNameTip').html("");
    $('#update-paramNameTip').css('color', 'black');
});
$('#update-paramName').blur(function () {
    if ($(this).val() === '') {
        $(this).addClass('null');
        $('#update-paramNameTip').html("请输入参数名称！");
        $('#update-paramNameTip').css('color', 'red');
    }
});
$('#update-paramValue').focus(function () {
    $(this).removeClass('null');
    $('#update-paramValueTip').html("");
    $('#update-paramValueTip').css('color', 'black');
});
$('#update-paramValue').blur(function () {
    if ($(this).val() === '' || !/^[0-9]*$/.test($(this).val())) {
        $(this).addClass('null');
        $('#update-paramValueTip').html("请输入参数值！");
        $('#update-paramValueTip').css('color', 'red');
    }
});

//刷新参数类型下拉框
refreshParamTypeList();
//查找表格数据
search();



//以下是封装函数
//查找数据
function search() {
    let paramName = $('#paramName').val();
    let paramTypeString = $("#paramType").find("option:selected").text();
    if (paramTypeString === "全部") {
        paramTypeString = "";
    }
    let queryBeans = [
        {field: 'PARAM_STATE', operator: '!=', value: '不可用'},
        {field: 'PARAM_NAME', operator: 'LIKE', value: paramName},
        {field: 'PARAM_TYPE', operator: '=', value: paramTypeString},
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'param.do?method=findByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "PARAM_TYPE",
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
        let param = list[i];
        let $tr = $('<tr></tr>');
        //复选框
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('paramId', param.paramId);
        $td.append(checkbox);
        $tr.append($td);
        //序号
        $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //参数名
        $td = $('<td></td>');
        $td.append(param.paramName);
        $tr.append($td);
        //参数类型
        $td = $('<td></td>');
        $td.append(param.paramType);
        $tr.append($td);
        //参数值
        $td = $('<td></td>');
        $td.append(param.paramValue);
        $tr.append($td);
        //操作
        $td = $('<td></td>');
        let $update = $('<a>修改</a>');
        $update.attr('param', JSON.stringify(param));
        $update.click(showUpdateDiv);
        $td.append($update);
        let $delete = $('<a>删除</a>');
        $delete.attr('paramId', param.paramId);
        $delete.click(del);
        $td.append($delete);
        $tr.append($td);
        $('#data').append($tr);
    }
    //填充剩余行数
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 6; j++) {
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
    $('#insert-paramName').val("");
    $('#insert-paramName').removeClass('null');
    $('#insert-paramNameTip').html("");
    $('#insert-paramNameTip').css('color', 'black');
    $("#insert-paramType").prop('selectedIndex', 0);
    $('#insert-paramValue').val("");
    $('#insert-paramValue').removeClass('null');
    $('#insert-paramValueTip').html("");
    $('#insert-paramValueTip').css('color', 'black');
}

//显示修改div
function showUpdateDiv() {
    $('#update-div').show();
    $('#mask').show();
    $('#update-commit').attr('param', $(this).attr('param'));
    let param = JSON.parse($(this).attr('param'));
    $('#update-paramName').val(param.paramName);
    $("#update-paramType option").each(function () {
        if ($(this).text() === param.paramType) {
            $(this).prop('selected', 'true');
        }
    });
    $('#update-paramValue').val(param.paramValue);
}

//提交删除
function del() {
    if (confirm("确定删除吗？")) {
        let paramIds = [];
        paramIds.push($(this).attr("paramId"));
        $.ajax({
            url: 'param.do?method=changeParam',
            type: 'post',
            dataType: 'json',
            data: {
                paramIds: JSON.stringify(paramIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert("成功删除了" + resp.data + "个参数！");
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
        let paramIds = [];
        $(':checkbox[name=select]').each(function () {
            if ($(this).is(':checked')) {
                paramIds.push($(this).attr("paramId"));
            }
        });
        $.ajax({
            url: 'param.do?method=changeParam',
            type: 'post',
            dataType: 'json',
            data: {
                paramIds: JSON.stringify(paramIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    $('#selectAll').prop('checked', false);
                    alert("成功删除了" + resp.data + "个参数！");
                    search();
                } else {
                    alert("删除失败！");
                }
            }
        });
    }
}

//提交新增
function insertCommit() {
    if ($('#insert-paramName').val() === "") {
        return;
    }
    if ($('#insert-paramValue').val() === "" || !/^[0-9]*$/.test($('#insert-paramValue').val())) {
        return;
    }
    if (confirm("确定提交吗？")) {
        $("#insert-commit").off("click");
        $.ajax({
            url: 'param.do?method=insertParam',
            type: 'post',
            dataType: 'json',
            data: {
                paramName: $('#insert-paramName').val(),
                paramType: $("#insert-paramType").find("option:selected").text(),
                paramValue: $('#insert-paramValue').val()
            },
            success: function (resp) {
                $('#insert-commit').click(insertCommit);
                if (resp.state === 0) {
                    $('#insert-div').hide();
                    $('#mask').hide();
                    alert("新增成功！");
                    search();
                }
            }
        });
    }
}

//提交修改
function updateCommit() {
    if ($('#update-paramName').val() === "") {
        return;
    }
    if ($('#update-paramValue').val() === "" || !/^[0-9]*$/.test($('#update-paramValue').val())) {
        return;
    }
    let param = JSON.parse($(this).attr('param'));
    param.paramName = $('#update-paramName').val();
    param.paramType = $("#update-paramType").find("option:selected").text();
    param.paramValue = $('#update-paramValue').val();
    if (confirm("确定提交吗？")) {
        $("#update-commit").off("click");
        $.ajax({
            url: 'param.do?method=updateParam',
            type: 'post',
            dataType: 'json',
            data: {
                param: JSON.stringify(param)
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