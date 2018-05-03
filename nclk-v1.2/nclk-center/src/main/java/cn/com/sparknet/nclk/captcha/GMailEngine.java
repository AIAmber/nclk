package cn.com.sparknet.nclk.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * GMail样式的验证码引擎
 * @author chenxy
 */
public class GMailEngine extends ListImageCaptchaEngine {
	
	private final static int MIN_WORD_LENGTH = 5;	//验证码最小字符数
	private final static int MAX_WORD_LENGTH = 5;	//验证码最大字符数
	private final static int MIN_FONT_SIZE = 26;	//验证码最小字体大小
	private final static int MAX_FONT_SIZE = 26;	//验证码最大字体大小
	private final static int IMAGE_WIDTH = 134;		//验证码宽度
	private final static int IMAGE_HEIGHT = 44;		//验证码高度
	/**验证码随机字符**/
	private final static String RANDOM_WORD = "23456789abcdefghijkmnpqrstuvwxyz"; 
	/**验证码随机字体**/
	private final static Font[] RANDOM_FONT = new Font[]{
		new Font("nyala", Font.BOLD, MIN_FONT_SIZE),
		new Font("Bell MT", Font.PLAIN, MIN_FONT_SIZE),
		new Font("Credit valley", Font.BOLD, MIN_FONT_SIZE)
	};
	
	@Override
	protected void buildInitialFactories() {
		//字符设置
		WordGenerator word = new RandomWordGenerator(RANDOM_WORD);
		//字体设置
		FontGenerator font = new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, RANDOM_FONT);
		//背景设置
		BackgroundGenerator background = new UniColorBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, Color.white);
		//字符颜色设置
		RandomRangeColorGenerator color = new RandomRangeColorGenerator(
				new int[] { 0, 150 }, new int[] { 0, 150 },  new int[] { 0, 150 });
		//干扰线设置
		RandomRangeColorGenerator lineColor = new RandomRangeColorGenerator(
                new int[] { 150, 255 }, new int[] { 150, 255 }, new int[] { 150, 255 });
		TextDecorator[] textDecorators = new TextDecorator[1];
		textDecorators[0] = new LineTextDecorator(1,lineColor);
		//生成字符
		TextPaster textPaster = new DecoratedRandomTextPaster(MIN_WORD_LENGTH, MAX_WORD_LENGTH, color, textDecorators);
		//过滤器设置
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		//生成验证码图片
		WordToImage word2image = new DeformedComposedWordToImage(font, background, textPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(word, word2image));
	}

}
