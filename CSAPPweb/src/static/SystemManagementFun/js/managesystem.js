/**
 * Created by scw on 2017/8/2.
 */
$(function () {
    //设置鼠标到table中的行的显示效果的改变
    $('.userinfoshowtab tr').mouseenter(function() {
         $(this).css('background-color','#ffff66');
    })
    $('.userinfoshowtab tr').mouseout(function() {
         $(this).css('background-color','#d4e3e5');
    })

    //显示所有学生信息
    $('#showallstudentinfo').click(function () {
        $('#layoutcenter>div').hide();  //将所有的table隐藏
        $('#tableeasyuishowinfo').show();  //显示对应栏目的table
        $('#tableeasyuishowinfo').tabs('select', 0);
    })
    //显示老师信息
    $('#showallteacherinfo').click(function () {
        $('#layoutcenter>div').hide();  //将所有的table隐藏
        $('#tableeasyuishowinfo').show();  //显示对应栏目的table
        $('#tableeasyuishowinfo').tabs('select', 1);
    })
    //显示助教信息
    $('#showallassistantinfo').click(function () {
        $('#layoutcenter>div').hide();  //将所有的table隐藏
        $('#tableeasyuishowinfo').show();  //显示对应栏目的table
        $('#tableeasyuishowinfo').tabs('select', 2);
    })

    //单个添加学生，老师，助教信息模块功能
    $('#addstudentinfosigle').click(function () {
        $('#layoutcenter>div').hide();  //将所有的table隐藏
        $('#tableeasyuiaddsigle').show();  //显示对应栏目的table
        $('#tableeasyuiaddsigle').tabs('select', 0);
    })
    //单个验证老师添加信息
    $('#addteacherinfosigle').click(function () {
        $('#layoutcenter>div').hide();
        $('#tableeasyuiaddsigle').show();
        $('#tableeasyuiaddsigle').tabs('select', 1);
    })
    //单个验证助教添加
    $('#addassistantsigle').click(function () {
        $('#layoutcenter>div').hide();
        $('#tableeasyuiaddsigle').show();
        $('#tableeasyuiaddsigle').tabs('select', 2);
    })


    //批量添加学生，老师，助教信息
    $('#addstudentinfomore').click(function () {
        $('#layoutcenter>div').hide();
        $('#tableeasyuiaddmore').show();
        $('#tableeasyuiaddmore').tabs('select', 0);
    })
    //批量验证老师信息
    $('#addsteacherinfomore').click(function () {
        $('#layoutcenter>div').hide();
        $('#tableeasyuiaddmore').show();
        $('#tableeasyuiaddmore').tabs('select', 1);
    })
    //批量验证助教信息
    $('#addassistantmore').click(function () {
        $('#layoutcenter>div').hide();
        $('#tableeasyuiaddmore').show();
        $('#tableeasyuiaddmore').tabs('select', 2);
    })

    //单个添加信息表单的验证功能
    //学生验证
    $('#regesterstu').click(function () {
        var number = $('#usernumber').val().trim();
        var name = $('#username').val().trim();
        var stuclass = $('#userclass').val().trim();
        if(name ==""||number == ""|| stuclass==""){
            alert("所填信息不能为空!")
            return;
        }
        $('#siglestudentregester').attr({'action':'/systemmanagement/regesterinfo/'});
        $('#siglestudentregester').submit();
    })
    //助教验证
    $('#regesterass').click(function () {
        var number = $('#usernumber2').val().trim();
        var name = $('#username2').val().trim();
        var stuclass = $('#userclass2').val().trim();
        if(name ==""||number == ""|| stuclass==""){
            alert("所填信息不能为空!")
            return;
        }
        $('#sigleassistantregester').attr({'action':'/systemmanagement/regesterinfo/'});
        $('#sigleassistantregester').submit();
    })
    //老师验证
    $('#regestertea').click(function () {
        var number = $('#usernumber3').val().trim();
        var name = $('#username3').val().trim();
        var stuclass = $('#userclass3').val().trim();
        if(name ==""||number == ""|| stuclass==""){
            alert("所填信息不能为空!")
            return;
        }
        $('#sigleteacherregester').attr({'action':'/systemmanagement/regesterinfo/'});
        $('#sigleteacherregester').submit();
    })


    //学生的分页处理
    $('#studentinfopage').pagination({
        //选择新页面的时候触发pageNumber:选择的页面，pageSize:选择的每页的大小
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $.ajax({
                url:'/systemmanagement/changepagenumber/',
                async:false, //这里不要异步，因为异步的话，如果数据多了，就导致数据显示过慢
                data:{"pagesize":pageSize ,"pagenumber":pageNumber,"type":'1'}, //页面大小,页面索引数和请求的数据类型
                type:"GET",
                success:function (data) {  //请求成功
                    //1:先将之前的表格中的数据进行清除
                    $('#studentbodyinfo').empty();
                    //2:循环拼接需要的类型数据
                    $.each(data.data,function (i , m) {
                        var $std = $('<tr><td>'+m.uid +'</td><td>'+m.name+'</td><td>'+m.student_class_name+'</td>' +
                            '<td>'+m.permission+'</td><td>'+m.contact+'</td><td><a href="#" id="updatatableinfostu" class="'+ m.uid +'">更新</a></td><td><a href="#" id="deletetableinfostu" class="'+m.uid+'">删除</a></td></tr>');
                        //3.将表格内容添加到对应的table中
                        $std.appendTo('#studentbodyinfo');
                    })
                    $('.userinfoshowtab tr').mouseenter(function () {
                        $(this).css('background-color', '#ffff66');
                    })
                    $('.userinfoshowtab tr').mouseout(function () {
                        $(this).css('background-color', '#d4e3e5');
                    })
                },
                errors:function () {
                    alert("信息加载失败，请稍后再试哦！")
                },
                dataType:"json"
            });
            $(this).pagination('loaded');
	    },
        //更改页面大小的时候触发的函数:pageSize:更改的页面大小
	    onChangePageSize:function (pageSize) {
            $.ajax({
                url:'/systemmanagement/changepagesize/',
                async:false, //这里不要异步，因为异步的话，如果数据多了，就导致数据显示过慢
                data:{"pagesize":pageSize ,"type":'1'}, //页面大小和请求的数据类型
                type:"GET",
                success:function (data) {  //请求成功
                    //1:先将之前的表格中的数据进行清除
                    $('#studentbodyinfo').empty();
                    //2:循环拼接需要的类型数据
                    $.each(data.data,function (i , m) {
                        var $std = $('<tr><td>'+m.uid +'</td><td>'+m.name+'</td><td>'+m.student_class_name+'</td>' +
                            '<td>'+m.permission+'</td><td>'+m.contact+'</td><td><a href="#" id="updatatableinfostu" class="'+ m.uid +'">更新</a></td><td><a href="#" id="updatatableinfostu" class="'+m.uid+'">删除</a></td></tr>');
                        //3.将表格内容添加到对应的table中
                        $std.appendTo('#studentbodyinfo');
                    })
                    $('.userinfoshowtab tr').mouseenter(function () {
                        $(this).css('background-color', '#ffff66');
                    })
                    $('.userinfoshowtab tr').mouseout(function () {
                        $(this).css('background-color', '#d4e3e5');
                    })
                },
                errors:function () {
                    alert("信息加载失败，请稍后再试哦！")
                },
                dataType:"json"
            });
        }
    })
    //老师的分页处理
    $('#teacherinfopage').pagination({
        //选择新页面的时候触发pageNumber:选择的页面，pageSize:选择的每页的大小
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $.ajax({
                url:'/systemmanagement/changepagenumber/',
                async:false, //这里不要异步，因为异步的话，如果数据多了，就导致数据显示过慢
                data:{"pagesize":pageSize ,"pagenumber":pageNumber,"type":'3'}, //页面大小,页面索引数和请求的数据类型
                type:"GET",
                success:function (data) {  //请求成功
                    //1:先将之前的表格中的数据进行清除
                    $('#teacherbodyinfo').empty();
                    //2:循环拼接需要的类型数据
                    $.each(data.data,function (i , m) {
                        var $std = $('<tr><td>'+m.uid +'</td><td>'+m.name+'</td><td>'+m.teacher_class_name+'</td>' +
                            '<td>'+m.permission+'</td><td>'+m.contact+'</td><td><a href="#" id="updatatableinfotea" class="'+ m.uid +'">更新</a></td><td><a href="#" id="deletetableinfotea" class="'+m.uid+'">删除</a></td></tr>');
                        //3.将表格内容添加到对应的table中
                        $std.appendTo('#teacherbodyinfo');
                    })
                    $('.userinfoshowtab tr').mouseenter(function () {
                        $(this).css('background-color', '#ffff66');
                    })
                    $('.userinfoshowtab tr').mouseout(function () {
                        $(this).css('background-color', '#d4e3e5');
                    })
                },
                errors:function () {
                    alert("信息加载失败，请稍后再试哦！")
                },
                dataType:"json"
            });
            $(this).pagination('loaded');
	    },
        //更改页面大小的时候触发的函数:pageSize:更改的页面大小
	    onChangePageSize:function (pageSize) {
            $.ajax({
                url:'/systemmanagement/changepagesize/',
                async:false, //这里不要异步，因为异步的话，如果数据多了，就导致数据显示过慢
                data:{"pagesize":pageSize ,"type":'1'}, //页面大小和请求的数据类型
                type:"GET",
                success:function (data) {  //请求成功
                    //1:先将之前的表格中的数据进行清除
                    $('#teacherbodyinfo').empty();
                    //2:循环拼接需要的类型数据
                    $.each(data.data,function (i , m) {
                        var $std = $('<tr><td>'+m.uid +'</td><td>'+m.name+'</td><td>'+m.teacher_class_name+'</td>' +
                            '<td>'+m.permission+'</td><td>'+m.contact+'</td><td><a href="#" id="updatatableinfotea" class="'+ m.uid +'">更新</a></td><td><a href="#" id="updatatableinfotea" class="'+m.uid+'">删除</a></td></tr>');
                        //3.将表格内容添加到对应的table中
                        $std.appendTo('#teacherbodyinfo');
                    })
                    //重新绑定鼠标移动到table的显示css，因为上面清空了之前的，那么就没有了
                    $('.userinfoshowtab tr').mouseenter(function () {
                        $(this).css('background-color', '#ffff66');
                    })
                    $('.userinfoshowtab tr').mouseout(function () {
                        $(this).css('background-color', '#d4e3e5');
                    })
                },
                errors:function () {
                    alert("信息加载失败，请稍后再试哦！")
                },
                dataType:"json"
            });
        }
    })
    //助教的分页处理
    $('#assistantinfopage').pagination({
        //选择新页面的时候触发pageNumber:选择的页面，pageSize:选择的每页的大小
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $.ajax({
                url:'/systemmanagement/changepagenumber/',
                async:false, //这里不要异步，因为异步的话，如果数据多了，就导致数据显示过慢
                data:{"pagesize":pageSize ,"pagenumber":pageNumber,"type":'2'}, //页面大小,页面索引数和请求的数据类型
                type:"GET",
                success:function (data) {  //请求成功
                    //1:先将之前的表格中的数据进行清除
                    $('#assistantbodyinfo').empty();
                    //2:循环拼接需要的类型数据
                    $.each(data.data,function (i , m) {
                        var $std = $('<tr><td>'+m.uid +'</td><td>'+m.name+'</td><td>'+m.student_class_name+'</td>' +
                            '<td>'+m.permission+'</td><td>'+m.contact+'</td><td><a href="#" id="updatatableinfoass" class="'+ m.uid +'">更新</a></td><td><a href="#" id="deletetableinfoass" class="'+m.uid+'">删除</a></td></tr>');
                        //3.将表格内容添加到对应的table中
                        $std.appendTo('#assistantbodyinfo');
                    })
                    $('.userinfoshowtab tr').mouseenter(function () {
                        $(this).css('background-color', '#ffff66');
                    })
                    $('.userinfoshowtab tr').mouseout(function () {
                        $(this).css('background-color', '#d4e3e5');
                    })
                },
                errors:function () {
                    alert("信息加载失败，请稍后再试哦！")
                },
                dataType:"json"
            });
            $(this).pagination('loaded');
	    },
        //更改页面大小的时候触发的函数:pageSize:更改的页面大小
	    onChangePageSize:function (pageSize) {
            $.ajax({
                url:'/systemmanagement/changepagesize/',
                async:false, //这里不要异步，因为异步的话，如果数据多了，就导致数据显示过慢
                data:{"pagesize":pageSize ,"type":'1'}, //页面大小和请求的数据类型
                type:"GET",
                success:function (data) {  //请求成功
                    //1:先将之前的表格中的数据进行清除
                    $('#teacherbodyinfo').empty();
                    //2:循环拼接需要的类型数据
                    $.each(data.data,function (i , m) {
                        var $std = $('<tr><td>'+m.uid +'</td><td>'+m.name+'</td><td>'+m.student_class_name+'</td>' +
                            '<td>'+m.permission+'</td><td>'+m.contact+'</td><td><a href="#" id="updatatableinfoass" class="'+ m.uid +'">更新</a></td><td><a href="#" id="updatatableinfoass" class="'+m.uid+'">删除</a></td></tr>');
                        //3.将表格内容添加到对应的table中
                        $std.appendTo('#teacherbodyinfo');
                    })
                    $('.userinfoshowtab tr').mouseenter(function () {
                        $(this).css('background-color', '#ffff66');
                    })
                    $('.userinfoshowtab tr').mouseout(function () {
                        $(this).css('background-color', '#d4e3e5');
                    })
                },
                errors:function () {
                    alert("信息加载失败，请稍后再试哦！")
                },
                dataType:"json"
            });
        }
    })


    //点击学生table中的更新链接，进行更新操作
    var clickIndex = 0;  //记录点击更新的索引，方便进行更新
    $(document).on('click','#updatatableinfostu',function () {
        //获取点击的更新的索引，方便后面进行数据的及时更新
        clickIndex = $('#studentinfoshowtab tr').index($(this).parent().parent());
        if($('#opuptatadiv').css("display") == "block"){  //如果已经显示，则先关闭
            $('#opuptatadiv').css({'display':"none"});
        }
        else if($('#opuptatadiv').css("display") == "none"){  //如果是隐藏，则进行显示
            //获取点击超链接的每一列的内容
            var para1 = $(this).parent().siblings().eq(0).text();
            var para2 = $(this).parent().siblings().eq(1).text();
            var para3 = $(this).parent().siblings().eq(2).text();
            var para4 = $(this).parent().siblings().eq(3).text();
            var para5 = $(this).parent().siblings().eq(4).text();
            //将内容填充到对应的更新列中
            $('#opupdatauseruid').val(para1);
            $('#opupdatausername').val(para2);
            $('#opupdatauserclass').val(para3);
            $('#opupdatauserper').val(para4);
            $('#opupdatausertel').val(para5);
            $('#opuptatadiv').css({'display':"block"});
        }
        // //1：获取点击更新条目的信息(用于子窗口进行反馈)
        // var obj = new  Object();
        // obj.para1 =  $(this).parent().siblings().eq(0).text();
        // obj.para2 =  $(this).parent().siblings().eq(1).text();
        // obj.para3 =  $(this).parent().siblings().eq(2).text();
        // obj.para4 =  $(this).parent().siblings().eq(3).text();
        // obj.para5 =  $(this).parent().siblings().eq(4).text();
        // //2：传送参数进行显示更新
        // window.showModalDialog("/systemmanagement/updatauserinfohml",obj);
    })
    //点击老师table中的更新链接，进行更新操作
     $(document).on('click','#updatatableinfotea',function () {
        //获取点击的更新的索引，方便后面进行数据的及时更新
        clickIndex = $('#teacherinfoshowtab tr').index($(this).parent().parent());
        if($('#opuptatadiv1').css("display") == "block"){  //如果已经显示，则先关闭
            $('#opuptatadiv1').css({'display':"none"});
        }
        else if($('#opuptatadiv1').css("display") == "none"){  //如果是隐藏，则进行显示
            //获取点击超链接的每一列的内容
            var para1 = $(this).parent().siblings().eq(0).text();
            var para2 = $(this).parent().siblings().eq(1).text();
            var para3 = $(this).parent().siblings().eq(2).text();
            var para4 = $(this).parent().siblings().eq(3).text();
            var para5 = $(this).parent().siblings().eq(4).text();
            //将内容填充到对应的更新列中
            $('#opupdatauseruid1').val(para1);
            $('#opupdatausername1').val(para2);
            $('#opupdatauserclass1').val(para3);
            $('#opupdatauserper1').val(para4);
            $('#opupdatausertel1').val(para5);
            $('#opuptatadiv1').css({'display':"block"});
        }
    })
    //点击助教table中的更新链接，进行更新操作
     $(document).on('click','#updatatableinfoass',function () {
       //获取点击的更新的索引，方便后面进行数据的及时更新
        clickIndex = $('#assistantinfoshowtab tr').index($(this).parent().parent());
        if($('#opuptatadiv2').css("display") == "block"){  //如果已经显示，则先关闭
            $('#opuptatadiv2').css({'display':"none"});
        }
        else if($('#opuptatadiv2').css("display") == "none"){  //如果是隐藏，则进行显示
            //获取点击超链接的每一列的内容
            var para1 = $(this).parent().siblings().eq(0).text();
            var para2 = $(this).parent().siblings().eq(1).text();
            var para3 = $(this).parent().siblings().eq(2).text();
            var para4 = $(this).parent().siblings().eq(3).text();
            var para5 = $(this).parent().siblings().eq(4).text();
            //将内容填充到对应的更新列中
            $('#opupdatauseruid2').val(para1);
            $('#opupdatausername2').val(para2);
            $('#opupdatauserclass2').val(para3);
            $('#opupdatauserper2').val(para4);
            $('#opupdatausertel2').val(para5);
            $('#opuptatadiv2').css({'display':"block"});
        }
    })

    //点击学生table中的删除链接，进行删除操作
     $(document).on('click','#deletetableinfostu',function () {
          if(confirm("确定要删除该学生信息吗？")){  //确定删除
               //1：获取需要进行删除学生的uid(通过之前的超链接的class属性可以获取)
                var studentUid = $(this).attr('class');
                var opresult = deleteuserjs(studentUid);
                 if (opresult == true) {  //如果删除操作成功，则进行后面的内容
                //将对应点击的超链接的单元格的内容进行隐藏消失使其无法进行点击操作，就标识删除，等刷新时，则会进行删除内容
                $(this).parent().parent().css({'display': "none"});
                }
          }
    })
    //点击老师table中的删除链接，进行删除操作
     $(document).on('click','#deletetableinfotea',function () {
        if(confirm("确定要删除该老师信息吗？")){  //确定删除
               //1：获取需要进行删除学生的uid(通过之前的超链接的class属性可以获取)
                var studentUid = $(this).attr('class');
                var opresult = deleteuserjs(studentUid);
                 if (opresult == true) {  //如果删除操作成功，则进行后面的内容
                //将对应点击的超链接的单元格的内容进行隐藏消失使其无法进行点击操作，就标识删除，等刷新时，则会进行删除内容
                $(this).parent().parent().css({'display': "none"});
                }
          }
    })
    //点击助教table中的删除链接，进行删除操作
     $(document).on('click','#deletetableinfoass',function () {
        if(confirm("确定要删除该助教信息吗？")){  //确定删除
               //1：获取需要进行删除学生的uid(通过之前的超链接的class属性可以获取)
                var studentUid = $(this).attr('class');
                var opresult = deleteuserjs(studentUid);
                 if (opresult == true) {  //如果删除操作成功，则进行后面的内容
                //将对应点击的超链接的单元格的内容进行隐藏消失使其无法进行点击操作，就标识删除，等刷新时，则会进行删除内容
                $(this).parent().parent().css({'display': "none"});
                }
          }
    })

    //学生用户进行点击更新按钮的操作
    $('#opupdatabutton').click(function () {
        //判断填写的内容是否符合规则(先简单的判断不能为空)
       if( $('#opupdatauseruid').val() == ""|| $('#opupdatausername').val() == ""|| $('#opupdatauserclass').val()== ""|| $('#opupdatauserper').val()== ""||$('#opupdatausertel').val()== ""){
            alert("所填写的信息不能为空");
            return ;
       }
       else if($('#opupdatauseruid').val() == null|| $('#opupdatausername').val() == null|| $('#opupdatauserclass').val()== null|| $('#opupdatauserper').val()== null||$('#opupdatausertel').val()== null){
           alert("所填写的信息不能为空");
            return ;
       }
       else {  //ajax提交信息到后台
           var opresult = false ; //标识更新的结果，false为失败，true为成功
            $.ajax({
                url: '/systemmanagement/updatauserinfoop/',
                type: "GET",
                async: false,
                data: {'uid':$('#opupdatauseruid').val(),
                        'name':$('#opupdatausername').val(),
                        'cla':$('#opupdatauserclass').val(),
                        'tel':$('#opupdatausertel').val(),
                        'per':$('#opupdatauserper').val()},                   //传送参数
                success: function (data) {
                    //判断请求的结果
                    if (data.result == 'success') {   //成功
                        opresult = true;
                        alert("更新成功！！")
                    }
                    else {
                        alert("更新失败！！")
                    }
                },
                error: function () {
                    alert("网络不稳，请稍后再试！!")
                },
                dataType: "json"
            });
            if(opresult == true){  //标识更新成功，则将对应的内容进行修改为更新后的内容
                $('#opuptatadiv').css({'display':"none"});
                //找到对应要修改的内容（自己这个还用得比较巧，但是肯定还有其他方法，而且下面的可以优化）
                $('#studentinfoshowtab tr').eq(clickIndex).children().eq(0).text($('#opupdatauseruid').val());
                $('#studentinfoshowtab tr').eq(clickIndex).children().eq(1).text($('#opupdatausername').val());
                $('#studentinfoshowtab tr').eq(clickIndex).children().eq(2).text($('#opupdatauserclass').val());
                $('#studentinfoshowtab tr').eq(clickIndex).children().eq(3).text($('#opupdatauserper').val());
                $('#studentinfoshowtab tr').eq(clickIndex).children().eq(4).text($('#opupdatausertel').val());
            }
       }
    });
    //老师用户进行点击更新按钮的操作
    $('#opupdatabutton1').click(function () {
        //判断填写的内容是否符合规则(先简单的判断不能为空)
       if( $('#opupdatauseruid1').val() == ""|| $('#opupdatausername1').val() == ""|| $('#opupdatauserclass1').val()== ""|| $('#opupdatauserper1').val()== ""||$('#opupdatausertel1').val()== ""){
            alert("所填写的信息不能为空");
            return ;
       }
       else if($('#opupdatauseruid1').val() == null|| $('#opupdatausername1').val() == null|| $('#opupdatauserclass1').val()== null|| $('#opupdatauserper1').val()== null||$('#opupdatausertel1').val()== null){
           alert("所填写的信息不能为空");
            return ;
       }
       else {  //ajax提交信息到后台
           var opresult = false ; //标识更新的结果，false为失败，true为成功
            $.ajax({
                url: '/systemmanagement/updatauserinfoop/',
                type: "GET",
                async: false,
                data: {'uid':$('#opupdatauseruid1').val(),
                        'name':$('#opupdatausername1').val(),
                        'cla':$('#opupdatauserclass1').val(),
                        'tel':$('#opupdatausertel1').val(),
                        'per':$('#opupdatauserper1').val()},                   //传送参数
                success: function (data) {
                    //判断请求的结果
                    if (data.result == 'success') {   //成功
                        opresult = true;
                        alert("更新成功！！")
                    }
                    else {
                        alert("更新失败！！")
                    }
                },
                error: function () {
                    alert("网络不稳，请稍后再试！!")
                },
                dataType: "json"
            });
            if(opresult == true){  //标识更新成功，则将对应的内容进行修改为更新后的内容
                $('#opuptatadiv1').css({'display':"none"});
                //找到对应要修改的内容（自己这个还用得比较巧，但是肯定还有其他方法，而且下面的可以优化）
                $('#teacherinfoshowtab tr').eq(clickIndex).children().eq(0).text($('#opupdatauseruid1').val());
                $('#teacherinfoshowtab tr').eq(clickIndex).children().eq(1).text($('#opupdatausername1').val());
                $('#teacherinfoshowtab tr').eq(clickIndex).children().eq(2).text($('#opupdatauserclass1').val());
                $('#teacherinfoshowtab tr').eq(clickIndex).children().eq(3).text($('#opupdatauserper1').val());
                $('#teacherinfoshowtab tr').eq(clickIndex).children().eq(4).text($('#opupdatausertel1').val());
            }
       }
    });
    //助教用户进行点击更新按钮的操作
    $('#opupdatabutton2').click(function () {
        //判断填写的内容是否符合规则(先简单的判断不能为空)
       if( $('#opupdatauseruid2').val() == ""|| $('#opupdatausername2').val() == ""|| $('#opupdatauserclass2').val()== ""|| $('#opupdatauserper2').val()== ""||$('#opupdatausertel2').val()== ""){
            alert("所填写的信息不能为空");
            return ;
       }
       else if($('#opupdatauseruid2').val() == null|| $('#opupdatausername2').val() == null|| $('#opupdatauserclass2').val()== null|| $('#opupdatauserper2').val()== null||$('#opupdatausertel2').val()== null){
           alert("所填写的信息不能为空");
            return ;
       }
       else {  //ajax提交信息到后台
           var opresult = false ; //标识更新的结果，false为失败，true为成功
            $.ajax({
                url: '/systemmanagement/updatauserinfoop/',
                type: "GET",
                async: false,
                data: {'uid':$('#opupdatauseruid2').val(),
                        'name':$('#opupdatausername2').val(),
                        'cla':$('#opupdatauserclass2').val(),
                        'tel':$('#opupdatausertel2').val(),
                        'per':$('#opupdatauserper2').val()},                   //传送参数
                success: function (data) {
                    //判断请求的结果
                    if (data.result == 'success') {   //成功
                        opresult = true;
                        alert("更新成功！！")
                    }
                    else {
                        alert("更新失败！！")
                    }
                },
                error: function () {
                    alert("网络不稳，请稍后再试！!")
                },
                dataType: "json"
            });
            if(opresult == true){  //标识更新成功，则将对应的内容进行修改为更新后的内容
                $('#opuptatadiv2').css({'display':"none"});
                //找到对应要修改的内容（自己这个还用得比较巧，但是肯定还有其他方法，而且下面的可以优化）
                $('#assistantinfoshowtab tr').eq(clickIndex).children().eq(0).text($('#opupdatauseruid2').val());
                $('#assistantinfoshowtab tr').eq(clickIndex).children().eq(1).text($('#opupdatausername2').val());
                $('#assistantinfoshowtab tr').eq(clickIndex).children().eq(2).text($('#opupdatauserclass2').val());
                $('#assistantinfoshowtab tr').eq(clickIndex).children().eq(3).text($('#opupdatauserper2').val());
                $('#assistantinfoshowtab tr').eq(clickIndex).children().eq(4).text($('#opupdatausertel2').val());
            }
       }
    });


     //进行删除操作的方法：参数para是点击删除的uid内容
    function deleteuserjs(para) {
        var opresult = false;
        //2：发出ajax进行后台删除操作
        $.ajax({
            url: '/systemmanagement/deleteuserinfoop/',
            async: false,  //这里要变为同步，一定要，因为如果用异步请求，这会导致数据还没删除页面已经加载完了
            data: {'op': para},
            type: 'GET',
            success: function (data) {
                //判断请求的结果
                if (data.result == 'success') {   //成功
                    opresult = true;
                    alert("删除成功！！")
                }
                else {
                    alert("删除失败！！")
                }
            },
            error: function () {
                alert("网络不稳，请稍后再试！!")
            },
            dataType: "json"
        });
        return opresult;   //返回删除结果
    }
})