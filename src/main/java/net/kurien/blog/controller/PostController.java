package net.kurien.blog.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.kurien.blog.common.template.TemplateConfig;
import net.kurien.blog.common.template.metadata.TemplateCss;
import net.kurien.blog.common.template.metadata.TemplateJs;
import net.kurien.blog.common.template.metadata.TemplateMeta;
import net.kurien.blog.domain.PageMaker;
import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.module.post.service.PostService;
import net.kurien.blog.module.post.vo.Post;

@Controller
@RequestMapping("/post")
public class PostController {
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Inject
	private PostService postService;
	
	/**
	 * 사용자가 목록 화면에 접속한다.
	 * 포스트 전체를 보여준다.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		TemplateMeta tMeta = new TemplateMeta();
		TemplateCss tCss = new TemplateCss();
		tCss.add("<link rel=\"stylesheet\" href=\"/css/module/post.css\">");
		TemplateJs tHJs = new TemplateJs();
		TemplateJs tFJs = new TemplateJs();
		
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setLang("ko");
		templateConfig.setCharset("utf-8");
		templateConfig.setTitle("Post List &dash; Kurien's Blog");
		templateConfig.setMeta(tMeta);
		templateConfig.setCss(tCss);
		templateConfig.setHeadJs(tHJs);
		templateConfig.setFootJs(tFJs);
		
		model.addAttribute("templateConfig", templateConfig);
		
//		여기까지는 Interceptor로 이동 
		
		int totalRowCount = postService.getCount(criteria);
		PageMaker pageMaker = new PageMaker(criteria, totalRowCount);
		
		List<Post> posts = postService.getList(criteria);
		
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("posts", posts);
		
		return "post/list";
	}
	
	/**
	 * 사용자가 포스트 뷰 화면에 접속한다.
	 * 해당 번호의 포스트를 보여준다.
	 * 
	 * @param postNo
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/view/{postNo}", method = RequestMethod.GET)
	public String view(@PathVariable int postNo, Model model) throws Exception {
		TemplateMeta tMeta = new TemplateMeta();
		TemplateCss tCss = new TemplateCss();
		tCss.add("<link rel=\"stylesheet\" href=\"/css/module/post.css\">");
		TemplateJs tHJs = new TemplateJs();
		TemplateJs tFJs = new TemplateJs();
		
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setLang("ko");
		templateConfig.setCharset("utf-8");
		templateConfig.setTitle("Post View &dash; Kurien's Blog");
		templateConfig.setMeta(tMeta);
		templateConfig.setCss(tCss);
		templateConfig.setHeadJs(tHJs);
		templateConfig.setFootJs(tFJs);
		
		model.addAttribute("templateConfig", templateConfig);
		
		Post post = postService.get(postNo);
		
		model.addAttribute("post", post);
		
		return "post/view";
	}
	
}
