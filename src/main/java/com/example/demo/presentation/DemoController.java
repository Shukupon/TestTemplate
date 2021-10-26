package com.example.demo.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.service.DemoService;
import com.example.demo.bean.Goods;

//RESTAPIのコントローラクラス
@RestController
@RequestMapping("/demo")
public class DemoController {

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	DemoService demoService;

	@PostMapping("/shopping")
	public ResponseEntity<Boolean> shopping(@RequestBody Goods goods) {

		logger.info("処理開始");
		Boolean response = demoService.decide(goods);
		logger.info("処理完了");

		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(response);
	}
}
