# coding=UTF-8
import json
import os
import shutil

import time
from django.http import *
from django.shortcuts import render
from django.utils.http import urlquote

from .ZipUtilities import ZipUtilities
from .models import *

BASE_DIR = os.path.dirname ( os.path.dirname ( os.path.abspath ( __file__ ) ) )
MEDIA_ROOT = os.path.join ( BASE_DIR, "static/media/task" )


# 作业管理主页进入 选择对应功能
def Main(request):
    # 获得用户的权限
    permission = request.session.get ( 'permission' )
    content = {'user_permission': permission}
    return render ( request, 'WorkManage/Main.html', content )


# 助教发布作业
def TeacherPublishHomeWork(request):
    # time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")#当前时间
    time = datetime.datetime.now ().strftime ( "%Y-%m-%d" )  # 当前时间
    # content = {'list':homework_class,'nowtime':time}

    # 获取修改发布作业的信息
    homework_info = request.GET.get ( 'upd' )

    if homework_info != None:
        # 根据班级名称和作业名称修改作业

        # 判断是否是对应的 否则进行拦截
        c_n = homework_info.split ( '@' )[0]
        u_p = request.session.get ( 'peimission' )
        u_c = request.session.get ( 'student_class_name' )
        if u_p != 2 and c_n != u_c:
            return HttpResponse ( '抱歉，您不能进行此操作！' )

        homework = HomeWork.objects.filter ( class_name=(homework_info.split ( '@' )[0]),
                                             home_work_name=(homework_info.split ( '@' )[1]) )
        for homeworks in homework:  # 此处可以换成homework[0] 因为是唯一的
            # 获取要修改作业的名字 开始时间 结束时间
            homework_name_old = homeworks.home_work_name
            homework_start_time = homeworks.start_time.replace ( ' ', 'T' )
            homework_end_time = homeworks.end_time.replace ( ' ', 'T' )
            # 标志位 判断是否是修改的还是要新建
            homework_flag = 1
    else:
        # 如果刚进去没有点击修改作业 则默认显示为空time+'T'+'00:00'
        homework_name_old = ''
        homework_start_time = ''
        homework_end_time = ''
        homework_flag = 0
    # 获取对应班级的对应作业 在已发布作业的列表进行显示
    class_name = request.session.get ( 'student_class_name' );
    homework_class = HomeWork.objects.filter ( class_name=class_name )

    # 如果新建的作业存在重复的则提醒用户
    repeat_flag = request.GET.get ( 'flag' )

    if repeat_flag == '1':
        flag = repeat_flag
    else:
        flag = '0'
    # 用户的名字
    user_name = request.session.get ( 'name' )

    content = {'name': user_name, 'list': homework_class, 'homework_name_old': homework_name_old,
               'homework_start_time': homework_start_time,
               'homework_end_time': homework_end_time, 'flag': homework_flag, 'judg_flag': json.dumps ( flag )}
    return render ( request, 'WorkManage/TeacherPublishHomeWork.html', content )


