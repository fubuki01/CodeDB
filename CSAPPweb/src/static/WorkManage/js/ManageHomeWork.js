/**
 * Created by Wyd on 2017/7/24.
 */

function select_class_and_homework() {

    //根据ID获得对象中选中的内容
    var class_name_value = document.getElementById('class_name').value
    var homework_name_value = document.getElementById('homework_name').value
    if (class_name_value == '---请选择班级---' || homework_name_value == '---请选择作业---') {
        alert("不能选择空值！")
        return false
    }
    window.location = '/WorkManage/SelectClassAndHomeWork/?cname=' + class_name_value + '&hname=' + homework_name_value
}

//已交作业全选和全不选事件
function posted_zhuan(flag) {
    var select_state_postdes = document.getElementsByName("select_state_postde");
    for (var i = 0; i < select_state_postdes.length; i++) {
        var select = select_state_postdes[i];
        select.checked = flag;
    }

}

//迟交作业的全选和全不选
function delayedzhuang(flag) {
    var select_state_delayeds = document.getElementsByName("select_state_delayed");
    for (var i = 0; i < select_state_delayeds.length; i++) {
        var select = select_state_delayeds[i];
        select.checked = flag;
    }
}


//点击下载按钮 下载已提交的作业
function down_student_homework_posted(wname) {
    //创建数组用于存放选中的id
    var select_ids = new Array();
    var j = 0;
    //判断是否是空选
    var select_state_postdes = document.getElementsByName("select_state_postde");
    for (var i = 0; i < select_state_postdes.length; i++) {
        var select = select_state_postdes[i];
        if (select.checked == true && select.id != 'select_state_postde') {
            select_ids[j] = select.id
            j++;
        }
    }
    if (select_ids.length == 0) {
        alert("请先选择要下载的作业");
    } else {
        window.location = '/WorkManage/DownStudentsHomeWork/?info=' + select_ids + '&name=' + wname;
    }
}

//点击下载按钮 下载迟交的作业
function down_student_homework_delayed(wname) {
    //创建数组用于存放选中的id
    var select_ids = new Array();
    var j = 0;
    //判断是否是空选
    var select_state_delayeds = document.getElementsByName("select_state_delayed");
    for (var i = 0; i < select_state_delayeds.length; i++) {
        var select = select_state_delayeds[i];
        if (select.checked == true && select.id != 'select_state_delayed') {
            select_ids[j] = select.id
            j++;
        }
    }
    if (select_ids.length == 0) {
        alert("请先选择要下载的作业");
    } else {
        window.location = '/WorkManage/DownStudentsHomeWork/?info=' + select_ids + '&name=' + wname;
    }
}

//退回
//退回 已经上传的
function withdraw_posted(infoname) {
    var wname = infoname.split('@')[0]
    var cname = infoname.split('@')[1]
    //创建数组用于存放选中的id
    var select_ids = new Array();
    var j = 0;
    //判断是否是空选
    var select_state_postdes = document.getElementsByName("select_state_postde");
    for (var i = 0; i < select_state_postdes.length; i++) {
        var select = select_state_postdes[i];
        if (select.checked == true && select.id != 'select_state_postde') {
            select_ids[j] = select.id
            j++;
        }
    }
    if (select_ids.length == 0) {
        alert("请先选择要退回的的作业");
    } else {
        window.location = '/WorkManage/WithDrawStudentsHomeWork/?info=' + select_ids + '&name=' + wname + '&cname=' + cname;
    }
}


//退回 补交的
function withdraw_delayed(infoname) {
    var wname = infoname.split('@')[0]
    var cname = infoname.split('@')[1]
    //创建数组用于存放选中的id
    var select_ids = new Array();
    var j = 0;
    //判断是否是空选
    var select_state_delayeds = document.getElementsByName("select_state_delayed");
    for (var i = 0; i < select_state_delayeds.length; i++) {
        var select = select_state_delayeds[i];
        if (select.checked == true && select.id != 'select_state_delayed') {
            select_ids[j] = select.id
            j++;
        }
    }
    if (select_ids.length == 0) {
        alert("请先选择要退回的作业");
    } else {
        window.location = '/WorkManage/WithDrawStudentsHomeWork/?info=' + select_ids + '&name=' + wname + '&cname=' + cname;
        ;
    }
}


