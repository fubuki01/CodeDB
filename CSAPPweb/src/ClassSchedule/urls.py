#coding=UTF-8
from django.conf.urls import url
from ClassSchedule import views

urlpatterns = [
    url(r'^$', views.classSchedule),
    url(r'^ajaxQuery$', views.ajaxQuery),
    url(r'^ajaxQuery2$', views.ajaxQuery2),
    url(r'^ajaxPublish$', views.ajaxPublish),
    url(r'^ajaxDelete$', views.ajaxDelete),
]