# 助教发布作业后台处理
def TeacherPublishHomeWorkServer(request):
    # 判断是否是修改的
    flag = request.POST['flag']
    flag_repeat = '0'
    class_name = request.session.get ( 'student_class_name' )
    # 判断要修改还是要新发布
    if flag == '1':  # 要修改
        modify_homework = HomeWork.objects.filter ( class_name=class_name,
                                                    home_work_name=request.POST['homework_name'] )
        # if modify_homework:
        # 获取子集中对应的学生
        students = HomeWork_Student_Set.objects.filter ( user_class=class_name,
                                                         home_work_name=request.POST['homework_name'] )
        # 如果是修改的
        for modify_homeworko in modify_homework:
            modify_homeworko.start_time = request.POST['homework_publish_start_time'].replace ( 'T', ' ' )
            modify_homeworko.end_time = request.POST['homework_publish_end_time'].replace ( 'T', ' ' )
            # 修改对应子集中学生作业的日期
            for student in students:
                student.home_work_start_time = modify_homeworko.start_time
                student.home_work_end_time = modify_homeworko.end_time
                # 修改作业提交状态
                if student.post_time == '' and student.post_state == '未交':
                    student.post_state = '未交'
                elif student.post_time == '' and student.post_state == '被助教或老师退回':
                    student.post_state = '被助教或老师退回'
                elif student.post_time =='' and student.post_state == '被催交':
                    student.post_state = '被催交'
                elif student.post_time <= student.home_work_end_time:
                    student.post_state = '已交'
                elif student.post_time > student.home_work_end_time:
                    student.post_state = '迟交'
                student.save ()
            modify_homeworko.save ()
    elif flag == '0':  # 新发布的
        # 判断作业名称是否是重复的
        workname = request.POST['homework_name']
        home_work = HomeWork.objects.filter ( class_name=class_name, home_work_name=workname )
        # 如果没有重复的 则新建作业
        if len ( home_work ) == 0:  # 没有重复的 发布
            # 从用户表中获取符合条件的学生用户
            class_users = User.objects.filter ( student_class_name=class_name, permission='1' )
            # 创建作业
            homework = HomeWork ()
            homework.class_name = class_name
            homework.home_work_name = request.POST['homework_name']
            homework.start_time = request.POST['homework_publish_start_time'].replace ( 'T', ' ' )
            homework.end_time = request.POST['homework_publish_end_time'].replace ( 'T', ' ' )

            # 在meadia路径下创建对应文件夹
            os.makedirs ( MEDIA_ROOT + '/' + homework.class_name + '/' + homework.home_work_name )

            # 把符合条件的用户加入到对应的子集中
            for class_user in class_users:
                homework_student_set = HomeWork_Student_Set ()
                homework_student_set.user_name = class_user.name
                homework_student_set.user_number = class_user.uid
                homework_student_set.user_class = homework.class_name
                homework_student_set.home_work_name = homework.home_work_name
                homework_student_set.home_work_start_time = homework.start_time
                homework_student_set.home_work_end_time = homework.end_time
                homework_student_set.is_posted = False
                homework_student_set.save ()
                homework.home_work_list.append ( homework_student_set )
            # 把创建的作业写入数据库
            homework.save ()

        else:  # 存在重复的
            flag_repeat = '1'

    return HttpResponseRedirect ( '/WorkManage/TeacherPublishHomeWork/?flag=' + flag_repeat )


# 助教删除或者修改发布完成的作业
def HomeWorkDelect(request):
    # 获取传过来的操作类型
    homework_info_operation_type = request.GET['type']
    # 获取传过来的参数 对应的班级和作业名称
    homework_info = request.GET['info']
    if homework_info_operation_type == 'delete':
        # 根据班级名称和作业名称删除作业
        homework = HomeWork.objects.filter ( class_name=(homework_info.split ( '@' )[0]),
                                             home_work_name=(homework_info.split ( '@' )[1]) )
        # 同时删除对应作业下的文件夹
        shutil.rmtree ( MEDIA_ROOT + '/' + homework_info.split ( '@' )[0] + '/' + homework_info.split ( '@' )[1] )

        homework.delete ()
        # 根据作业名称属性删除子集中对应的学生
        homework_student_set = HomeWork_Student_Set.objects.filter ( user_class=homework_info.split ( '@' )[0],
                                                                     home_work_name=homework_info.split ( '@' )[1] )
        for homework_student_sets in homework_student_set:
            homework_student_sets.delete ()

        # 刷新页面
        return HttpResponseRedirect ( '/WorkManage/TeacherPublishHomeWork/' )
    if homework_info_operation_type == 'modify':
        # 返回到url界面进行处理

        return HttpResponseRedirect ( '/WorkManage/TeacherPublishHomeWork/?upd=' + homework_info );


# 学生管理作业
def StudentMangeHomeWork(request):
    # 获得学生的uid查找作业
    user_id = request.session.get ( 'uid' )
    # 未提交的作业
    unpost_homeworks = HomeWork_Student_Set.objects.filter ( user_number=user_id, is_posted=False )
    # for unpost_homework in unpost_homeworks:
    #     # 根据子集属性来获取对应班班级和作业名称
    #     classname = unpost_homework.user_class
    #     homework_name = unpost_homework.home_work_name
    #     # 在发布的作业表中查找符合条件的作业
    #     homework_not_post = HomeWork.objects.filter ( class_name=classname, home_work_name=homework_name )
    #     # 因为只有一条所以就不用循环了
    #     unposted_list.append ( homework_not_post[0] )

    # 已提交的作业
    posted_homeworks = HomeWork_Student_Set.objects.filter ( user_number=user_id, is_posted=True )

    # 获取用户的名字
    user_name = request.session.get ( 'name' )
    content = {'name': user_name, 'unpost_homeworks_list': unpost_homeworks, 'posted_homeworks_list': posted_homeworks}

    return render ( request, 'WorkManage/StudentMangeHomeWork.html', content )


