package com.blog.util.bo;

import com.blog.exception.BusinessException;
import com.blog.util.CodeUties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
@Slf4j
@Data
public class HttpSessionBO<T,U> {
    private T email;
    private U code;

    public HttpSessionBO(T email, U code) {
        this.email = email;
        this.code = code;
    }

    public static HttpSessionBO<String,String> getHttpSessionBO(HttpSession session,String toMail) {
        String code = CodeUties.getCode();
        session.setAttribute("code",code);
        session.setAttribute("email",toMail);
        Object emailObj = session.getAttribute("email");
        Object codeObj =  session.getAttribute("code");
        if (emailObj==null || codeObj==null){
            log.error("Httpsession 数据丢失");
            throw new BusinessException("Httpsession 数据丢失");
        }
        return new HttpSessionBO( emailObj,codeObj);
    }

}
