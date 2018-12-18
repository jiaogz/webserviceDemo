import cn.yun.service.LanguageService;
import cn.yun.service.LanguageService_Service;

public class ClientMail {


    public static void main(String[] args) {

        LanguageService_Service service = new LanguageService_Service();

        LanguageService servicePort = service.getLanguageServicePort();

        String language = servicePort.getLanguage(1);

        System.out.println(language);

    }
}
