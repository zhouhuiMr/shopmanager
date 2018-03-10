(function(window){
    var tableAbstract = function(){
        this.everyPageCol = 10;
        this.colname = ""; //按照排序的列的名称
        this.condition = ""; //查询的条件
        this.dataList = null; //数据的列表
        this.selectRowID = 0; //当前选中的行的id

        this.url = "";//请求列表的地址

        this.xml = ""; //table 的内容

        this.container = null;

        this.modalContainer = $("#modal-all");

        this.pageSpan = new pageContainer();//页码标签

    }
    tableAbstract.prototype = {
        addTileListen : function(){},
        setPageColumn : function(){},
        request : function(callback){},
        setData : function(){},
        setTableAction : function(){},
        setLoading : function(){},
        setTipsMessage : function(style){}
    }
    window.tableAbstract = tableAbstract;

    var columnAction = function(callback){
        this.clickColname = "";
        this.callback = callback;
        this.init();
    };
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
        this.mapnumber = 0;//当前所在的组数
        this.xml = "";
        this.pageMap = new Array();
        this.container = $(".pagination");
        this.callback = null;
    }
    pageContainer.prototype = {
        init : function(){
            if(this.pagecount <= 0){
                this.pagecount = 1;
                this.currnumber = 1;
            }
            this.pageMap.splice(0,this.pageMap.length);
            this.mapnumber = 0;
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
            this.setPage(this.mapnumber);

        },
        setPage : function(start){
            this.xml = "";
            this.xml += "<li class=\"page-item page-previous\">";
            this.xml += "  <a class=\"page-link\" aria-label=\"Previous\">";
            this.xml += "   <span aria-hidden=\"true\">&laquo;</span>";
            this.xml += "   <span class=\"sr-only\">Previous</span>";
            this.xml += "  </a>";
            this.xml += "</li>";
            var pageArray = this.pageMap[start];
            for(var i=pageArray[0];i<=pageArray[pageArray.length-1];i++){
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
                //判断不在同一组进行页数的切换
                var consult = Math.ceil(obj.currnumber/obj.number)-1;
                if(consult != obj.mapnumber && consult > -1){
                    obj.mapnumber = consult;
                    obj.setPage(obj.mapnumber);
                }
                //将页码的背景色统一然后将选中的进行变色
                obj.container.find('.page-number').each(function(){
                   $(this).find('.page-link').css({"background":"#fff"});
                });
                var remainder = obj.currnumber%obj.number;
                if(remainder == 0){
                    obj.container.find('.page-number:eq('+(obj.number-1)+') > .page-link').css({"background":"#e9ecef"});
                }else{
                    obj.container.find('.page-number:eq('+(remainder-1)+') > .page-link').css({"background":"#e9ecef"});
                }
            };
            setbackColor(o);
            this.container.find(".page-previous").bind('click',function(){
                if(o.currnumber > 1){
                    o.currnumber --;
                    setbackColor(o);
                    if(o.callback != null){
                        o.callback(o.currnumber);
                    }
                }
            });
            this.container.find(".page-next").bind('click',function(){
                if(o.currnumber < o.pagecount){
                    o.currnumber ++;
                    setbackColor(o);
                    if(o.callback != null){
                        o.callback(o.currnumber);
                    }
                }
            });

            this.container.find(".page-number").each(function(){
                $(this).bind('click',function(){
                    o.currnumber = $(this).find('.page-link').text();
                    setbackColor(o);
                    if(o.callback != null){
                        o.callback(o.currnumber);
                    }
                });
            });
        }
    }
    window.pageContainer = pageContainer;
})(window)