#coding=UTF-8
from django.http import HttpResponse
from MainPage.models import *
from ClassSchedule.models import *
from django.shortcuts import render
from django.http import HttpResponseRedirect
from django.http import JsonResponse
from bson import is_valid

#主页
def index(request):
#     User(uid='s123',psw='123',name='student',permission=1,student_class_name='计科1504',
#          teacher_class_name=[],assisant_info={},
#             contact='Tel:1233211234567',is_valid=True).save()
    uid=request.session.get("uid",default=None)
    if uid!=None :
        return HttpResponse(render(request, "MainPage/index.html"))
    else :
        try :
            psw=request.POST["psw"]
            uid=request.POST["uid"]
            user=User.objects(uid=uid)[0]
            if user["psw"]==psw and user["is_valid"]:
                #开启会话
                request.session["uid"]=user["uid"]
                request.session["name"]=user["name"]
                request.session["permission"]=user["permission"]
                request.session["student_class_name"]=user["student_class_name"]
                request.session["teacher_class_name"]=user["teacher_class_name"]
                return HttpResponse(render(request, "MainPage/index.html"))
        except Exception as err :
            pass
    return HttpResponseRedirect("/index/login?message=error")


#登陆
def login(request):
    uid=request.session.get("uid",default=None)
    if uid!=None :
        return HttpResponseRedirect("/index")
    else :
        message=request.GET.get("message", default=None)
        if message=="error":
            context={"content": "alert(\"登陆失败\");",}
            return render(request,"MainPage/login.html", context)
        else :
            return HttpResponse(render(request, "MainPage/login.html"))

#404
def test(request):
    return HttpResponse("<script>alert(\"你好，"+
                        request.session.get("name",default=None)+"。前方施工中。。。。\")</script>敬请期待。。。")
    
#登出
def logout(request):
    request.session.clear()
    return HttpResponse(render(request, "MainPage/login.html"))

#主页推送
def ajaxPush(request):
    data={}
    uid=request.session.get("uid",-1)
    classname=request.session.get('student_class_name',-1)
    
    messages=Messages.objects( Q(message_to=uid) & Q(message_type=0) & Q(is_valid=True) ).order_by('-timeStamp')
    for message in messages:
        data['homework'+str(message['timeStamp'])]='<span class="highlight">'+message['publish_date']\
            +"@"+message['message_content']+'</span>'
    
    messages=Messages.objects( ( Q(message_to=classname) | Q(message_to='all')) & Q(message_type=1) & Q(is_valid=True) ).order_by('-timeStamp')
    for message in messages:
        data['classinfo'+str(message['timeStamp'])]=message['publish_date']+"@"+message['message_content']
    
    if data:
        data["haspush"]="true"
    else :
        data["haspush"]="false"
        
    print(data)
    return JsonResponse(data)