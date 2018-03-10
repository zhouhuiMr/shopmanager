(function (window) {
    var clientUser = function () {
        tableAbstract.call(this);

        this.url = "../action/manager/clientuser/list";//请求列表的地址

        this.container = $("#clientuser-container");

        var o = this;
        this.pageSpan.callback = function (currnumber) {
            o.pageSpan.currnumber = currnumber;
            o.request();
        }

        this.addTileListen();
        this.setLoading();
        this.setPageColumn();
    }
    clientUser.prototype = tableAbstract.prototype;
    clientUser.prototype.addTileListen = function () {
        var obj = this;
        var column = new columnAction(function () {
            obj.colname = column.clickColname;
            obj.pageSpan.currnumber = 1;
            obj.request(function () {
                obj.pageSpan.init();
            });
        });
        $(".bt-search").bind('click', function () {
            obj.condition = $("#search-input").val();
            obj.pageSpan.currnumber = 1;
            obj.request(function () {
                obj.pageSpan.init();
            });
        });
        var isStop = -1;
        var setChangeTips = function(){
            var xml = "<p class=\"font-weight-bold text-danger\">";
            xml += " <i class=\"fa fa-exclamation fa-lg\"></i>&nbsp;\u60a8\u9009\u62e9\u7684\u7f16\u53f7\uff1a"+obj.selectRowID+"\u3002";
            xml += "</p>";
            obj.setTipsMessage("",xml);
        };
        var setNoSelectTips = function(){
            var xml = "<p class=\"font-weight-bold text-danger\">";
            xml += " <i class=\"fa fa-exclamation fa-lg\"></i>&nbsp;\u8bf7\u9009\u62e9\u4fee\u6539\u7684\u884c\u6570\u3002";
            xml += "</p>";
            obj.setTipsMessage("",xml);
        }
        $(".user-stop").bind('click',function(){
            if(obj.selectRowID > 0){
                isStop = 1;
                // $("#modal-all > .modal-dialog > .modal-content > .modal-footer").css({"display":"block"});
                setChangeTips();
            }else{
                // $("#modal-all > .modal-dialog > .modal-content > .modal-footer").css({"display":"none"});
                setNoSelectTips();
            }
        });
        $(".user-start").bind('click',function(){
            if(obj.selectRowID > 0){
                isStop = 0;
                // $("#modal-all > .modal-dialog > .modal-content > .modal-footer").css({"display":"block"});
                setChangeTips();
            }else{
                // $("#modal-all > .modal-dialog > .modal-content > .modal-footer").css({"display":"none"});
                setNoSelectTips();
            }
        });

        $(".modal-dialog > .modal-content > .modal-footer > .btn-primary").bind('click',function(){
            if(obj.selectRowID > 0){
                $.ajax({
                    url:"../action/manager/clientuser/change",
                    type:"POST",
                    data:{
                        "id":obj.selectRowID,
                        "status":isStop
                    },
                    error : function(){
                        window.location.href = window.ERRORPAGE;
                    },
                    success:function(data){
                        if("0000" == data.code){
                            var id = data.data.id;
                            obj.request(function(){
                                var xml = "";
                                xml += "<p class='font-weight-bold text-info'>";
                                xml += "\u4fee\u6539\u7f16\u53f7\uff1a"+id+"\u6210\u529f\u3002";
                                xml += "</p>";
                                obj.setTipsMessage("",xml);
                            });
                        }
                    }
                });
                isStop = -1
                obj.selectRowID = 0;
            }else{
                obj.modalContainer.modal('hide');
            }
        });
    };
    clientUser.prototype.setPageColumn = function () {
        var obj = this;
        $("#pageColumnSelect").on('change', function () {
            obj.everyPageCol = $(this).val();
            obj.pageSpan.currnumber = 1;
            obj.request(function () {
                obj.pageSpan.init();
            });
        });
    };
    clientUser.prototype.request = function (callback) {
        var obj = this;
        $.ajax({
            url: obj.url,
            type: "POST",
            data: {
                "column": obj.everyPageCol,
                "curpage": obj.pageSpan.currnumber,
                "colname": obj.colname,
                "condition": obj.condition
            },
            error: function () {
                window.location.href = window.ERRORPAGE;
            },
            success: function (data) {
                if ("0000" == data.code) {
                    obj.pageSpan.pagecount = data.data.pagecount;
                    obj.dataList = data.data.clientuserlist;
                    obj.setData();
                    obj.selectRowID = 0;
                    if (callback != null) {
                        callback();
                    }
                }
            }
        })
    };
    clientUser.prototype.setData = function () {
        if (this.dataList != null) {
            this.xml = "";
            if(this.dataList.length > 0){
                for (var i = 0; i < this.dataList.length; i++) {
                    var user = this.dataList[i];
                    this.xml += "<tr>";
                    this.xml += "<th scope=\"row\">" + user.id + "</th>";
                    this.xml += "<td>" + user.username + "</td>";
                    this.xml += "<td>" + user.telphone + "</td>";
                    this.xml += "<td>" + user.province + "</td>";
                    this.xml += "<td>" + user.city + "</td>";
                    this.xml += "<td>" + user.area + "</td>";
                    this.xml += "<td>" + user.level + "</td>";
                    this.xml += "<td>" + user.feeamount + "</td>";
                    this.xml += "<td>" + user.grades + "</td>";
                    this.xml += "<td>" + user.registtime + "</td>";
                    if (user.status == 0) {
                        this.xml += "<td>\u4f7f\u7528\u4e2d</td>";
                    } else {
                        this.xml += "<td>\u7981\u7528\u4e2d</td>";
                    }
                    this.xml += "</tr>";
                }
                this.container.find(".table > tbody").html(this.xml);
                $(".page-pagenum").html("\u5171" + this.pageSpan.pagecount + "\u9875");
                this.setTableAction();
            }else{
                var xml = "";
                xml += "<tr>";
                xml += " <td colspan=\"11\" style=\"text-align: center\">\u6682\u65e0\u6570\u636e</td>";
                xml += "</tr>";
                this.container.find(".table > tbody").html(xml);
                $(".page-pagenum").html("\u5171" + 1 + "\u9875");
            }
        }
    };
    clientUser.prototype.setTableAction = function () {
        var o = this;
        this.container.find(".table > tbody > tr").each(function () {
            $(this).bind('click', function () {
                $(this).parent().find("tr").each(function () {
                    $(this).css({"background-color": "#fff"});
                });
                $(this).css({"background-color": "#e9ecef"});
                o.selectRowID = $(this).find("th").html();
            });
        });
    };
    clientUser.prototype.setLoading = function(){
        var xml = "";
        xml += "<tr>";
        xml += " <td colspan=\"11\" style=\"text-align: center\">";
        xml += "  <i class=\"fa fa-spinner fa-2x fa-spin\"></i>";
        xml += " </td>";
        xml += "</tr>";
        this.container.find(".table > tbody").html(xml);
    };
    clientUser.prototype.setTipsMessage = function(title , style){
        if(this.modalContainer.length > 0){
            if(title != "" && title != null){
                $("#tipsModalLongTitle").html(title);
            }
            $("#modal-all > .modal-dialog > .modal-content > .modal-body").html(style);
            this.modalContainer.modal('show');
        }
    };
    window.clientUser = clientUser;
})(window);