/**
 * Created by Wyd on 2017/7/27.
 */
//判断上传是否为空
function filepost() {
    var filevalue = document.getElementById('fileField')
    if (filevalue.value==''){
        alert('请先选择要上传的文件！')
        return false
    }
}



//点击撤回按钮
function withdraw(info) {
    var name_item = info.split('@')[0]
    var grade = info.split('@')[1]
    if (grade != '还没批改哦!') {
        alert("成绩都出来了，还想修改，傻了吧！")
    }
    //如果点击确定按钮
    else {
        if (confirm("确定要撤回吗？")) {
            window.location = '/WorkManage/HomeWorkWithdraw/?info=' + name_item
        }
    }
}