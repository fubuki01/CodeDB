/**
 * Created by Wyd on 2017/7/24.
 */

function submitclick() {
    var homework_name = document.getElementById('homework_name');
    var homework_publish_start_time = document.getElementById('homework_publish_start_time')
    var homework_publish_end_time = document.getElementById('homework_publish_end_time');
    //获取当前时间
    var d = new Date();
    var month = d.getMonth()+1
    if(month<10){
        month = '0'+(d.getMonth()+1).toString()
    }
    var day = d.getDate()
    if (day<10){
        day = '0'+d.getDate().toString()
    }
    var str = d.getFullYear()+'-'+month+'-'+day+'T'+d.getHours()+':'+d.getMinutes();

    if ((homework_name.value == "")||(homework_publish_start_time.value == "")
        ||(homework_publish_end_time.value == "")){
        alert("请将信息填写完整！");
        return false;
    }else if (str>homework_publish_end_time.value.toString()){
        alert("结束时间不能小于当前时间哦！")
        return false
    }else if (homework_publish_start_time.value.toString()>homework_publish_end_time.value.toString()){
        alert("开始时间不能晚于结束时间")
        return false
    }
}

//删除发布的作业
function delect_homework(delete_homework_info){
    if (confirm("确定要删除吗？")){
        operation_type = 'delete'
        window.location='/WorkManage/HomeWorkDelect/?info='+delete_homework_info+'&type='+operation_type;
        return true
    }else {
        return false
    }
}

//修改发布的作业
function modify_homework(modify_homework_info) {
    if (confirm("确定要修改吗？")){
        operation_type = 'modify'
        window.location='/WorkManage/HomeWorkDelect/?info='+modify_homework_info+'&type='+operation_type;
        return true
    }else{
        return false
    }
}


