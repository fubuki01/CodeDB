package com.readboy.game;

public interface Watched {
	//在其接口中定义一个用来增加观察者的方法  
    public void add(Watcher watcher);  
    //再定义一个用来删除观察者权利的方法  
    public void remove(Watcher watcher);  
    //再定义一个可以实现行为并向观察者传输信息的方法
    public void notifyWatcher();  
	//public void handWriteOver(Watcher watcher); 
}
