## RestFull开发

### 介绍：资源表现层的状态转移

  - 资源：访问服务器获得的任何东西
  
  - 表现层：显示资源
  
  - 状态转移：客户端、服务端的交互

  - 软件架构的一种风格
  
  - 客户端/服务端可以通过XML、JSON传递数据
  
### 编程风格

 - URL：更加简洁、层次感，便于浏览器缓存（浏览器的缓冲机制：同一个URL）
 
     http://www.jd.com/#2111
 
 - 访问后台的CUID方法：
 
    1. 传统做法：
    
        http://www.jd.com/usrAction?type=add
        http://www.jd.com/usrAction?type=delete
        http://www.jd.com/usrAction?type=update
        
    2. Rest风格，使用一个URL地址：http://www.jd.com/usrAction
    
        基于请求方式的不同（7种）：post、get、delete、put
        
###  开发

 - 服务端
 
   1. 添加依赖
  
   2. 服务的接口、实现、实体类
  
   3. 发布服务
   
 - 客户端
 
    1. 添加依赖
    
    2. 调用远程服务
 