# 上传作业管理后台
def UploadHomeWorkServer(request):
    if request.method == "POST":  # 请求方法为POST时，进行处理
        myFile = request.FILES.get ( "fileField", None )  # 获取上传的文件，如果没有文件，则默认为None
        if not myFile:
            return HttpResponse ( "no files for upload!" )
            # return render ( request, 'WorkManage/NoFileWarn.html' )
        # 获得班级名称
        homework_class = request.POST['class_and_workname'].split ( '@' )[0]
        # 获得作业名称
        homework_name = request.POST['class_and_workname'].split ( '@' )[1]
        # 存放倒指定路径下
        destination = open ( os.path.join ( MEDIA_ROOT + '/' + homework_class + '/' + homework_name, myFile.name ),
                             'wb+' )  # 打开特定的文件进行二进制的写操作
        for chunk in myFile.chunks ():  # 分块写入文件
            destination.write ( chunk )
        destination.close ()

        # 获取当前时间
        time = datetime.datetime.now ().strftime ( "%Y-%m-%d %H:%M" )  # 当前时间
        # 获得学生用户对应的学号与姓名
        user_uid = request.session.get ( 'uid' )
        user_name = request.session.get('name')
        change_items = HomeWork_Student_Set.objects.filter ( user_number=user_uid, home_work_name=homework_name )
        # 修改is_posted的状态
        for change_item in change_items:
            change_item.is_posted = True
            change_item.post_time = time
            #如果是被催交的作业
            if change_item.post_state == '被催交':
                message = Messages.objects.filter(message_to=user_uid,message_content=user_name+"同学，你的"+homework_name+"作业被催交，请尽快提交作业，谢谢！")[0]
                message.is_valid = True
                message.save()
            if change_item.home_work_end_time < time:
                change_item.post_state = '迟交'
            else:
                change_item.post_state = '已交'
            dir = MEDIA_ROOT.replace ( '/', '\\' ) + '\\' + homework_class + '\\' + homework_name + '\\' + myFile.name
            change_item.home_work_dir = dir
            change_item.save ()
        return HttpResponseRedirect ( '/WorkManage/StudentMangeHomeWork/' )


# 作业撤回后台处理
def HomeWorkWithdraw(request):
    item_info = request.GET['info']
    # 用户学号
    number = request.session.get ( 'uid' )
    withdraw_items = HomeWork_Student_Set.objects.filter ( user_number=number, home_work_name=item_info )
    for withdraw_item in withdraw_items:  # 其实只有一条
        withdraw_item.is_posted = False
        withdraw_item.post_time = None
        withdraw_item.post_state = '未交'
        os.remove ( withdraw_item.home_work_dir )  # 删除已经上传的文件
        withdraw_item.home_work_dir = None
        withdraw_item.save ()

    return HttpResponseRedirect ( '/WorkManage/StudentMangeHomeWork/' )


# 老师或助教管理修改作业
def ManageHomeWork(request):
    class_name_home_works = {}  # 用于存放老师对应班级的作业
    # 获取权限类别
    user_permission = request.session.get ( 'permission' )
    print ( user_permission )
    # 助教对应的班级和班级所对应的作业
    if (user_permission == 2):
        # 获取用户对应的班级"student_class_name"
        assisant_class_name = request.session.get ( 'student_class_name' )
        home_works = HomeWork.objects.filter ( class_name=assisant_class_name )
        # 返回老师或助教姓名
        user_name = request.session.get ( 'name' )
        content = {'name': user_name, 'class': assisant_class_name, 'homeworks': home_works,
                   'permisssion': user_permission,
                   'Dict': json.dumps ( class_name_home_works )}
    elif (user_permission == 3):
        # 获取老师对应的班级"teacher_class_name"
        user_id = request.session.get ( 'uid' )
        user = User.objects.filter ( uid=user_id )
        teacher_class_names = user[0].teacher_class_name
        for teacher_class_name in teacher_class_names:
            # 获取对应班级的作业
            home_work_list = []
            home_works = HomeWork.objects.filter ( class_name=teacher_class_name )
            for home_work in home_works:
                home_work_list.append ( home_work.home_work_name )
            class_name_home_works[teacher_class_name] = home_work_list

        # 返回老师或助教姓名
        user_name = request.session.get ( 'name' )
        content = {'name': user_name, 'teacher_class_homework_info': class_name_home_works,
                   'permisssion': user_permission,
                   'Dict': json.dumps ( class_name_home_works )}

    return render ( request, 'WorkManage/ManageHomeWork.html', content )


