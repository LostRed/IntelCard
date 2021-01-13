//添加菜单动画特效
function addEffect() {
    //添加一级菜单的滑动特效以及展开和收缩
    $(function () {
        $('.menu-wrap>ul>li>a').click(function () {
            let id = $(this).parents('li').parents().attr('id');
            if ($(this).siblings('ul').css('display') === 'none') {
                if (id === 'menu') {
                    $(this).parents('li').siblings('li').children('ul').parent('li').children('a').removeClass('active');
                    $(this).parents('li').siblings('li').children('ul').slideUp(200);
                }
                $(this).addClass('active');
                $(this).siblings('ul').slideDown(200).children('li');
                $(this).parent('li').addClass('show').siblings('li').removeClass('show');
            } else {
                $(this).removeClass('active');
                $(this).siblings('ul').slideUp(200);
                $(this).parent('li').removeClass('show');
            }
        });
    });
    //添加二级菜单激活后的高亮显示特效
    $(function () {
        $(".menu-wrap>ul>li>ul>li>a").click(function () {
            $(".menu-wrap>ul>li>ul>li>a").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass("active");
        })
    });
}
