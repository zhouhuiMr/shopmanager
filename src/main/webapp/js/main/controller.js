window.HtmlList = new Array();
window.SUCCESSMSG = "success";
window.ERRORMSG = "error";
window.ERRORPAGE = "";
window.HTMLPage = [
    ["../views/home/home.html"],
    ["../views/clientuser/clientuser.html","../views/clientuser/userlevel.html"]
];
(function(window){
    var HtmlObject = function(){
        this.name = "";
        this.url = "";
        this.precontents = "";
        this.contents = "";
        this.container = "";
        this.type = "xml";
        this.createTime = new Date().getTime();
        this.message = ERRORMSG;
    }
    window.HtmlObject = HtmlObject;

    var analysisHTML = function(obj){
        if(obj.message == SUCCESSMSG){
            var DOM = new DOMParser().parseFromString(obj.precontents, "text/html");
            obj.container = DOM.body;
            obj.contents = DOM.body.innerHTML
        }else{
            return null;
        }
    };

    var getHTML = function(url,callback){
        this.url = url;
        this.callback = callback;
        this.HO = new HtmlObject();
        this.init();
    };
    getHTML.prototype = {
        init : function(){
            //set url
            this.HO.url = this.url;
            //set name
            var u = this.url.split("/");
            this.HO.name = u[u.length-1];
            //set content
            var o = this;
            $.ajax({
                url:o.url,
                type:"GET",
                data:{},
                error:function(){
                    o.HO.message = ERRORMSG;
                },
                success:function(data){
                    o.HO.message = SUCCESSMSG;
                    o.HO.precontents = data.replace(/[\r\n]/g,"");
                    analysisHTML(o.HO);
                    if(o.HO.contents != null){
                        HtmlList.push(o.HO);
                    }
                    o.callback(o.HO);
                }
            });
        }
    };
    window.getHTML = getHTML;

    var HTMLFactory = function(url,callback){
        this.htmlList = HtmlList;
        this.url = url;
        this.callback = callback;

        this.controllerHTML();

        this.removeAllHtml = function(){
            HtmlList.splice(0,HtmlList.length);
        };
        this.removeHtmlByUrl = function(url){
            for(var i=0;i<HtmlList.length;i++){
                if(this.url == this.htmlList[i].url){
                    if(this.htmlList[i].message == SUCCESSMSG){
                        HtmlList.splice(i,1);
                        break;
                    }
                }
            }
        };
    }
    HTMLFactory.prototype = {
        controllerHTML : function(){
            var isExist = false;
            for(var i=0; i<this.htmlList.length;i++){
                if(this.url == this.htmlList[i].url){
                    if(this.htmlList[i].message == SUCCESSMSG){
                        isExist = true;
                        this.callback(this.htmlList[i]);
                        break;
                    }
                }
            }
            if(!isExist){
                new getHTML(this.url,this.callback);
            }
        }
    }
    window.HTMLFactory = HTMLFactory;
})(window);