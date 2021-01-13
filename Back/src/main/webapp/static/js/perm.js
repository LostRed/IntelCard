//下拉框数据集合
let roleList = [];
let hasMenuList = [];
let hasNotMenuList = [];
let roleBean;
let addMenu;
let removeMenu;
//绑定事件
$('#btnAddAll').click(addAll);
$('#btnAdd').click(add);
$('#btnRemoveAll').click(removeAll);
$('#btnRemove').click(remove);
//载入角色
loadingRoleTree();
//以下是封装函数
//添加菜单滑动特效
$(function () {
    $(".menu-wrap>ul>li>a").click(function () {
        if ($(this).siblings('ul').css('display') === 'none') {
            $(this).siblings('ul').slideDown(200).children('li');
            $(this).parent('li').addClass('show');
        } else {
            $(this).siblings('ul').slideUp(200);
            $(this).parent('li').removeClass('show');
        }
    });
});

//载入角色树
function loadingRoleTree() {
    $.ajax({
        url: 'role.do?method=findAll',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            if (resp.state === 0) {
                roleList = resp.data;
                loadingRoleMenu($('#role'));
                addEffect();
            }
        }
    });
}

//载入角色菜单
function loadingRoleMenu($rootUl) {
    $rootUl.empty();
    for (let i = 0; i < roleList.length; i++) {
        let role = roleList[i];
        let $a = $('<a></a>')
        let $li = $('<li></li>');
        let $ul = $('<ul></ul>');
        $a.attr('role', JSON.stringify(role));
        $a.click(readMenu);
        let $span = $('<span></span>');
        $span.append(role.roleName);
        $a.append($span);
        $ul.hide();
        $li.append($a)
        $li.append($ul);
        $rootUl.append($li);
    }
}

//读取角色拥有的菜单
function readMenu() {
    roleBean = JSON.parse($(this).attr('role'));
    searchMenu();
}

//读取角色拥有的菜单
function searchMenu() {
    let flag1 = false;
    let flag2 = false;
    $('#loading').css('display', 'flex');
    //请求已有菜单
    $.ajax({
        url: 'menu.do?method=findHasByRoleId',
        type: 'post',
        dataType: 'json',
        data: {
            role: JSON.stringify(roleBean)
        },
        success: function (resp) {
            flag1 = true;
            if (flag1 && flag2) {
                $('#loading').hide();
            }
            if (resp.state === 0) {
                hasMenuList = resp.data;
                loadingHasMenu(0, $('#hasMenu'));
                console.log(hasMenuList)
                addHasMenuEffect();
            }
        }
    });
    //请求未有菜单
    $.ajax({
        url: 'menu.do?method=findHasNotByRoleId',
        type: 'post',
        dataType: 'json',
        data: {
            role: JSON.stringify(roleBean)
        },
        success: function (resp) {
            flag2 = true;
            if (flag1 && flag2) {
                $('#loading').hide();
            }
            if (resp.state === 0) {
                hasNotMenuList = resp.data;
                loadingHasNotMenu(0, $('#hasNotMenu'));
                console.log(hasNotMenuList)
                addHasNotMenuEffect();
            }
        }
    });
}

//载入已有菜单
function loadingHasMenu(parentId, $rootUl) {
    $rootUl.empty();
    for (let i = 0; i < hasMenuList.length; i++) {
        let menu = hasMenuList[i];
        let pid = 0;
        if (menu.parentMenuBean != null) {
            pid = menu.parentMenuBean.menuId;
        }
        if (pid === parentId) {
            let $a = $('<a></a>')
            let $li = $('<li></li>');
            let $ul = $('<ul></ul>');
            if (pid !== 0) {
                $a.attr('menu', JSON.stringify(menu));
            }
            if (parentId === 0) {
                let $label = $('<label></label>');
                $a.append($label);
            }
            let $span = $('<span></span>');
            $span.append(menu.menuName);
            $a.append($span);
            if (parentId !== 0) {
                $ul.hide();
            }
            $li.append($a)
            $li.append($ul);
            $rootUl.append($li);
            loadingHasMenu(menu.menuId, $ul);
        }
    }
}

