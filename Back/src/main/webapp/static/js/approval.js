let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
//绑定事件
$('#selectAll').change(selectAll);
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);

//更改div控件
$('#update-commit').click(updateCommit);
$('#update-back').click(function () {
    $('#update-div').hide();
    $('#mask').hide();
});

$('#cardCodeStart').focus(function () {
    $(this).removeClass('null');
    $('#cardCodeStartTip').html("");
    $('#cardCodeStartTip').css('color', 'black');
});
$('#cardCodeStart').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($(this).val())) {
        $(this).addClass('null');
        $('#cardCodeStartTip').html("请输入正确的卡号！");
        $('#cardCodeStartTip').css('color', 'red');
    }
});
//查询div控件
$('#query-back').click(function () {
    $('#query-div').hide();
    $('#mask').hide();
});

//更新申请人下拉框
refreshApplyUserList();
//刷新申请下拉框
refreshApplyStateList();
search();

/**
 * 查找数据
 */
function search() {
    let userString = $("#applyUser").find("option:selected").attr("user");
    let userId = "";
    if (userString !== undefined) {
        userId = JSON.parse(userString).userId;
    }
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
        {field: "APPLY_USER_ID", operator: '=', value: userId},
        {field: "TO_CHAR(APPLY_TIME,'YYYY-MM-DD')", operator: '>=', value: date1},
        {field: "TO_CHAR(APPLY_TIME,'YYYY-MM-DD')", operator: '<=', value: date2},
        {field: 'PARAM_STATE_ID', operator: '=', value: stateId}
    ];
    find(queryBeans);
}

/**
 * 提交多条件
 * @param queryBeans 查找数据
 */
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

/**
 * 刷新表格数据
 * @param list 数据集合
 */
function refreshTable(list) {
    $('#data').empty();
    for (let i = 0; i < list.length; i++) {
        let apply = list[i];
        let $tr = $('<tr></tr>');
        //序号
        let $td = $('<td></td>');
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
        if (apply.auditUser.name == null) {
            $td.append('无');
        } else {
            $td.append(apply.auditUser.name);
        }
        $tr.append($td);
        //审核时间
        $td = $('<td></td>');
        if (apply.auditTime ==null) {
            $td.append('无');
        } else {
            $td.append(formatDate(apply.auditTime));
        }
        $tr.append($td);
        //操作
        $td = $('<td></td>');
        if (apply.state.paramName === "待审核") {
            let $update = $('<a>审核</a>');
            $update.attr('apply', JSON.stringify(apply));
            $update.click(showUpdateDiv);
            $td.append($update);
        }
        let $query = $('<a>查看</a>');
        $query.attr('apply', JSON.stringify(apply));
        $query.click(showQueryDiv);
        $td.append($query);
        $tr.append($td);
        $('#data').append($tr);
    }
    //填补剩余行数
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 8; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
}

/**
 * 显示修改div
 */
function showUpdateDiv() {
    $('#update-div').show();
    $('#mask').show();
    let apply = JSON.parse($(this).attr('apply'));
    $('#update-commit').attr('apply', $(this).attr('apply'));
    $('#update-applyUser').val(apply.applyUser.name);
    $('#update-date').val(formatDate(apply.applyTime));
    $('#update-applyNum').val(apply.applyNum);
}

/**
 * 显示查看div
 */
function showQueryDiv() {
    $('#query-div').show();
    $('#mask').show();
    let apply = JSON.parse($(this).attr('apply'));
    $('#query-applyUser').val(apply.applyUser.name);
    $('#query-applyDate').val(formatDate(apply.applyTime));
    $('#query-applyNum').val(apply.applyNum);
    if (apply.auditUser != null) {
        $('#query-auditUser').val(apply.auditUser.name);
    } else {
        $('#query-auditUser').val('无');
    }
    if (apply.auditTime != null) {
        $('#query-auditDate').val(formatDate(apply.auditTime));
    } else {
        $('#query-auditDate').val(undefined);
    }
    $.ajax({
        url: 'card.do?method=findByApplyId',
        type: 'post',
        dataType: 'json',
        data: {
            applyId: apply.applyId,
        },
        success: function (resp) {
            if (resp.state === 0) {
                refreshSelect(resp.data);
            }
        }
    });
}

/**
 * 更新查看领用卡号下拉框
 * @param list 数据集合
 */
function refreshSelect(list) {
    $('#query-cardCode').empty();
    for (let cardCode of list) {
        let $option = $('<option></option>');
        $option.append(cardCode);
        $('#query-cardCode').append($option);
    }
}


/**
 * 提交修改
 */
function updateCommit() {
    if ($('#cardCodeStart').val() === "" || !/^[a-zA-Z]{1,5}[0-9]{8}$/.test($('#cardCodeStart').val())) {
        return;
    }
    let apply = JSON.parse($(this).attr('apply'));
    apply.applyNum = $('#update-applyNum').val();
    if (confirm("确定提交吗？")) {
        $("#update-commit").off("click");
        $.ajax({
            url: 'apply.do?method=auditApply',
            type: 'post',
            dataType: 'json',
            data: {
                apply: JSON.stringify(apply),
                cardCodeStart: $('#cardCodeStart').val().trim().toUpperCase()
            },
            success: function (resp) {
                $('#update-commit').click(updateCommit);
                if (resp.state === 0) {
                    $('#update-div').hide();
                    $('#mask').hide();
                    alert("审核成功！");
                    search();
                } else {
                    alert("库存一卡通不足！请更改开始卡号或使用卡入库功能新增一卡通。");
                }
            }
        });
    }
}