# 老师或助教选择对应班级和班级所对的作业 后台处理
def SelectClassAndHomeWork(request):
    # 获取选择的班级和对应的作业

    classname = request.GET['cname']
    homework = request.GET['hname']

    posted_list_number = 0  # 记录正常提交的数量
    delayed_list_number = 0  # 记录迟交的数量
    unposted_list_number = 0  # 记录未提交的数量
    posted_list = []  # 用于存放正常提交的
    delayed_list = []  # 用于存放迟交的
    unposted_list = []  # 用于存放未提交的

    # 正常提交的
    posteds = HomeWork_Student_Set.objects.filter ( user_class=classname, home_work_name=homework, post_state='已交' )
    for posted in posteds:
        posted_list.append ( posted )
        posted_list_number = posted_list_number + 1

    # 迟交的
    delayeds = HomeWork_Student_Set.objects.filter ( user_class=classname, home_work_name=homework, post_state='迟交' )
    for delayed in delayeds:
        delayed_list.append ( delayed )
        delayed_list_number = delayed_list_number + 1

    # 未提交的
    unposteds = HomeWork_Student_Set.objects.filter ( Q ( post_state='未交' ) | Q ( post_state='被助教或老师退回' )|Q (post_state='被催交'),
                                                      user_class=classname, home_work_name=homework )
    for unposted in unposteds:
        unposted_list.append ( unposted )
        unposted_list_number = unposted_list_number + 1

    # 获得对应班级的人数
    class_student_total_number = Class.objects.filter ( class_name=classname )[0].student_total_number

    # 重写选择班级和对应作业的操作
    class_name_home_works = {}  # 用于存放老师对应班级的作业
    # 获取权限类别
    user_permission = request.session.get ( 'permission' )
    # 获得用户的名字
    user_name = request.session.get ( 'name' )
    # 助教对应的班级和班级所对应的作业
    if (user_permission == 2):
        # 获取用户对应的班级"student_class_name"
        assisant_class_name = request.session.get ( 'student_class_name' )
        # 拦截器
        if classname!=assisant_class_name:
            return HttpResponse('抱歉，您不能对此进行访问！')

        home_works = HomeWork.objects.filter ( class_name=assisant_class_name )

        content = {'number': class_student_total_number, 'posted': posted_list, 'posted_number': posted_list_number,
                   'delayed': delayed_list, 'delayed_number': delayed_list_number, 'unposted': unposted_list,
                   'unposted_number': unposted_list_number, 'classname': classname, 'work_name': homework,
                   'name': user_name,
                   # 重写的 选择班级和对应作业
                   'class': assisant_class_name, 'homeworks': home_works, 'permisssion': user_permission,
                   'Dict': json.dumps ( class_name_home_works )}

    elif (user_permission == 3):
        # 获取老师对应的班级"teacher_class_name"
        user_id = request.session.get ( 'uid' )
        user = User.objects.filter ( uid=user_id )
        teacher_class_names = user[0].teacher_class_name
        if classname not in teacher_class_names:
            return  HttpResponse('抱歉，您不能对此进行访问！')
        for teacher_class_name in teacher_class_names:
            # 获取对应班级的作业
            home_work_list = []
            home_works = HomeWork.objects.filter ( class_name=teacher_class_name )
            for home_work in home_works:
                home_work_list.append ( home_work.home_work_name )
            class_name_home_works[teacher_class_name] = home_work_list;

            content = {'number': class_student_total_number, 'posted': posted_list, 'posted_number': posted_list_number,
                       'delayed': delayed_list, 'delayed_number': delayed_list_number, 'unposted': unposted_list,
                       'unposted_number': unposted_list_number, 'classname': classname, 'work_name': homework,
                       'name': user_name,
                       # 重写的 选择班级和对应作业
                       'teacher_class_homework_info': class_name_home_works, 'permisssion': user_permission,
                       'Dict': json.dumps ( class_name_home_works )}

    return render ( request, 'WorkManage/ManageHomeWork.html', content )


