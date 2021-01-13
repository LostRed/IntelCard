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
$('#cardCodeStart').focus(function () {
    $(this).removeClass('null');
    $('#cardCodeStartTip').html("");
    $('#cardCodeStartTip').css('color', 'black');
});
$('#cardCodeStart').blur(function () {
    if ($(this).val() === '' || !/^[0-9]{1,8}$/.test($(this).val())) {
        $(this).addClass('null');
        $('#cardCodeStartTip').html("请输入正确的卡号！");
        $('#cardCodeStartTip').css('color', 'red');
    }
});
$('#cardCodeEnd').focus(function () {
    $(this).removeClass('null');
    $('#cardCodeEndTip').html("");
    $('#cardCodeEndTip').css('color', 'black');
});
$('#cardCodeEnd').blur(function () {
    if ($(this).val() === '' || !/^[0-9]{1,8}$/.test($(this).val()) ||
        parseInt($(this).val()) < parseInt($('#cardCodeStart').val())) {
        $(this).addClass('null');
        $('#cardCodeEndTip').html("请输入正确的卡号！");
        $('#cardCodeEndTip').css('color', 'red');
    }
});
$('#prefix').focus(function () {
    $(this).removeClass('null');
    $('#prefixTip').html("");
    $('#prefixTip').css('color', 'black');
});
$('#prefix').blur(function () {
    if ($(this).val() === '' || !/^[a-zA-Z]{1,5}$/.test($(this).val())) {
        $(this).addClass('null');
        $('#prefixTip').html("请输入正确的前缀！");
        $('#prefixTip').css('color', 'red');
    }
});

//刷新一卡通状态下拉框
refreshCardStateList(0);
//查询数据
search();



