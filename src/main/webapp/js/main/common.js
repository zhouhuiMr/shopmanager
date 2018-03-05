(function(window){
    var columnAction = function(callback){
        this.clickColname = "";
        this.callback = callback;
        this.init();
    }
    columnAction.prototype = {
        init : function(){
            var o = this;
            //表格列标题
            $(".table > thead > tr > th").each(function(){
                if($(this).find('span').length > 0){
                    $(this).bind('click',function(){
                        var obj = $(this).index();
                        $(".table > thead > tr > th").each(function(){
                            if(obj != $(this).index()){
                                if($(this).find('span').length > 0){
                                    $(this).find('span > .fa').removeClass('fa-angle-up').addClass('fa-angle-down');
                                }
                            }
                        });
                        if($(this).find('span > .fa-angle-up').length > 0){
                            $(this).find('span > .fa').removeClass('fa-angle-up').addClass('fa-angle-down');
                            o.clickColname = "";
                        }else{
                            $(this).find('span > .fa').removeClass('fa-angle-down').addClass('fa-angle-up');
                            o.clickColname = $(this).attr('name');
                        }
                        o.callback();
                    });
                }
            });
        }
    }
    window.columnAction = columnAction;

    var pageContainer = function(){
        this.number = 5; //一次显示的最多页数
        this.pagecount = 1; //总页数
        this.currnumber = 1;//当前页数
        this.xml = "";
        this.pageMap = new Array();
        this.container = $(".pagination");
    }
    pageContainer.prototype = {
        init : function(pagecount){
            this.pagecount = pagecount;

            var num = -1;
            for(var i=1;i<=this.pagecount;i++){
                var a = null;
                if((i%this.number) == 1){
                    num ++;
                    a = new Array();
                    this.pageMap.push(a);
                }
                this.pageMap[num].push(i);
            }
            console.info(this.pageMap);
        },
        setPage : function(start,end){
            this.xml = "";
            this.xml += "<li class=\"page-item page-previous\">";
            this.xml += "  <a class=\"page-link\" aria-label=\"Previous\">";
            this.xml += "   <span aria-hidden=\"true\">&laquo;</span>";
            this.xml += "   <span class=\"sr-only\">Previous</span>";
            this.xml += "  </a>";
            this.xml += "</li>";
            for(var i=start;i<=end;i++){
                this.xml += "<li class=\"page-item page-number\"><a class=\"page-link\">"+i+"</a></li>";
            }
            this.xml += "<li class=\"page-item page-next\">";
            this.xml += "  <a class=\"page-link\" aria-label=\"Next\">";
            this.xml += "   <span aria-hidden=\"true\">&raquo;</span>";
            this.xml += "   <span class=\"sr-only\">Next</span>";
            this.xml += "  </a>";
            this.xml += "</li>";
            this.container.html(this.xml);
            this.setListen();
        },
        setListen : function(){
            var o = this;
            var setbackColor = function(obj){
                obj.container.find('.page-number').each(function(){
                   $(this).find('.page-link').css({"background":"#fff"});
                });
            };
            this.container.find(".page-previous").bind('click',function(){
                if(this.currnumber > 1){
                    this.currnumber --;
                    setbackColor(o);
                }
            });
            this.container.find(".page-next").bind('click',function(){
                if(this.currnumber < this.pagecount){
                    this.currnumber ++;
                    setbackColor(o);
                }
            });

            this.container.find(".page-number").each(function(){
                $(this).bind('click',function(){
                    this.currnumber = $(this).index();
                    setbackColor(o);
                    $(this).find('.page-link').css({"background":"#e9ecef"});
                });
            });
        }
    }
    window.pageContainer = pageContainer;
})(window)