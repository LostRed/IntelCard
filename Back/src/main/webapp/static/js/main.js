//菜单集合
let menuList = [];
//绑定事件
$('#exit').click(function () {
    $('#exit-div').show();
    $('#mask').show();
});
$('#cancel').click(function () {
    $('#exit-div').hide();
    $('#mask').hide();
});
$('#sure').click(exit);
//请求菜单
$.ajax({
    url: 'menu.do?method=findByUserId',
    type: 'post',
    dataType: 'json',
    data: {},
    success: function (resp) {
        if (resp.state === 0) {
            menuList = resp.data;
            loadingMenu(0, $('#menu'));
            addEffect();
        }
    }
});

//以下是封装函数
//退出系统
function exit() {
    $("#sure").off("click");
    $.ajax({
        url: 'user.do?method=exit',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            $('#sure').click(exit);
            if (resp.state === 0) {
                location.href = "../login.jsp";//重定向至登录界面
            }
        }
    });
}

/**
 * 载入菜单
 * @param parentId 菜单的父id
 * @param $rootUl dom菜单节点
 */
function loadingMenu(parentId, $rootUl) {
    $rootUl.empty();
    for (let i = 0; i < menuList.length; i++) {
        let pid = 0;
        //菜单的父菜单不为空时
        if (menuList[i].parentMenuBean != null) {
            pid = menuList[i].parentMenuBean.menuId;
        }
        //父菜单id相等时
        if (pid === parentId) {
            //构造菜单
            let $a = $('<a></a>')
            let $li = $('<li></li>');
            let $ul = $('<ul></ul>');
            if (menuList[i].pageHref !== null) {
                $a.prop('href', menuList[i].pageHref);//添加菜单链接
                $a.prop('target', 'view');//指定链接目标
            }
            let $span = $('<span></span>');
            $span.append(menuList[i].menuName);
            $a.append($span);
            if (parentId === 0) {
                let $label = $('<label></label>');
                $a.append($label);
            }
            //当菜单是二级菜单，将其隐藏
            if (parentId !== 0) {
                $ul.hide();
            }
            $li.append($a)
            $li.append($ul);
            $rootUl.append($li);
            //添加该菜单下的子菜单
            loadingMenu(menuList[i].menuId, $ul);
        }
    }
}
