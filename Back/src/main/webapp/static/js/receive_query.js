//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
//绑定事件
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);
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
        {field: "TO_CHAR(CREATE_TIME,'YYYY-MM-DD')", operator: '<=', value: date2}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'card.do?method=findReceiveByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "CARD_CODE",
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
        //序号
        let $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //卡号
        $td = $('<td></td>');
        $td.append(card.cardCode);
        $tr.append($td);
        //申请时间
        $td = $('<td></td>');
        if (card.applyBean != null) {
            $td.append(formatDate(card.applyBean.applyTime));
        } else {
            $td.append("无");
        }
        $tr.append($td);
        //审批时间
        $td = $('<td></td>');
        if (card.applyBean != null) {
            $td.append(formatDate(card.applyBean.auditTime));
        } else {
            $td.append("无");
        }
        $tr.append($td);
        //审批人
        $td = $('<td></td>');
        if (card.applyBean != null && card.applyBean.auditUser != null) {
            $td.append(card.applyBean.auditUser.name);
        } else {
            $td.append("无");
        }
        $tr.append($td);
        //卡绑定病人
        $td = $('<td></td>');
        if (card.patientBean != null) {
            $td.append(card.patientBean.name);
        } else {
            $td.append("无");
        }
        $tr.append($td);
        //卡余额
        $td = $('<td></td>');
        $td.append(card.amount + "元");
        $tr.append($td);
        //卡状态
        $td = $('<td></td>');
        $td.append(card.state.paramName);
        $tr.append($td);
        $('#data').append($tr);
    }
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
