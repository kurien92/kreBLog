package net.kurien.blog.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.kurien.blog.common.template.TemplateConfig;
import net.kurien.blog.common.template.metadata.TemplateCss;
import net.kurien.blog.common.template.metadata.TemplateJs;
import net.kurien.blog.common.template.metadata.TemplateMeta;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		

		TemplateMeta tMeta = new TemplateMeta();
		TemplateCss tCss = new TemplateCss();
		tCss.add("<link rel=\"stylesheet\" href=\"/css/home.css\">");
		TemplateJs tHJs = new TemplateJs();
		TemplateJs tFJs = new TemplateJs();
		
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setLang("ko");
		templateConfig.setCharset("utf-8");
		templateConfig.setTitle("Kurien's Blog");
		templateConfig.setMeta(tMeta);
		templateConfig.setCss(tCss);
		templateConfig.setHeadJs(tHJs);
		templateConfig.setFootJs(tFJs);
		
		model.addAttribute("templateConfig", templateConfig);
		
		return "home";
	}
}