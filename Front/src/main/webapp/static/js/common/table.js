/**
 * 全选复选框点击后
 */
function selectAll() {
    let flag = $(this).prop('checked');
    $(':checkbox[name=select]').each(function () {
        if ($(this).attr('disabled') !== 'disabled') {
            $(this).prop('checked', flag);
        }
    });
    check();
}

/**
 * 复选框点击后
 */
function selectThis() {
    if (!$(this).prop('checked')) {
        $('#selectAll').prop('checked', false);
    }
    check();
}

/**
 * 检查全选复选框状态是否还处于选中状态
 */
function check() {
    let selectedNum = 0;
    let disabledNum = 0;
    $(':checkbox[name=select]').each(function () {
        if ($(this).attr('disabled') !== 'disabled' && $(this).prop('checked')) {
            selectedNum++;
        } else if ($(this).attr('disabled') === 'disabled') {
            disabledNum++;
        }
    });
    if (selectedNum > 0) {
        //将删除按钮变为可用状态
        $('#delete').prop('disabled', false);
        //将取号按钮变为可用状态
        $('#offer').prop('disabled', false);
    } else {
        //将删除按钮变为不可用状态
        $('#delete').prop('disabled', true);
        //将取号按钮变为不可用状态
        $('#offer').prop('disabled', true);
    }
    if ($(':checkbox[name=select]').length > 0) {
        if (selectedNum + disabledNum === $(':checkbox[name=select]').length) {
            $('#selectAll').prop('checked', true);
        } else {
            $('#selectAll').prop('checked', false);
        }
    } else {
        $('#selectAll').prop('checked', !$('#selectAll').prop('checked'));
    }
    //改变按钮样式
    if (!$('#delete').prop('disabled')) {
        $('#delete').css('background-color', '#1cade4');
    } else {
        $('#delete').css('background-color', 'gray');
    }

    if (!$('#offer').prop('disabled')) {
        $('#offer').css('background-color', '#1cade4');
    } else {
        $('#offer').css('background-color', 'gray');
    }
}

/**
 * 上一页
 */
function pageup() {
    if (currentPage > 1) {
        currentPage--;
        search();
    }
}

/**
 * 下一页
 */
function pagedown() {
    if (currentPage < totalPage) {
        currentPage++;
        search();
    }
}

/**
 * 刷新因为吗
 * @param pageBean 分页对象
 */
function refreshPage(pageBean) {
    currentPage = pageBean.currentPage;
    totalPage = pageBean.totalPage;
    $('#page').html(currentPage + '/' + totalPage);
}

/**
 * 插入窗口的拖动
 */
$(function () {
    let box = $('#insert-div');
    $('#insert-drag').mousedown(
        function (event) {
            let isMove = true;
            let abs_x = event.pageX - box.offset().left;
            let abs_y = event.pageY - box.offset().top;
            $(document).mousemove(function (e) {
                    if (isMove) {
                        let moveX = e.clientX - abs_x;
                        let moveY = e.clientY - abs_y;
                        let maxX = $(window).width() - box.width();//X轴可移动最大距离
                        let maxY = $(window).height() - box.height();//Y轴可移动最大距离
                        //范围限定  最小时取最大  最大时取最小
                        moveX = Math.min(maxX, Math.max(0, moveX));
                        moveY = Math.min(maxY, Math.max(0, moveY));
                        box.css({'left': moveX, 'top': moveY});
                        box.css('transform', 'none');
                    }
                }
            ).mouseup(
                function () {
                    isMove = false;
                }
            );
        }
    );
});
/**
 *更新窗口的拖动
 */
$(function () {
    let box = $('#update-div');
    $('#update-drag').mousedown(
        function (event) {
            let isMove = true;
            let abs_x = event.pageX - box.offset().left;
            let abs_y = event.pageY - box.offset().top;
            $(document).mousemove(function (e) {
                    if (isMove) {
                        let moveX = e.clientX - abs_x;
                        let moveY = e.clientY - abs_y;
                        let maxX = $(window).width() - box.width();//X轴可移动最大距离
                        let maxY = $(window).height() - box.height();//Y轴可移动最大距离
                        //范围限定  最小时取最大  最大时取最小
                        moveX = Math.min(maxX, Math.max(0, moveX));
                        moveY = Math.min(maxY, Math.max(0, moveY));
                        box.css({'left': moveX, 'top': moveY});
                        box.css('transform', 'none');
                    }
                }
            ).mouseup(
                function () {
                    isMove = false;
                }
            );
        }
    );
});
/**
 * 查询窗口的拖动
 */
$(function () {
    let box = $('#query-div');
    $('#query-drag').mousedown(
        function (event) {
            let isMove = true;
            let abs_x = event.pageX - box.offset().left;
            let abs_y = event.pageY - box.offset().top;
            $(document).mousemove(function (e) {
                    if (isMove) {
                        let moveX = e.clientX - abs_x;
                        let moveY = e.clientY - abs_y;
                        let maxX = $(window).width() - box.width();//X轴可移动最大距离
                        let maxY = $(window).height() - box.height();//Y轴可移动最大距离
                        //范围限定  最小时取最大  最大时取最小
                        moveX = Math.min(maxX, Math.max(0, moveX));
                        moveY = Math.min(maxY, Math.max(0, moveY));
                        box.css({'left': moveX, 'top': moveY});
                        box.css('transform', 'none');
                    }
                }
            ).mouseup(
                function () {
                    isMove = false;
                }
            );
        }
    );
});
/**
 * 预约窗口的拖动
 */
$(function () {
    let box = $('#appoint-div');
    $('#appoint-drag').mousedown(
        function (event) {
            let isMove = true;
            let abs_x = event.pageX - box.offset().left;
            let abs_y = event.pageY - box.offset().top;
            $(document).mousemove(function (e) {
                    if (isMove) {
                        let moveX = e.clientX - abs_x;
                        let moveY = e.clientY - abs_y;
                        let maxX = $(window).width() - box.width();//X轴可移动最大距离
                        let maxY = $(window).height() - box.height();//Y轴可移动最大距离
                        //范围限定  最小时取最大  最大时取最小
                        moveX = Math.min(maxX, Math.max(0, moveX));
                        moveY = Math.min(maxY, Math.max(0, moveY));
                        box.css({'left': moveX, 'top': moveY});
                        box.css('transform', 'none');
                    }
                }
            ).mouseup(
                function () {
                    isMove = false;
                }
            );
        }
    );
});