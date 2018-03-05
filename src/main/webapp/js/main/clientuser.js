(function(window){
    var clientUser = function(){
        this.column = 2; //每页显示的行数
        this.curpage = 1; //当前的页数
        this.pagecount = 1; //总页数
        this.colname = ""; //按照排序的列的名称
        this.condition = ""; //查询的条件
        this.dataList = null; //数据的列表

        this.url = "../action/manager/clientuser/list";//请求列表的地址

        this.xml = ""; //table 的内容

        this.container = "#clientuser-container";

        this.pageSpan = new pageContainer();

        this.addTileListen();
        this.setLoading();
        this.pageColumn();
        this.pageSpan.init(13)
    }
    clientUser.prototype = {
        addTileListen : function(){
            var obj = this;
            var column = new columnAction(function(){
                obj.colname = column.clickColname;
                obj.request();
            });
        },
        pageColumn : function(){
            var obj = this;
            $("#pageColumnSelect").on('change',function(){
                obj.column = $(this).val();
                obj.request();
            });
        },
        request : function(){
            var obj = this;
            $.ajax({
                url : obj.url,
                type : "POST",
                data : {
                    "column" : obj.column,
                    "curpage" : obj.curpage,
                    "colname" : obj.colname,
                    "condition" : obj.condition
                },
                error : function(){
                    // window.location.href = window.ERRORPAGE;
                },
                success : function(data){
                    if("0000" == data.code){
                        if(data.data.count > 0){
                            obj.column = data.data.column;
                            obj.curpage = data.data.curpage;
                            obj.pagecount = data.data.pagecount;
                            obj.dataList = data.data.clientuserlist;
                            obj.setData();
                        }
                    }
                }
            })
        },
        setData : function(){
            if(this.dataList != null){
                this.xml = "";
                for(var i=0;i<this.dataList.length;i++){
                    var user = this.dataList[i];
                    this.xml += "<tr>";
                    this.xml += "<th scope=\"row\">"+user.id+"</th>";
                    this.xml += "<td>"+user.username+"</td>";
                    this.xml += "<td>"+user.telphone+"</td>";
                    this.xml += "<td>"+user.province+"</td>";
                    this.xml += "<td>"+user.city+"</td>";
                    this.xml += "<td>"+user.area+"</td>";
                    this.xml += "<td>"+user.level+"</td>";
                    this.xml += "<td>"+user.grades+"</td>";
                    this.xml += "<td>"+user.registtime+"</td>";
                    if(user.status == 0){
                        this.xml += "<td>\u4f7f\u7528\u4e2d</td>";
                    }else{
                        this.xml += "<td>\u7981\u7528\u4e2d</td>";
                    }
                    this.xml += "</tr>";
                }
                $(this.container).find(".table > tbody").html(this.xml);
                $(".page-pagenum").html("\u5171"+this.pagecount+"\u9875");
            }
        },
        setLoading : function(){
            var xml = "";
            xml += "<tr>";
            xml += " <td colspan=\"10\" style=\"text-align: center\">";
            xml += "  <i class=\"fa fa-spinner fa-2x fa-spin\"></i>";
            xml += " </td>";
            xml += "</tr>";
            $(this.container).find(".table > tbody").html(xml);
        }
    }
    window.clientUser = clientUser;
})(window);