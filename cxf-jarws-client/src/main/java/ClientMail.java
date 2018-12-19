import cn.yun.service.LanguageService;
import cn.yun.service.LanguageService_Service;

/**
 * 远程调用服务的方式一：
 *
 * 1.wsimport生成本地代理对象，安装jar包
 *
 * 2.调用服务
 *
 */
public class ClientMail {


    public static void main(String[] args) {

        LanguageService_Service service = new LanguageService_Service();

        LanguageService servicePort = service.getLanguageServicePort();

        String language = servicePort.getLanguage(1);

        System.out.println(language);

    }
}
