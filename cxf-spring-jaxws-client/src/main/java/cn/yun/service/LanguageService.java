package cn.yun.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 开发语言排名查询服务
 */

@WebService(serviceName = "LanguageService")
public interface LanguageService {

    @WebResult(name = "language") String getLanguage(@WebParam(name = "position") int position);
}
