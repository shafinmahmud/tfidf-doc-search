package shafin.ml.tfidf.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import shafin.ml.tfidf.model.ArticleDto;
import shafin.ml.tfidf.model.BanglapediaDoc;
import shafin.ml.tfidf.service.HomeService;


@Controller
public class HomeController {

	@Autowired
	HomeService homeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		ArrayList<ArticleDto>  docs = (ArrayList<ArticleDto>) homeService.getRandomArticle(10);
		model.addAttribute("docs", docs);
		
		return "home";
	}
	
	@RequestMapping(value = "/doc", params = { "name" }, method = RequestMethod.GET)
	public String getDoc(HttpServletRequest request, @RequestParam("name") String name, Model model) {
			
		BanglapediaDoc doc = homeService.pullDoc(name);
		model.addAttribute("doc",doc);
		
		return "article";
	}
}
