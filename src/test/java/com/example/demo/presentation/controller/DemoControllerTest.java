package com.example.demo.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.application.service.DemoService;
import com.example.demo.bean.Goods;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DemoController.class)
@ExtendWith(OutputCaptureExtension.class)
public class DemoControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	DemoService mockService;

	@Test
	public void shoppingTest01(CapturedOutput output) throws Exception {

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
		assertThat(output).contains("処理開始");
		assertThat(output).contains("処理完了");

	}

	@Test
	public void shoppingTest02(CapturedOutput output) throws Exception {

		// 使用する商品情報の準備
		Goods goods = createGoods();

		// リクエストの準備
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(goods);

		// モックの設定
		doThrow(Exception.class).when(mockService).decide(any(Goods.class));

		// execute
		String responseJson = this.mockMvc
				.perform(post("/demo/shopping").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().is5xxServerError()).andReturn().getResponse().getContentAsString();

		verify(mockService, times(1)).decide(any(Goods.class));
		assertTrue(responseJson.equals("an error has occured."));
		assertThat(output).contains("処理開始");

	}

	private Goods createGoods() {

		// Test用のデータ作成
		Goods goods = new Goods();
		goods.setName("アボガド");
		goods.setPrice(180);
		return goods;
	}
}
