CustomLintDemo
===============
CustomLintDemo is a series of custom Lint rules specific implementation, designed to demonstrate
 the use of Lint API.  
 
Detailed custom Lint implementation methods and principles, you can see the article:  
[Android Lint工作原理剖析](http://carrotsight.com/2016/06/21/Android%20Lint%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86%E5%89%96%E6%9E%90.html)  
[浅谈Android自定义Lint规则的实现 （一）](http://carrotsight.com/2016/01/29/%E6%B5%85%E8%B0%88Android%E8%87%AA%E5%AE%9A%E4%B9%89Lint%E8%A7%84%E5%88%99%E7%9A%84%E5%AE%9E%E7%8E%B0%20%EF%BC%88%E4%B8%80%EF%BC%89.html)  
[浅谈Android自定义Lint规则的实现 （二）](http://carrotsight.com/2016/02/01/%E6%B5%85%E8%B0%88Android%E8%87%AA%E5%AE%9A%E4%B9%89Lint%E8%A7%84%E5%88%99%E7%9A%84%E5%AE%9E%E7%8E%B0%20%EF%BC%88%E4%BA%8C%EF%BC%89.html)
   
======
This demo program does Lint checking for the following issues:  

[1]Don't use Log/Toast/Handler directly. Use LogUtil/ToastUtil/HandlerUtil instead.  
[2]Prefix layout xml file of Activity/Fragment with "activity_"/""fragment_".  
[3]Layout resource file in ViewHolder must be named with prefix "item_".  
[4]In layout xml file, you should name an view item with specified prefix.  
[5]In build.gradle file, version info should refer to gradle-wrapper.properties.  
[6]Nested For/If/Try statements can not be more than 3 layers.  
[7]A constant should be named with only UPPER CASE letter and underline.  
[8]You should not call `new Message()` directly. Instead, you should use `handler.obtainMessage` 
or `Message.Obtain()`.  
[9]You should throw your own exception with the one caught in try-catch block.   

  
======
CustomLintDemo对Android Lint规则进行了扩展，在不影响Android Lint原有的检查项目的基础上，额外检查以下内容：
【1】不能直接使用log,toast，handler  
【2】activity或fragment对应的layout资源，用前缀标示，例如：activity_mediapicker.xml  
【3】viewholder关联的资源以item结尾  
【4】xml布局文件中使用前缀表明类型，比如，btn_xxx  
【5】build.gradle里面那个版本依赖，必须通过 gradle.properties文件中指定  
【6】嵌套For/If/Try最大深度3  
【7】常量的名称   
【8】Message必须采用Message.Obtain获取 
【9】throw异常的时候注意把已经捕获的异常也一并带上，例如：throw new ManagerException("数据库异常");应该改成throw new ManagerException("数据库异常",e)    

======
自定义Lint规则的2种检查方法：  
1、目前最新版的Android Studio已经把一部分自定义Lint检查的功能集成到了IDE中，直接可见。  
![](http://7xle8x.com1.z0.glb.clouddn.com/16-4-13/85143697.jpg)  
  
2、在终端运行"gradle lint"任务，得到完整Lint报告。 
![](http://7xle8x.com1.z0.glb.clouddn.com/16-1-8/9825338.jpg)



