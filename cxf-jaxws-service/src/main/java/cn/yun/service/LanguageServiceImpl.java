package cn.yun.service;


import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class LanguageServiceImpl implements LanguageService {
    @Override
    public String getLanguage(int position) {
        String language = "";
        switch (position){
            case 1:
                language =  "java";
                break;
            case 2:
                language =  "C";
                break;
            case 3:
                language =  "C++";
                break;
            default:
                break;
        }

        return language;
    }

    /**
     * 发布服务：
     * 1.创建工厂
     *
     *  webservice 访问流程：
     * 	 -. 检测本地代理描述的wsdl是否与服务端的wsdl一致 ，俗称为握手
     * 	 -. 通过soap协议实现通信 ，采用的是post请求 ， 数据封装在满足soap规约的xml中
     * 	 -. 返回数据 同样采用的是soap通信，  数据封装在满足soap规约的xml中
     * @param args
     */
    public static void main(String[] args) {

        LanguageService service = new LanguageServiceImpl();

        String address = "http://localhost:80/cxf/LanguageService";

        JaxWsServerFactoryBean factoryBean=new JaxWsServerFactoryBean();

        factoryBean.setAddress(address);
        //对外提供webservcie的业务类或者接口
        factoryBean.setServiceClass(LanguageService.class);
        //服务的实现bean
        factoryBean.setServiceBean(service);

        //添加日志拦截器
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());

        factoryBean.create();

        System.out.println("服务地址:"+address+"?WSDL");
    }


}
