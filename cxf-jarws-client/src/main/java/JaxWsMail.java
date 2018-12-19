import cn.yun.service.LanguageService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 远程调用服务的方式二：
 *
 */

public class JaxWsMail {


    public static void main(String[] args) {

        //1.创建代理工厂
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        //2.设置地址
        factoryBean.setAddress("http://localhost:80/cxf/LanguageService");

        //3.设置接口类型
        factoryBean.setServiceClass(LanguageService.class);

        //4.对接口生成代理对象（JDK的接口动态代理）  -- java代理：静态代理、动态代理（JDK接口代理、cgLib子类代理）
        LanguageService service = factoryBean.create(LanguageService.class);

        //5.调用服务
        String language = service.getLanguage(1);

        System.out.println(language);

    }

}