# 老师或助教下载已提交的作业
def DownStudentsHomeWork(request):
    work_dirs = []
    wname = request.GET.get ( 'name' )  # 对应的作业名称
    students_ids = request.GET.get ( 'info' ).split ( ',' )
    # 要下载作业学生的学号
    for students_id in students_ids:
        work = HomeWork_Student_Set.objects.filter ( user_number=students_id, home_work_name=wname )[0]
        class_name = work.user_class
        work_dirs.append ( work.home_work_dir )

    utilities = ZipUtilities ()
    for work_dir in work_dirs:
        tmp_dl_path = os.path.join ( work_dir )
        utilities.toZip ( tmp_dl_path, wname )
    # utilities.close()
    # file_dir_names = wname+".zip".encode('utf-8').decode('gb2312')
    # file_dir_name = file_dir_names.encode('ISO8859_1')
    file_dir_name = class_name+wname+".rar"
    response = StreamingHttpResponse ( utilities.zip_file, content_type='application/rar' )# "nihao.rar"
    # response['Content-Disposition'] = 'attachment;filename="{0}"'.format (file_dir_name)  # 文件名要修改
    response['Content-Disposition'] = 'attachment; filename="' + urlquote(file_dir_name) +'"'
    return response


# 老师或助教退回重做已上传的作业
def WithDrawStudentsHomeWork(request):
    wname = request.GET.get ( 'name' )  # 对应的作业名称
    cname = request.GET.get ( 'cname' )  # 对应的班级名称
    students_ids = request.GET.get ( 'info' ).split ( ',' )
    # 要退回重做作业学生的学号
    for students_id in students_ids:
        works = HomeWork_Student_Set.objects.filter ( user_number=students_id, home_work_name=wname )
        for work in works:
            work.post_time = None
            work.post_state = '被助教或老师退回'
            work.is_posted = False
            work.homework_score = '还没有批改哦!'
            os.remove ( work.home_work_dir )  # 删除已经上传的文件
            work.home_work_dir = None
            work.save ()

    return HttpResponseRedirect ( '/WorkManage/SelectClassAndHomeWork/?cname=' + cname + '&hname=' + wname )


# 老师或助教发布成绩
def WritHomeWorkScore(request):
    numbers = request.GET.get ( 'number' )  # 存放对应的学号
    scores = request.GET.get ( 'score' )  # 存放对应的成绩
    home_work = request.GET.get ( 'hname' )
    class_name = request.GET.get ( 'cname' )
    students_number = numbers.split ( ',' )
    students_score = scores.split ( ',' )
    i = 0
    for student_number in students_number:
        student = HomeWork_Student_Set.objects.filter ( home_work_name=home_work, user_class=class_name,
                                                        user_number=student_number )[0]
        student.homework_score = students_score[i]
        i = i + 1;
        student.save ()

    return HttpResponseRedirect ( '/WorkManage/SelectClassAndHomeWork/?cname=' + class_name + '&hname=' + home_work )


