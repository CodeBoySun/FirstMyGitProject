package org.lanmei.cms.controller.kaptcha;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.lanmei.cms.common.session.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping
public class KaptchaImageCreate {
	
	private final static Logger logger = LoggerFactory.getLogger("KaptchaImageCreate.class");	
	{
		logger.debug("KaptchaImageCreate Create Bean............. ");
	}
	
	
	private Producer kaptchaProducer=null;  
	  
    @Autowired  
    public void setCaptchaProducer(Producer kaptchaProducer) {  
        this.kaptchaProducer = kaptchaProducer;  
    }  
  
    @RequestMapping("/kaptcha")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{  
    	
    	logger.debug("into kaptcha/image/create");
       response.setDateHeader("Expires",0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        String validateCode = kaptchaProducer.createText();  
        logger.debug("验证码 = " +  validateCode);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, validateCode); 
       	
		SessionUtils.setSession("validateCode", validateCode);
		
        BufferedImage bi = kaptchaProducer.createImage(validateCode);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }  
}
