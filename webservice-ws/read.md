## webservice

### 介绍
* WebService技术规则：
 
    1. 基于web的系统级接口规范
    
    - 一个普通适用标准 :http+xml
    
    - 任何网络通信的操作系统
    
    - 自包含、自描述、模块化
    
    - 发布、定位、通过web调用
 
   2. web service 技术和规则
    
    - xml(可拓展标记语言)
    
    - soap(简易对象访问协议)  soap – http+xmlsocket
    
    - wsdl(web services 描述语言) 
    
    - uddi(通用描述、发现及整合)
 
   3. 跨平台的可互操作性
   
    - 基于xml
   
    - 基于xsd(xml schema )
   
    - 基于http

  4. webservice 适用场景
   
   - 跨防火墙的通信
   
   - 异构系统间应用程序集成（jee:(EJB RMI)、php、c++、.net (com+)）
   
   
### HTTPClient

* 对Http请求进行封装

    HttpClient使用步骤如下：
    
    1.创建 HttpClient 的实例 
    
    2.创建某种连接方法的实例，在这里是 GetMethod。在 GetMethod 的构造函数中传入待连接的地址
    
    3.配置要传输的参数，和消息头信息
    
    4.调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例
    
    5.通过response读取字符串
    
    6.释放连接。无论执行方法是否成功，都必须释放连接    

### wsimport调用第三方服务

* 问题：

    1. 如何解析结果
    2. 如何传递对象参数 

  - 推荐的访问方式
          
     在jdk 1.6 版本以后 ，通过jax-ws 包提供对webservice的支持 
	     
	 - 该方式通过注解的方式来声明webservice 
	     
	 - 通过 jdk EndPoint.publish()发布webserive服务

	    早期的版本 ： jax-rpc （remote produce call）

	 - webservice 纳入 w3c规范，其他的平台都支持该规范 ：JEE\Php\.NET
	    
	 - wsimport 方式 ： 对远程的webservice生成本地代理，再通过本地代理来访问webservice
       
      1. wsimport 命令的位置： JDK安装目录
	
	- 要求：
	  
	  1. jdk的 版本要在 jdk 1.6.21及以上 
	  
	  2. 操作系统安装的jdk版本 与 MyEclispe 及 默认指定的版本要一致

	- wsimport使用： 记得设置jdk\bin  环境变量  指定path 
           
      语法  wsimport [opations] <wsdl_uri>
	     
	   - wsdl_uri:wsdl 的统一资源标识符
	     
	   - d  ：指定要输出的文件的位置
	   
	   - s  ：表示要解析java的源码 ，默认解析出的是class字节码 
	   
	   - p  ： 指定输出的包名
	   
	   wsimport -s ./ -p cn.yun.import http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?WSDL
	   
### 自定义发布一个webservice

   - 声明 某个业务服务为webservice服务通过@webservice 注解来声明
    
   - 发布webservice服务Endpoint.publish()发布
   
   - 通过wsimport生成本地代理来访问自己发布的webservicewsimport
   
   通过注解配置修改wsdl文件, 让它具有更好的可读性 
      需求：
        1. 想改方法的输入参数名、返回参数名
        2. 想改服务名，方法名
        3. 对该业务类的某些方法不想对外发布 
          -  默认只有public 修饰的方法会以webservice的形式对外发布 
   	 而 private  受保护的，缺省的，都不会发布
          - 某些public 方法也不想对外发布 
   
      实现：注解配置修改wsdl文件
      
### 基于接口的webservice开发

* 服务端

    1.定义接口，暴露需要对外发布的方法
        
        @Webservice
        
    2.书写实现类，注意也需要添加@Webservice
    
    3.使用Endpoint.publish发布服务
    
* 客户端

    1.通过wsimport生成本地代理对象
    
    2.将生成的对象打包成jar包形式引入
        
        jar -cvf xxx.jar ./cn
        
        安装到本地
        mvn install:install-file -DgroupId=cn.yun.service -DartifactId=sgs-server-api -Dversion=0.10.1 -Dpackaging=jar -Dfile=F:/reddwarf-0.10.1/sgs-server-api-0.10.1.jar 
    
    3.使用
    
          
	   