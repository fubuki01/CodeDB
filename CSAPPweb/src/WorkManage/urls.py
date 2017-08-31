# coding=UTF-8
from django.conf.urls import url,include;
from .import views

urlpatterns = [

    # 作业管理主页进入
    url ( r'^Main/$', views.Main ),  # 作业管理主页进入

    # 助教发布作业与管理
    url(r'^TeacherPublishHomeWork/$',views.TeacherPublishHomeWork),#助教发布作业
    url(r'^TeacherPublishHomeWorkServer/$',views.TeacherPublishHomeWorkServer),#发布作业后台管理
    url(r'^HomeWorkDelect/$',views.HomeWorkDelect),#删除发布完成的作业

    # 学生管理作业
    url ( r'^StudentMangeHomeWork/$', views.StudentMangeHomeWork ),  # 学生作业管理
    url ( r'^UploadHomeWorkServer/$', views.UploadHomeWorkServer ),  # 学生作业管理 上传作业后台
    url (r'^HomeWorkWithdraw/$',views.HomeWorkWithdraw),#作业撤回点击后台

    #助教或老师对学生作业进行管理
    url ( r'^ManageHomeWork/$', views.ManageHomeWork ),  # 老师助教管理作业
    url(r'^SelectClassAndHomeWork/$',views.SelectClassAndHomeWork),#老师或助教选择对应的班级和班级所对应的作业
    url(r'^DownStudentsHomeWork/$',views.DownStudentsHomeWork),#老师或助教下载的作业
    url(r'^WithDrawStudentsHomeWork/$',views.WithDrawStudentsHomeWork),#老师或助教退回重做已上传的作业
    url(r'^WritHomeWorkScore/$',views.WritHomeWorkScore),#老师或助教发布成绩
    url(r'HomeWorkUrge/$',views.HomeWorkUrge),#老师或助教催交作业

]