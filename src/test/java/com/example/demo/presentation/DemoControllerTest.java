package com.example.demo.presentation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.application.service.DemoService;
import com.example.demo.bean.Goods;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	DemoService mockService;

	@Test
	public void shoppingTest01() throws Exception {

		// 使用する商品情報の準備
		Goods goods = createGoods();

		// リクエストの準備
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(goods);

		// モックの設定
		when(mockService.decide(any(Goods.class))).thenReturn(true);

		// execute
		String responseJson = this.mockMvc
				.perform(post("/demo/shopping").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		Boolean result = objectMapper.readValue(responseJson, Boolean.class);
		verify(mockService, times(1)).decide(any(Goods.class));
		assertTrue(result);
	}

	private Goods createGoods() {

		// Test用のデータ作成
		Goods goods = new Goods();
		goods.setName("アボガド");
		goods.setPrice(180);
		return goods;
	}
}
