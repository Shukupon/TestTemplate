package com.example.demo.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.application.repository.DemoRepository;
import com.example.demo.bean.Goods;

@ExtendWith(SpringExtension.class)
public class DemoServiceTest {

	@TestConfiguration
	static class DemoUseCaseTestConfiguration {

		// テスト対象クラスのBean生成
		@Bean
		public DemoService demoService() {
			return new DemoService();
		}
	}

	// モックの作成
	@MockBean
	DemoRepository mockRepository;

	// テスト対象クラスのインスタンスにモックを注入
	@Autowired
	private DemoService target;

	// 正常系のテストメソッド
	@Test
	public void decideTest01() throws Exception {

		// 渡す引数の準備
		Goods goods = createGoods();

		// どんなGoods型の引数でもtrueを返すようにmockを定義
		when(mockRepository.findByName(Mockito.anyString())).thenReturn(createOrdinaryGoods());

		// assert
		assertTrue(target.decide(goods));
	}

	// 異常系のテストメソッド
	@Test
	public void decideTest02() throws Exception {

		// 渡す引数の準備
		Goods goods = createGoods();

		// どんなGoods型の引数でもtrueを返すようにmockを定義
		doThrow(new DataAccessException("") {
		}).when(mockRepository).findByName(Mockito.anyString());

		// throwされるExceptionのクラスをassert
		Throwable e = assertThrows(Exception.class, () -> {
			target.decide(goods);
		});
		
		// throwされるExceptionの元のクラスをassert
		assertTrue(e.getCause() instanceof DataAccessException);
	}

	// Test用のデータ作成
	private Goods createGoods() {

		Goods goods = new Goods();
		goods.setName("avocado");
		goods.setPrice(180);
		return goods;
	}

	// モック用のデータ作成
	private Goods createOrdinaryGoods() {

		Goods goods = new Goods();
		goods.setName("avocado");
		goods.setPrice(200);
		return goods;
	}

}