//以下是封装函数
//查找数据
function search() {
    let cardCode1 = $('#cardCode1').val();
    let cardCode2 = $('#cardCode2').val();
    if (!/^[a-zA-Z]{1,5}[0-9]{8}$/.test(cardCode1) && cardCode1 !== "") {
        cardCode1 = "*";
        $('#cardCode1').addClass('null');
    } else {
        $('#cardCode1').removeClass('null');
    }
    if (!/^[a-zA-Z]{1,5}[0-9]{8}$/.test(cardCode2) && cardCode2 !== "") {
        cardCode2 = "*";
        $('#cardCode2').addClass('null');
    } else {
        $('#cardCode2').removeClass('null');
    }
    let date1 = $('#date1').val();
    let date2 = $('#date2').val();
    if (date1 > date2 && date1 !== "" && date2 !== "") {
        $('#date2').addClass('null');
    } else {
        $('#date2').removeClass('null');
    }
    let stateString = $("#cardState").find("option:selected").attr("param");
    let stateId = "";
    if (stateString !== undefined) {
        stateId = JSON.parse(stateString).paramId;
    }
    let queryBeans = [
        {
            field: 'REGEXP_SUBSTR(CARD_CODE,\'^[a-zA-Z]+\')',
            operator: '=',
            value: cardCode1.trim().toUpperCase().match(/^\D*/)[0]
        },
        {
            field: 'REGEXP_SUBSTR(CARD_CODE,\'^[a-zA-Z]+\')',
            operator: '=',
            value: cardCode2.trim().toUpperCase().match(/^\D*/)[0]
        },
        {
            field: 'REGEXP_SUBSTR(CARD_CODE,\'[0-9]+$\')',
            operator: '>=',
            value: cardCode1.trim().toUpperCase().match(/\d*$/)[0]
        },
        {
            field: 'REGEXP_SUBSTR(CARD_CODE,\'[0-9]+$\')',
            operator: '<=',
            value: cardCode2.trim().toUpperCase().match(/\d*$/)[0]
        },
        {field: "TO_CHAR(CREATE_TIME,'YYYY-MM-DD')", operator: '>=', value: date1},
        {field: "TO_CHAR(CREATE_TIME,'YYYY-MM-DD')", operator: '<=', value: date2},
        {field: 'PARAM_STATE_ID', operator: '=', value: stateId}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'card.do?method=findByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "CREATE_TIME",
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
        let card = list[i];
        let $tr = $('<tr></tr>');
        //复选框
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('cardId', card.cardId);
        if (card.state.paramName !== "待领用") {
            checkbox.attr('disabled', 'disabled');
        }
        $td.append(checkbox);
        $tr.append($td);
        //序号
        $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //一卡通卡号
        $td = $('<td></td>');
        $td.append(card.cardCode);
        $tr.append($td);
        //入库时间
        $td = $('<td></td>');
        $td.append(formatDate(card.createTime));
        $tr.append($td);
        //一卡通状态
        $td = $('<td></td>');
        $td.append(card.state.paramName);
        $tr.append($td);
        //操作
        $td = $('<td></td>');
        if (card.state.paramName === "待领用") {
            let $delete = $('<a>删除</a>');
            $delete.attr('cardId', card.cardId);
            $delete.click(del);
            $td.append($delete);
        }
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
    $('#cardCodeStart').val("");
    $('#cardCodeStart').removeClass('null');
    $('#cardCodeStartTip').html("");
    $('#cardCodeStartTip').css('color', 'black');
    $('#cardCodeEnd').val("");
    $('#cardCodeEnd').removeClass('null');
    $('#cardCodeEndTip').html("");
    $('#cardCodeEndTip').css('color', 'black');
    $('#prefix').val("");
    $('#prefix').removeClass('null');
    $('#prefixTip').html("");
    $('#prefixTip').css('color', 'black');
}

//提交删除
function del() {
    if (confirm("确定删除吗？")) {
        let cardIds = [];
        cardIds.push($(this).attr("cardId"));
        $.ajax({
            url: 'card.do?method=deleteCard',
            type: 'post',
            dataType: 'json',
            data: {
                cardIds: JSON.stringify(cardIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert(resp.msgInfo + "成功删除了" + resp.data + "张卡！");
                    search();
                } else {
                    alert("删除失败！(注意：只能删除待领用的卡)");
                }
            }
        });
    }
}

//提交批量删除
function delBatch() {
    if (confirm("确定删除吗？")) {
        let cardIds = [];
        $(':checkbox[name=select]').each(function () {
            if ($(this).is(':checked')) {
                cardIds.push($(this).attr("cardId"));
            }
        });
        $.ajax({
            url: 'card.do?method=deleteCard',
            type: 'post',
            dataType: 'json',
            data: {
                cardIds: JSON.stringify(cardIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    $('#selectAll').prop('checked', false);
                    alert(resp.msgInfo + "成功删除了" + resp.data + "张卡！");
                    search();
                } else {
                    alert("删除失败！(注意：只能删除待领用的卡)");
                }
            }
        });
    }
}

//提交新增
function insertCommit() {
    if ($('#cardCodeStart').val() === "" || !/^[0-9]{1,8}$/.test($('#cardCodeStart').val())) {
        return;
    }
    if ($('#cardCodeEnd').val() === "" || !/^[0-9]{1,8}$/.test($('#cardCodeStart').val()) ||
        parseInt($('#cardCodeEnd').val()) < parseInt($('#cardCodeStart').val())) {
        return;
    }
    if ($('#prefix').val() === "" || !/^[a-zA-Z]{1,5}$/.test($('#prefix').val())) {
        return;
    }
    if (confirm("确定提交吗？")) {
        $("#insert-commit").off("click");
        $.ajax({
            url: 'card.do?method=insertCard',
            type: 'post',
            dataType: 'json',
            data: {
                cardCodeStart: $('#cardCodeStart').val(),
                cardCodeEnd: $('#cardCodeEnd').val(),
                prefix: $("#prefix").val()
            },
            success: function (resp) {
                $('#insert-commit').click(insertCommit);
                if (resp.state === 0) {
                    $('#insert-div').hide();
                    $('#mask').hide();
                    alert(resp.msgInfo + "成功入库了" + resp.data + "张卡！");
                    search();
                } else {
                    alert("所有卡号均已入库，入库失败！");
                }
            }
        });
    }
}