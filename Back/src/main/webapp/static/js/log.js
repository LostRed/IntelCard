//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
//绑定事件
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);

search();

//查找数据
function search() {
    let date1 = $('#date1').val();
    let date2 = $('#date2').val();
    if (date1 > date2 && date1 !== "" && date2 !== "") {
        $('#date2').addClass('null');
    }else{
        $('#date2').removeClass('null');
    }
    let queryBeans = [
        {field: "TO_CHAR(LOG_TIME,'YYYY-MM-DD')", operator: '>=', value: date1},
        {field: "TO_CHAR(LOG_TIME,'YYYY-MM-DD')", operator: '<=', value: date2}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $.ajax({
        url: 'log.do?method=findByPage',
        type: 'post',
        dataType: 'json',
        data: {
            currentPage: currentPage,
            pageSize: pageSize,
            queryBeans: JSON.stringify(queryBeans),
            field: "LOG_TIME",
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
        let log = list[i];
        let $tr = $('<tr></tr>');
        //序号
        let $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //卡号
        $td = $('<td></td>');
        $td.append(log.actionUser.name);
        $tr.append($td);
        //申请时间
        $td = $('<td></td>');
        $td.append(formatTime(log.logTime));
        $tr.append($td);
        //操作事项
        $td = $('<td></td>');
        $td.append(log.logName);
        $tr.append($td);
        $('#data').append($tr);
    }
    //填充剩余行数
    let remainder = pageSize - list.length;
    for (let i = 0; i < remainder; i++) {
        let tr = $('<tr> </tr>');
        for (let j = 0; j < 4; j++) {
            let $td = $('<td> </td>');
            tr.append($td);
        }
        $('#data').append(tr);
    }
}
