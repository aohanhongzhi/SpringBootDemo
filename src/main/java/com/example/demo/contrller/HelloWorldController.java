package com.example.demo.contrller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//如果是页面开发需要使用Controller，如果是返回数据json那么可以使用RestController

@RestController
public class HelloWorldController {
	@RequestMapping("/hello")
	public String index() {
		return "Hello World";
	}

	@RequestMapping("/hellomap")
	public HashMap<String, Object> map() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("status", 200);
		hashMap.put("result", "ok");
		return hashMap;
	}

}