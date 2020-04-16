package net.kurien.blog.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.kurien.blog.common.template.Template;
import net.kurien.blog.domain.PageMaker;
import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.module.category.service.CategoryService;
import net.kurien.blog.module.category.vo.Category;
import net.kurien.blog.module.post.service.PostService;
import net.kurien.blog.module.post.vo.Post;

@Controller
@RequestMapping("/post")
public class PostController {
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Inject
	private Template template;

	@Inject
	private PostService postService;
	
	@Inject
	private CategoryService categoryService;
	
	/**
	 * 사용자가 목록 화면에 접속한다.
	 * 포스트 전체를 보여준다.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		int totalRowCount = postService.getCount("N", criteria);
		PageMaker pageMaker = new PageMaker(criteria, totalRowCount);
		
		List<Post> posts = postService.getList("N", criteria);
		
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("posts", posts);

		template.setTitle("Post list &dash; Kurien's Blog");
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/post.css\">");
		
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
		Post post = postService.get(postNo, "N");
		Category category = categoryService.get(post.getCategoryId());

		model.addAttribute("post", post);
		model.addAttribute("category", category);
		
		template.setTitle(post.getPostSubject() + " : Post view &dash; Kurien's Blog");
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/post.css\">");
		
		return "post/view";
	}
	
}
