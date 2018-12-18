## ajax请求webservice访问天气信息

 1. 使用wsimport生成本地代理对象，会报错ERROR，将xml保存到本地，删除不支持的标签，再次生成
 
    wsimport -s ./ http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?WSDL
 
 2. 打成jar包安装到本地仓库
 
     jar cvf weather.jar ./cn
     
     mvn install:install-file -DgroupId=cn.yun.weather -DartifactId=weath-api -Dversion=1.0.0 -Dpackaging=jar -Dfile=./weather.jar
     
 3. 客户端书写
 
        