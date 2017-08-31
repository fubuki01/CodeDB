# coding=UTF-8
from mongoengine import *
import datetime
# from CsapphnuApplication.settings import DBNAME
# #连接数据库的名字
# connect(DBNAME)

# 作业学生子集
class HomeWork_Student_Set(Document):
    # 学生姓名
    user_name = StringField ( max_length=20, required=True)
    # 学生学号
    user_number = StringField (max_length=20, required=True)
    # 学生班级
    user_class = StringField(max_length=20,required=True)
    # 作业名称
    home_work_name = StringField(max_length=50,required=True)
    # 作业开始日期
    home_work_start_time = StringField(max_length=30,required=True)
    # 作业结束日期
    home_work_end_time = StringField(max_length=30,required=True)
    # 作业提交日期
    post_time = StringField(max_length=30,default='')
    # 作业状态
    post_state = StringField(max_length=20,default='未交')
    # 作业路径
    home_work_dir = StringField(max_length=100)
    # 作业是否提交
    is_posted = BooleanField ( default=False, required=True)
    #作业成绩
    homework_score = StringField(max_length=20,required=True,default='还没批改哦!')
    #重复率
    repetition_rate = StringField(max_length=10)

# 作业数据表
class HomeWork(Document):
    # 班级名称
    class_name = StringField(max_length=20,required=True)
    # 作业名称
    home_work_name = StringField(max_length=30, required=True)
    # 作业开始时间
    start_time = StringField(max_length=30,required=True)
    # 作业结束时间
    end_time = StringField(max_length=30,required=True)
    # 作业子集 用于存放学生信息
    home_work_list = ListField(ReferenceField(HomeWork_Student_Set))

# 班级表
class Class(Document):
    # 班级名称
    class_name = StringField(max_length=20 ,required=True)
    # 班级老师名称
    teacher_name = StringField(max_length=20,required=True)
    # 老师学号
    teache_number = StringField(max_length=20,required=True)
    # 助教姓名
    assiaant_name = StringField(max_length=20,required=True)
    # 助教学号
    assisant_number = StringField(max_length=20,required=True)
    # 班级学生总人数
    student_total_number = IntField(required=True)

#  学生用户表
class User(Document):
    uid=StringField(max_length=12)  #用户id（学号/工号）
    psw=StringField(max_length=12)  #用户密码
    name=StringField(max_length=12) #用户姓名
    permission=IntField()   #用户身份：1—学生；2—助教；3—老师；4—管理员；
    student_class_name=StringField(max_length=12)   #学生和助教所在班级，老师为空；
    teacher_class_name=ListField()  #老师所带班级的列表，学生助教为空；
    assisant_info=DictField()   #老师所带助教的列表，学生助教为空；
    contact=StringField(max_length=30)  #联系方式；
    is_valid=BooleanField()   #True表示是该学期有效用户，False表示往学期的无效用户；
    student_grounp = StringField(max_length=10);  #学生的分组

# 消息队列
class Messages(Document):
    timeStamp=FloatField()  #时间戳
    publish_date=StringField(max_length=20)    #发布日期
    publisher=StringField(max_length=12)    #发布者
    message_to=StringField(max_length=12)   #消息对象：用户ID（面向单个用户），班级（面向单个班级），all（所有人）
    message_type=IntField() #消息类型：0—催交作业（首页推送）；1—课程通知（首页推送）；2—讨论课安排；3—实验课安排；4—大班课安排；
    message_content=StringField(max_length=500) #消息内容
    is_valid=BooleanField() #消息是否过期