//批改作业
//批改正常提交的
function writscore_posted() {
    var display_score_posteds = document.getElementsByName('display_score_posted')
    var writ_score_posteds = document.getElementsByName('writ_score_posted')
    var posted_scores = document.getElementsByName('posted_score')
    for (var i = 0; i < display_score_posteds.length; i++) {
        var display_score_posted = display_score_posteds[i];
        display_score_posted.style.display = 'none'
    }
    for (var j = 0; j < writ_score_posteds.length; j++) {
        var writ_score_posted = writ_score_posteds[j];
        writ_score_posted.style.display = 'inline'
    }
    for (var k = 0; k < posted_scores.length; k++) {
        var posted_score = posted_scores[k];
        if (posted_score.value == "还没批改哦!") {
            posted_score.setAttribute('value', '')
        }
    }
     if (writ_score_posteds.length==0){
        alert('无作业可以批改！')
    }
}

//批改迟交的
function writescore_delayed() {
    var display_score_delays = document.getElementsByName('display_score_delay')
    var writ_score_delays = document.getElementsByName('writ_score_delay')
    var delay_scores = document.getElementsByName('delay_score')
    for (var i = 0; i < display_score_delays.length; i++) {
        var display_score_delay = display_score_delays[i];
        display_score_delay.style.display = 'none'

    }
    for (var j = 0; j < writ_score_delays.length; j++) {
        var writ_score_delay = writ_score_delays[j];
        writ_score_delay.style.display = 'inline'
    }
    for (var k = 0; k < delay_scores.length; k++) {
        var delay_score = delay_scores[k];
        if (delay_score.value == "还没批改哦!") {
            delay_score.setAttribute('value', '')
        }
    }
    if (writ_score_delays.length==0){
        alert('无作业可以批改！')
    }
}


//发布成绩
//发布正常提交作业的成绩
function release_posted_score(info) {
    hname = info.split('@')[0]
    cname = info.split('@')[1]
    var flag = 0
    var posted_number_notes = new Array();//用于存放学号
    var posted_score_notes = new Array();//用于存放对应成绩
    var posted_scores = document.getElementsByName('posted_score')
    if (posted_scores.length != 0) {
        for (var i = 0; i < posted_scores.length; i++) {
            var posted_score = posted_scores[i];
            if (posted_score.value == '还没批改哦!') {
                alert('请先批改作业！')
                break
            } else {
                posted_number_notes[i] = posted_score.id;
                if (posted_score.value == '') {
                    posted_score_notes[i] = '还没批改哦!'
                } else {
                    posted_score_notes[i] = posted_score.value
                }
                flag = 1
            }

        }
        if (flag == 1) {
            window.location = encodeURI('/WorkManage/WritHomeWorkScore/?number=' + posted_number_notes + '&score=' + posted_score_notes + '&hname=' + hname + '&cname=' + cname)
            alert('发布成功！')
        }
    } else {
        alert('没有成绩可以发布！')
    }

}

//发布迟交作业的成绩
function release_delayed_score(info) {
    hname = info.split('@')[0]
    cname = info.split('@')[1]
    var falg = 0;
    var delay_number_notes = new Array();//用于存放学号
    var delay_score_notes = new Array();//用于存放对应成绩
    var delay_scores = document.getElementsByName('delay_score')
    var writ_score_delays = document.getElementsByName('writ_score_delay')
    if (delay_scores.length != 0) {
        for (var i = 0; i < delay_scores.length; i++) {
            var delay_score = delay_scores[i];
            if (delay_score.value=='还没批改哦!') {
                alert('请先批改作业！')
                break
            } else {
                delay_number_notes[i] = delay_score.id;
                if (delay_score.value == '') {
                    delay_score_notes[i] = '还没批改哦!'
                } else {
                    delay_score_notes[i] = delay_score.value
                }
                falg = 1
            }

        }
        if (falg == 1) {
            window.location = '/WorkManage/WritHomeWorkScore/?number=' + delay_number_notes + '&score=' + delay_score_notes + '&hname=' + hname + '&cname=' + cname;
            alert('发布成功！')
        }
    } else {
        alert('没有成绩可以发布！')
    }

}


//催交
function urge_click(info) {
    var workname = info.split('@')[0];//作业名称
    var student_number = info.split('@')[1];//对应的学号
    if(confirm("确定要催交吗？")){
        window.location='/WorkManage/HomeWorkUrge/?name='+workname+'&number='+student_number;
    }else {
        return false;
    }

}


// //自定义字典对象
// function Dictionary() {
//     this.data = new Array();
//
//     this.put = function (key, value) {
//         this.data[key] = value;
//     };
//
//     this.get = function (key) {
//         return this.data[key];
//     };
//
//     this.remove = function (key) {
//         this.data[key] = null;
//     };
//
//     this.isEmpty = function () {
//         return this.data.length == 0;
//     };
//
//     this.size = function () {
//         return this.data.length;
//     };
// }


