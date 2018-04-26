package cn.com.sparknet.nclk.captcha;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 验证码服务类（单例）
 * @author chenxy
 */
public class CaptchaServiceSingleton {
	
	private static ImageCaptchaService instance=new DefaultManageableImageCaptchaService(
			new FastHashMapCaptchaStore(),new GMailEngine(),180,100000,75000);
	
	public static ImageCaptchaService getInstance(){
	    return instance;
	}
	
}