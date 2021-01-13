//定义页码
let currentPage = 1;//当前页码
let totalPage = 1;//总页码
let pageSize = 5;//每页显示的行数
//绑定事件
$('#selectAll').change(selectAll);
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);
$('#cancel').click(cancelBatch);
$('#cancel').attr('disabled', true);

//刷新一卡通状态下拉框
refreshCardStateList(2);
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
        {field: 'PARAM_STATE_ID', operator: '=', value: stateId}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'card.do?method=findCancelByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "CARD_CODE",
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
        let card = list[i];
        let $tr = $('<tr></tr>');
        //复选框
        let $td = $('<td></td>');
        let checkbox = $('<input type="checkbox"/>');
        checkbox.change(selectThis);
        checkbox.attr('name', 'select');
        checkbox.attr('cardId', card.cardId);
        $td.append(checkbox);
        //序号
        $tr.append($td);
        $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //一卡通卡号
        $td = $('<td></td>');
        $td.append(card.cardCode);
        $tr.append($td);
        //卡状态
        $td = $('<td></td>');
        $td.append(card.state.paramName);
        $tr.append($td);
        //操作
        $td = $('<td></td>');
        let $cancel = $('<a>注销</a>');
        $cancel.attr('cardId', card.cardId);
        $cancel.click(cancel);
        $td.append($cancel);
        $tr.append($td);
        $('#data').append($tr);
    }
    //填补剩余行数
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 5; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
}

//提交注销
function cancel() {
    if (confirm("确定注销吗？")) {
        let cardIds = [];
        cardIds.push($(this).attr("cardId"));
        $.ajax({
            url: 'card.do?method=cancelCard',
            type: 'post',
            dataType: 'json',
            data: {
                cardIds: JSON.stringify(cardIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    alert("成功注销了" + resp.data + "张卡！");
                    search();
                } else {
                    alert("注销失败！");
                }
            }
        });
    }
}

//提交批量注销
function cancelBatch() {
    if (confirm("确定注销吗？")) {
        let cardIds = [];
        $(':checkbox[name=select]').each(function () {
            if ($(this).is(':checked')) {
                cardIds.push($(this).attr("cardId"));
            }
        });
        $.ajax({
            url: 'card.do?method=cancelCard',
            type: 'post',
            dataType: 'json',
            data: {
                cardIds: JSON.stringify(cardIds)
            },
            success: function (resp) {
                if (resp.state === 0) {
                    $('#selectAll').prop('checked', false);
                    alert("成功注销了" + resp.data + "张卡！");
                    search();
                } else {
                    alert("注销失败！");
                }
            }
        });
    }
}