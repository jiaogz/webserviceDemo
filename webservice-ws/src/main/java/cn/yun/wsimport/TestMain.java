package cn.yun.wsimport;

import java.util.List;

/**
 * 使用wsimport调用第三方服务:查询手机号信息
 *
 * 执行wsimport -s ./ -p cn.yun.msimport http://ws......?WSDL
 *
 * 通过JDK自带的wsimport解析WSDL接口描述生成本地代理对象来访问
 */
public class TestMain {
    public static void main(String[] args) {

        MobileCodeWS ws = new MobileCodeWS();

        MobileCodeWSSoap wsSoap = ws.getMobileCodeWSSoap();

        //返回数据：简单数据
        String mobileCodeInfo = wsSoap.getMobileCodeInfo("18924742402","");
        System.out.println(mobileCodeInfo);


        //返回复杂数据
        ArrayOfString databaseInfo = wsSoap.getDatabaseInfo();

        List<String> strings = databaseInfo.getString();

        for (String str:strings) {
            System.out.println(str);
        }

    }

}
