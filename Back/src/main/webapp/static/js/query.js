//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
//绑定事件
$('#selectAll').change(selectAll);
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);
//查询div控件
$('#query-back').click(function () {
    $('#query-div').hide();
    $('#mask').hide();
});
//刷新一卡通状态下拉框
refreshCardStateList(0);
//查询数据
search();

//以下是封装函数
//查找数据
function search() {
    let cardCode = $('#cardCode').val();
    let stateString = $("#cardState").find("option:selected").attr("param");
    let stateId = "";
    if (stateString !== undefined) {
        stateId = JSON.parse(stateString).paramId;
    }
    let userString = $("#applyUser").find("option:selected").attr("user");
    let userId = "";
    if (userString !== undefined) {
        userId = JSON.parse(userString).userId;
    }
    let queryBeans = [
        {field: 'CARD_CODE', operator: 'LIKE', value: cardCode.trim().toUpperCase()},
        {field: 'PARAM_STATE_ID', operator: '=', value: stateId},
        {field: "APPLY_USER_ID", operator: '=', value: userId}
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
        let $td = $('<td></td>');

        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(card.cardCode);
        $tr.append($td);
        $td = $('<td></td>');
        $td.append(card.state.paramName);
        $tr.append($td);
        $td = $('<td></td>');
        if (card.applyBean != null) {
            $td.append(card.applyBean.applyUser.name);
        } else {
            $td.append("无");
        }
        $tr.append($td);
        $td = $('<td></td>');
        if (card.patientBean != null) {
            $td.append(card.patientBean.name);
        } else {
            $td.append("无");
        }
        $tr.append($td);
        $td = $('<td></td>');
        let $query = $('<a>查看</a>');
        $query.attr('card', JSON.stringify(card));
        $query.click(query);
        $td.append($query);
        $tr.append($td);
        $('#data').append($tr);
    }
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

//查看详情
function query() {
    $('#query-div').show();
    $('#mask').show();
    let card = JSON.parse($(this).attr('card'));
    $('#query-cardCode').val(card.cardCode);
    $('#query-cardState').val(card.state.paramName);
    $('#query-amount').val(card.amount);
    if (card.patientBean != null) {
        $('#query-patient').val(card.patientBean.name);
    } else {
        $('#query-patient').val('无');
    }
    if (card.applyBean != null) {
        $('#query-applyUser').val(card.applyBean.applyUser.name);
        $('#query-auditDate').val(formatDate(card.applyBean.auditTime));
    } else {
        $('#query-applyUser').val('无');
        $('#query-auditDate').val(undefined);
    }
    if (card.saleUser != null) {
        $('#query-saleUser').val(card.saleUser.name);
    } else {
        $('#query-saleUser').val('无');
    }
    if (card.saleTime != null) {
        $('#query-saleDate').val(formatDate(card.saleTime));
    } else {
        $('#query-saleDate').val(undefined);
    }
}