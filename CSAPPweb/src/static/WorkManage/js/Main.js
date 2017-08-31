/**
 * Created by Wyd on 2017/8/1.
 */


//判断助教发布作业quanxian
function publish_home_work(info) {
    if(info==2||info==4){
        return true
    }else {
        alert('对不起，权限不够，您无法访问该模块！')
        return false
    }
}

//助教或老师管理作业权限
function manage_home_work(info){
    if(info==2||info==3||info==4){
        return true
    }else {
        alert('对不起，权限不够，您无法访问该模块！')
        return false
    }
}

//学生管理作业权限
function student_manage_home_work(info) {
    if (info==1||info==4){
        return true
    }else {
        alert('对不起，权限不够，您无法访问该模块！')
        return false
    }
}