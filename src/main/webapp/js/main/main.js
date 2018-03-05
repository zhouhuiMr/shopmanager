var url = "."
$(function(){
    //获取登录的用户名
    var username = getCookie("username"),
        lastlogin = getCookie("lastlogin");
    if(username != null && username != ""){
        $(".left-span > .card > .username").html(username);
        $(".left-span > .card > .lastlogin").html(lastlogin);
    }
    //退出登录
    $("#signout").bind('click',function(){
        document.cookie = "username=;path=/;expires=-1";
        document.cookie = "token=;path=/;expires=-1";
        document.cookie = "localtime=;path=/;expires=-1";
        window.location.href= "../index.html";
    });

    //菜单的动画效果
    $('.card').on('show.bs.collapse',function(){
        $(this).find('.card-header').css({"background":"#2d2e2f"});
        $(this).find('.card-header > .fa-right').removeClass('fa-chevron-left');
        $(this).find('.card-header > .fa-right').addClass('fa-chevron-down');
        var root = $(this).parent().find('.card > .card-header');
        root.each(function(){
            var c = $(this);
            if(c.attr('aria-expanded') == 'true'){
                c.parent().find('.collapse').collapse('hide')
            }
        });
    });
    $('.card').on('hide.bs.collapse', function () {
        $(this).find('.card-header').css({"background":"none"});
        $(this).find('.card-header > .fa-right').removeClass('fa-chevron-down');
        $(this).find('.card-header > .fa-right').addClass('fa-chevron-left');
    });

    //选择详细菜单
    $("#left-menu > .card").each(function(){
        var num = $(this).index();
        if(num == 0){
            //首页菜单
            $(this).find("#headingHome").bind("click",function(){
                var url = HTMLPage[num][$(this).index()];
                new HTMLFactory(url,function(obj){
                    $(".right-span > .html-container").html(obj.contents);
                });
            });
        }else{
            //详细菜单
            $(this).find(".collapse > .card-body > .menu-detail > .nav-item").each(function(){
                $(this).bind("click",function(){
                    var url = HTMLPage[num][$(this).index()];
                    new HTMLFactory(url,function(obj){
                        //设置页面
                        $(".right-span > .html-container").html(obj.contents);
                        new clientUser().request();
                    });
                });
            });
        }
    });

    function getCookie(name) {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }
    new HTMLFactory("../views/home/home.html",function(obj){
        $(".right-span > .html-container").html(obj.contents);
    });
});