//载入未有菜单
function loadingHasNotMenu(parentId, $rootUl) {
    $rootUl.empty();
    for (let i = 0; i < hasNotMenuList.length; i++) {
        let menu = hasNotMenuList[i];
        let pid = 0;
        if (menu.parentMenuBean != null) {
            pid = menu.parentMenuBean.menuId;
        }
        if (pid === parentId) {
            let $a = $('<a></a>')
            let $li = $('<li></li>');
            let $ul = $('<ul></ul>');
            if (pid !== 0) {
                $a.attr('menu', JSON.stringify(menu));
            }
            if (parentId === 0) {
                let $label = $('<label></label>');
                $a.append($label);
            }
            let $span = $('<span></span>');
            $span.append(menu.menuName);
            $a.append($span);
            if (parentId !== 0) {
                $ul.hide();
            }
            $li.append($a)
            $li.append($ul);
            $rootUl.append($li);
            loadingHasNotMenu(menu.menuId, $ul);
        }
    }
}

//添加角色菜单滑动特效
function addEffect() {
    $(function () {
        $("#role>li>a").click(function () {
            $("#role>li>a").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass('active');
        });
    });
}

//添加已有菜单滑动特效
function addHasMenuEffect() {
    $('#hasMenu>li>a').click(addMenuEffect);
    //添加二级菜单高亮选中效果
    $(function () {
        $("#hasMenu>li>ul>li>a").click(function () {
            $("#hasMenu>li>ul>li>a").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass('active');
            removeMenu = JSON.parse($(this).attr('menu'));
        });
    });
}

//添加未有菜单滑动特效
function addHasNotMenuEffect() {
    $('#hasNotMenu>li>a').click(addMenuEffect);
    //添加二级菜单高亮选中效果
    $(function () {
        $("#hasNotMenu>li>ul>li>a").click(function () {
            $("#hasNotMenu>li>ul>li>a").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass('active');
            addMenu = JSON.parse($(this).attr('menu'));
        });
    });
}

//添加一级菜单展开和收缩特效
function addMenuEffect() {
    if ($(this).siblings('ul').css('display') === 'none') {
        $(this).siblings('ul').slideDown(200).children('li');
        $(this).parent('li').addClass('show');
    } else {
        $(this).siblings('ul').slideUp(200);
        $(this).parent('li').removeClass('show');
    }
}

//分配所有菜单
function addAll() {
    if (roleBean == null) {
        alert("未选中角色！")
        return;
    }
    $('#btnAddAll').off('click');
    $.ajax({
        url: 'perm.do?method=insertAll',
        type: 'post',
        dataType: 'json',
        data: {
            role: JSON.stringify(roleBean),
            hasNotMenuList: JSON.stringify(hasNotMenuList)
        },
        success: function (resp) {
            $('#btnAddAll').click(addAll);
            if (resp.state === 0) {
                searchMenu();
            }
        }
    });
}

//分配选中的菜单
function add() {
    if (roleBean == null) {
        alert("未选中角色！")
        return;
    }
    if (addMenu == null) {
        alert("未选中菜单！")
        return;
    }
    $('#btnAdd').off('click');
    $.ajax({
        url: 'perm.do?method=insertPerm',
        type: 'post',
        dataType: 'json',
        data: {
            role: JSON.stringify(roleBean),
            addMenu: JSON.stringify(addMenu)
        },
        success: function (resp) {
            $('#btnAdd').click(add);
            if (resp.state === 0) {
                searchMenu();
            }
        }
    });
}

//移除所有菜单
function removeAll() {
    if (roleBean == null) {
        alert("未选中角色！")
        return;
    }
    $('#btnRemoveAll').off('click');
    $.ajax({
        url: 'perm.do?method=removeAll',
        type: 'post',
        dataType: 'json',
        data: {
            role: JSON.stringify(roleBean)
        },
        success: function (resp) {
            $('#btnRemoveAll').click(removeAll);
            if (resp.state === 0) {
                searchMenu();
            }
        }
    });
}

//移除选中的菜单
function remove() {
    if (roleBean == null) {
        alert("未选中角色！")
        return;
    }
    if (removeMenu == null) {
        alert("未选中菜单！")
        return;
    }
    $('#btnRemove').off('click');
    $.ajax({
        url: 'perm.do?method=removePerm',
        type: 'post',
        dataType: 'json',
        data: {
            role: JSON.stringify(roleBean),
            removeMenu: JSON.stringify(removeMenu)
        },
        success: function (resp) {
            $('#btnRemove').click(remove);
            if (resp.state === 0) {
                searchMenu();
            }
        }
    });
}