#老师或助教催交作业
def HomeWorkUrge(request):
    work_name = request.GET.get('name')
    student_number = request.GET.get('number')
    homework = HomeWork_Student_Set.objects.filter(user_number=student_number,home_work_name=work_name)[0]
    class_name = homework.user_class
    #修改作业状态
    homework.post_state = '被催交'
    homework.save()
    message = Messages()
    message.timeStamp = time.time()#时间戳
    message.publish_date = datetime.datetime.now ().strftime ( "%Y-%m-%d" )  # 消息发布日期 当前时间
    message.publisher = request.session.get('name')#发布者
    message.message_to = homework.user_number
    message.message_type = 0
    message.message_content = homework.user_name+"同学，你的"+homework.home_work_name+"作业被催交，请尽快提交作业，谢谢！"
    message.is_valid = True
    message.save()
    return HttpResponseRedirect ( '/WorkManage/SelectClassAndHomeWork/?cname=' + class_name + '&hname=' + work_name )
    # user1 = User ()
    # user1.uid = 'S1610W0588'
    # user1.psw = '123'
    # user1.name = '杨科华'
    # user1.permission = 3
    # user1.student_class_name = ''
    # user1.teacher_class_name = ['软件1班', '软件2班']
    # user1.assisant_info = {
    #     "佘超伟": 'S1610W0587',
    #     "王耀东": 'S1610W0589',
    # }
    # user1.contact = '13677354665'
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0587'
    # user1.psw = 'S1610W0587'
    # user1.name = '黄丽达'
    # user1.permission = 3
    # user1.student_class_name = ''
    # user1.teacher_class_name = ['软件3班', '计科2班']
    # user1.assisant_info = {
    #     "张钺": 'S1610W0588',
    #     "廖成林": 'S1610W0579',
    # }
    # user1.contact = '13677354688'
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0579'
    # user1.psw = 'S1610W0579'
    # user1.name = '廖成林'
    # user1.permission = 2
    # user1.student_class_name = '软件3班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = '13677365496'
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0589'
    # user1.psw = 'S1610W0589'
    # user1.name = '王耀东'
    # user1.permission = 2
    # user1.student_class_name = '软件1班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = '13677365447'
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0101'
    # user1.psw = 'S1610W0101'
    # user1.name = '小岳岳'
    # user1.permission = 1
    # user1.student_class_name = '软件1班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0102'
    # user1.psw = 'S1610W0102'
    # user1.name = '小张'
    # user1.permission = 1
    # user1.student_class_name = '软件1班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0103'
    # user1.psw = 'S1610W0103'
    # user1.name = '小刘'
    # user1.permission = 1
    # user1.student_class_name = '软件1班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0301'
    # user1.psw = 'S1610W0301'
    # user1.name = '小李'
    # user1.permission = 1
    # user1.student_class_name = '软件3班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0302'
    # user1.psw = 'S1610W0302'
    # user1.name = '小美'
    # user1.permission = 1
    # user1.student_class_name = '软件3班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0303'
    # user1.psw = 'S1610W0303'
    # user1.name = '小希'
    # user1.permission = 1
    # user1.student_class_name = '软件3班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()
    #
    # user1 = User ()
    # user1.uid = 'S1610W0201'
    # user1.psw = 'S1610W0201'
    # user1.name = '乐乐'
    # user1.permission = 1
    # user1.student_class_name = '软件2班'
    # user1.teacher_class_name = []
    # user1.assisant_info = {}
    # user1.contact = ''
    # user1.is_valid = True
    # user1.save ()

# 加密解密
# def encrypt(key, s):
#     b = bytearray ( str ( s ).encode ( "gbk" ) )
#     n = len ( b )  # 求出 b 的字节数
#     c = bytearray ( n * 2 )
#     j = 0
#     for i in range ( 0, n ):
#         b1 = b[i]
#         b2 = b1 ^ key  # b1 = b2^ key
#         c1 = b2 % 16
#         c2 = b2 // 16  # b2 = c2*16 + c1
#         c1 = c1 + 65
#         c2 = c2 + 65  # c1,c2都是0~15之间的数,加上65就变成了A-P 的字符的编码
#         c[j] = c1
#         c[j + 1] = c2
#         j = j + 2
#     return c.decode ( "gbk" )
# def decrypt(key, s):
#     c = bytearray ( str ( s ).encode ( "gbk" ) )
#     n = len ( c )  # 计算 b 的字节数
#     if n % 2 != 0:
#         return ""
#     n = n // 2
#     b = bytearray ( n )
#     j = 0
#     for i in range ( 0, n ):
#         c1 = c[j]
#         c2 = c[j + 1]
#         j = j + 2
#         c1 = c1 - 65
#         c2 = c2 - 65
#         b2 = c2 * 16 + c1
#         b1 = b2 ^ key
#         b[i] = b1
#     try:
#         return b.decode ( "gbk" )
#     except:
#         return "failed"
