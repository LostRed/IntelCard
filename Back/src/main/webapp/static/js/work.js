//定义页码
let currentPage = 1;
let totalPage = 1;
let pageSize = 5;
//绑定事件
$('#pageup').click(pageup);
$('#pagedown').click(pagedown);
$('#approve').click(search);
//刷新工作人员集合
refreshWorkUserList();
//查找数据
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
    let userString = $("#user").find("option:selected").attr("user");
    let userId = "";
    if (userString !== undefined) {
        userId = JSON.parse(userString).userId;
    }
    let queryBeans = [
        {field: "TIME", operator: '>=', value: date1},
        {field: "TIME", operator: '<=', value: date2},
        {field: "USER_ID", operator: '=', value: userId}
    ];
    find(queryBeans);
}

//提交多条件查找数据
function find(queryBeans) {
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'statistics.do?method=findByPage',
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
            $('#loading').hide();
            if (resp.state === 0) {
                refreshPage(resp.pageBean);
                refreshTable(resp.data);
                showChart(resp.data);
            }
        }
    });
}

//刷新表格数据
function refreshTable(list) {
    $('#data').empty();
    for (let i = 0; i < list.length; i++) {
        let statistics = list[i];
        let $tr = $('<tr></tr>');
        //序号
        let $td = $('<td></td>');
        $td.append((currentPage - 1) * pageSize + i + 1);
        $tr.append($td);
        //姓名
        $td = $('<td></td>');
        $td.append(statistics.name);
        $tr.append($td);
        //售卡数
        $td = $('<td></td>');
        $td.append(statistics.sold);
        $tr.append($td);
        //换卡数
        $td = $('<td></td>');
        $td.append(statistics.change);
        $tr.append($td);
        //退卡数
        $td = $('<td></td>');
        $td.append(statistics.refund);
        $tr.append($td);
        //处方登记数
        $td = $('<td></td>');
        $td.append(statistics.presReg);
        $tr.append($td);
        //处方退费数
        $td = $('<td></td>');
        $td.append(statistics.refundPres);
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

//显示图表
function showChart(list) {
    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById('chart'));
    let x = [];
    let map_data1 = [];
    let map_data2 = [];
    let map_data3 = [];
    let map_data4 = [];
    let map_data5 = [];
    for (let i = 0; i < list.length; i++) {
        let statistics = list[i];
        x.push(statistics.name);
        map_data1.push(statistics.sold);
        map_data2.push(statistics.change);
        map_data3.push(statistics.refund);
        map_data4.push(statistics.presReg);
        map_data5.push(statistics.refundPres);
    }
    option = {
        color: ['#0b6589', '#15799c', '#1cade4'
            , '#56b8e0', '#96d1ea'],
        title: {
            text: '工作量统计'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '0 % ',
            right: '0 % ',
            bottom: '10 % ',
            top: '10 % ',
            containLabel: true
        },
        calculable: true,
        xAxis: [{
            type: 'category',
            axisTick: {
                show: false
            },
            data: x
        }],
        yAxis: [{
            type: 'value'
        }],
        legend: {
            type: 'scroll',
            bottom: 0,
            data: ['售卡数', '换卡数', '退卡数', '登记处方数', '处方退费数']
        },
        series: [{
            name: '售卡数',
            type: 'bar',
            barGap: 0,
            data: map_data1
        },
            {
                name: '换卡数',
                type: 'bar',
                data: map_data2
            },
            {
                name: '退卡数',
                type: 'bar',
                data: map_data3
            },
            {
                name: '登记处方数',
                type: 'bar',
                data: map_data4
            },
            {
                name: '处方退费数',
                type: 'bar',
                data: map_data5
            }
        ]
    };
    myChart.setOption(option);
    window.onresize = myChart.resize;
}