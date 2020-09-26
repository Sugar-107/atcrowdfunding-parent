package com.atguigu.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GongfuController {


	@PreAuthorize("hasAnyRole('学徒','大师','宗师') or hasAuthority('putong:luohan')")
	@GetMapping("/level1/1")
	public String leve1Page(){
		return "/level1/"+1;
	}



	@PreAuthorize("hasRole('学徒') or hasAuthority('putong:wudang')")
	@GetMapping("/level1/2")
	public String leve1Page2(){
		return "/level1/"+2;
	}


	@PreAuthorize("hasRole('学徒') or hasAuthority('putong:quanzhen')")
	@GetMapping("/level1/3")
	public String leve1Page3(){
		return "/level1/"+3;
	}

	@PreAuthorize("hasAnyRole('大师','宗师') or hasAnyAuthority('gaoji:gongfu')")
	@GetMapping("/level2/{path}")
	public String leve2Page(@PathVariable("path")String path){
		return "/level2/"+path;
	}


	@PreAuthorize("hasAnyRole('宗师') or hasAnyAuthority('shenji:gongfu')")
	@GetMapping("/level3/{path}")
	public String leve3Page(@PathVariable("path")String path){
		return "/level3/"+path;
	}

}
