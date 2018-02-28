var url = "."
$(function(){
    $("#basic-addon2").bind('click',function(){
        $(this).find('.rounded').attr('src',url+'/action/authcode/picauthcode?'+Math.random());
    });
    
    var tips = $(".text-danger"),
    	u = $(".username"),
    	p = $(".password"),
    	c = $(".authcode"),
    	bt = $("#manager-login");
    
    u.focus(function(){
    	tips.html("");
    	bt.removeAttr("disabled");
    });
    p.focus(function(){
    	tips.html("");
    	bt.removeAttr("disabled");
    });
    c.focus(function(){
    	tips.html("");
    	bt.removeAttr("disabled");
    });
    
    bt.bind('click',function(){
        $(this).attr("disabled","true");
        var username = u.val(),
            password = p.val(),
            authcode = c.val();
        if(username == ""){
            tips.html("\u8bf7\u8f93\u5165\u7528\u6237\u540d\uff01");
        }else if(password ==  ""){
        	tips.html("\u8bf7\u8f93\u5165\u5bc6\u7801\uff01");
        }else if(authcode == ""){
        	tips.html("\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801\uff01");
        }else{
        	$.ajax({
                url:url+"/action/manager/login",
                type:"post",
                data:{
                    username : username,
                    password : password,
                    authcode : authcode
                },
                error : function(){
                	tips.html("\u670d\u52a1\u5668\u9519\u8bef\uff01");
                },
                success : function(data){
                    var code = data.code;
                    if("0000" == code){
                    	window.location.href = url+"/views/main.html";
                    }else if("0003" == code){
                    	c.val("");
                    	$("#basic-addon2 .rounded").attr('src',url+'/action/authcode/picauthcode?'+Math.random());
                    	tips.html("\u9a8c\u8bc1\u7801\u9519\u8bef\uff01");
                    }else if("1001" == code){
                    	u.val("");
                    	p.val("");
                    	c.val("");
                    	$("#basic-addon2 .rounded").attr('src',url+'/action/authcode/picauthcode?'+Math.random());
                    	tips.html("\u7528\u6237\u540d\u6216\u8005\u5bc6\u7801\u9519\u8bef\uff01");
                    }else{
                    	tips.html("\u670d\u52a1\u5668\u9519\u8bef\uff01");
                    }
                }
             });
        }
    });
})