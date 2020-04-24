package net.kurien.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.kurien.blog.common.template.Template;
import net.kurien.blog.domain.PageMaker;
import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.exception.NotFoundDataException;
import net.kurien.blog.module.category.service.CategoryService;
import net.kurien.blog.module.category.vo.Category;
import net.kurien.blog.module.post.service.PostService;
import net.kurien.blog.module.post.vo.Post;

@Controller
@RequestMapping("/category")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Inject
	private Template template;
	
	@Inject
	private PostService postService;
	
	@Inject
	private CategoryService categoryService;
	
	@RequestMapping("/{categoryId}")
	public String list(@PathVariable String categoryId, SearchCriteria criteria, Model model) throws Exception {
		logger.info(categoryId + " : 메서드 시작");
		
		Category category = categoryService.get(categoryId);
		
		if(category == null) {
			throw new NotFoundDataException("존재하지 않는 카테고리입니다.");
		}
		
		int totalRowCount = postService.getCountByCategoryId(category.getCategoryId(), "N");
		PageMaker pageMaker = new PageMaker(criteria, totalRowCount);
		
		List<String> categories = new ArrayList<>();
		categories.add(category.getCategoryId());
		
		List<Post> posts = postService.getListByCategoryIds(categories, "N");
		
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("posts", posts);
		
		template.setSubTitle("Category " + category.getCategoryName());
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/post.css\">");

		return "post/list";
	}
}