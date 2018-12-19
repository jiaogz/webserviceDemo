import cn.yun.service.LanguageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ClientTest {

    //注入
    @Resource
    private LanguageService languageService;

    @Test
    public void test(){
        String language = languageService.getLanguage(1);
        System.out.println(language);
    }
}
