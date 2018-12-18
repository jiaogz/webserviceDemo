## CXF-JarWS

###  CXF介绍 ：soa的Service服务层框架

   * cxf 是 Celtrix （ESB框架）和 XFire（webserivice） 合并而成，并且捐给了apache  
   
   * CxF的核心是org.apache.cxf.Bus(总线)，类似于Spring的 ApplicationContext
   
   * CXF默认是依赖于Spring的
   
   * Apache CXF 发行包中的jar，如果全部放到lib中，需要 JDK1.6 及以上，否则会报JAX-WS版本不一致的问题
   
   * CXF 内置了Jetty服务器 ，它是servlet容器，好比tomcat
   
   * 可以支持多种协议，比如：SOAP、POST/HTTP、RESTful HTTP

### CXF特点

   * 与Spring、Servlet做了无缝对接，cxf框架里面集成了Servlet容器Jetty 
  
   * 支持注解的方式来发布webservice
   
   * 能够显示一个webservice的服务列表
   
   * 能够添加拦截器：输入拦截器、输出拦截器 ：输入日志信息拦截器、输出日志拦截器、用户权限认证的拦截器
 
### 使用

* 服务端

    - 导包
    
    - 写接口,基于注解形式的
    
    - 发布使用，使用JaxWsServerFactoryBean创建工厂
    
* 客户端 
    
    - wsImport生成本地代理，安装jar
    
    - 调用服务