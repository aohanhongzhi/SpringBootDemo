package com.example.demo.contrller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.bean.Book;

@Controller
public class BookController {
	@GetMapping("/book")
	public ModelAndView books() {
		ArrayList<Book> books = new ArrayList<Book>();
		Book b1 = new Book();
		b1.setId(1);
		b1.setName("三国演义");
		b1.setAuthor("罗贯中");
		books.add(b1);
		Book b2 = new Book();
		b2.setId(2);
		b2.setName("曹雪芹");
		b2.setAuthor("红楼梦");
		books.add(b2);
		ModelAndView mv = new ModelAndView();
		mv.addObject("books", books);
		mv.setViewName("books");
		return mv;
	}

}
