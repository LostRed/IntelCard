//定义全局日历
let calendar;
//定义日历的样式
document.addEventListener('DOMContentLoaded', function () {
    let initialLocaleCode = 'zh-cn';
    let calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        height: '100%',
        expandRows: true,
        slotMinTime: '08:00',
        slotMaxTime: '20:00',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth'
        },
        locale: initialLocaleCode,
        initialView: 'dayGridMonth',
        buttonIcons: false, // 是否显示按钮图标
        navLinks: true, // can click day/week names to navigate views
        editable: false,
        selectable: true,
        nowIndicator: true,
        dayMaxEvents: true, // 当天事件很多时，是否显示"更多"
        //定义点击日期的方法
        dateClick: function (info) {
            $("#insert-commit").attr("workTime", info.dateStr);
            if (new Date(info.dateStr) <= new Date()) {
                return;
            }
            showInsertDiv();
        },
        //定义点击事件的方法
        eventClick: function (info) {
            if (new Date(info.event.end) <= new Date()) {
                return;
            }
            if (confirm("确定删除该排班吗？")) {
                deleteWork(info.event);
            }
        }
    });
    calendar.render();
});

//绑定事件
$('#insert-commit').click(insertCommit);
$('#insert-back').click(function () {
    $("#insert-div").hide();
    $("#mask").hide();
});

//初始化数据
$(function () {
    refreshDoctorList();
    $('#loading').css('display', 'flex');
    $.ajax({
        url: 'appoint.do?method=findAll',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            $('#loading').hide();
            if (resp.state === 0) {
                showData(resp.data);
            }
        }
    });
});

//以下是封装函数
//显示医生排班数据
function showData(list) {
    for (let i = 0; i < list.length; i++) {
        let appoint = list[i];
        let endTime = new Date(appoint.workTime);
        endTime.setHours(endTime.getHours() + 1);
        //构造每一个日历记录
        let event = {
            id: appoint.appointId,
            title: appoint.doctor.name,
            start: new Date(appoint.workTime),
            end: endTime,
            allDay: false
        };
        //刷新日历里面的记录
        calendar.addEvent(event);
    }
}

//显示添加排班弹窗
function showInsertDiv() {
    $('#date').text($("#insert-commit").attr("workTime"));
    $("#doctor").prop('selectedIndex', 0);
    $("#workTime").prop('selectedIndex', 0);
    $("#insert-div").show();
    $("#mask").show();
}

//删除排班
function deleteWork(event) {
    $.ajax({
        url: 'appoint.do?method=deleteAppoint',
        type: 'post',
        dataType: 'json',
        data: {
            appointId: event.id
        },
        success: function (resp) {
            if (resp.state === 0) {
                event.remove();
                alert("删除成功！");
            } else {
                alert("删除失败！该排班已被预约。");
            }
        }
    });
}

//提交添加排班
function insertCommit() {
    let doctor = JSON.parse($("#doctor").find("option:selected").attr('user'));
    let workTime = new Date($("#insert-commit").attr("workTime") + " " + $("#workTime").find("option:selected").val());
    $.ajax({
        url: 'appoint.do?method=insertAppoint',
        type: 'post',
        dataType: 'json',
        data: {
            doctor: JSON.stringify(doctor),
            workTime: JSON.stringify(workTime)
        },
        success: function (resp) {
            if (resp.state === 0) {
                alert("新增成功！");
                let endTime = new Date(resp.data.workTime);
                endTime.setHours(endTime.getHours() + 1);
                let event = {
                    id: resp.data.appointId,
                    title: doctor.name,
                    start: workTime,
                    end: endTime,
                    allDay: false
                }
                calendar.addEvent(event);
                $("#insert-div").hide();
                $("#mask").hide();
            } else {
                alert("新增失败！该医生该时段已排班。");
            }
        }
    });
}
