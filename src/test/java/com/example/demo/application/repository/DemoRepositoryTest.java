package com.example.demo.application.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.bean.Goods;

@MybatisTest
public class DemoRepositoryTest {

	@Autowired
	private DemoRepository demoRepository;

	@Test
	public void findByNameTest() {
		Goods goods = createGoods();
		Goods result = demoRepository.findByName(goods.getName());
		assertThat(goods, samePropertyValuesAs(result));
	}

	private Goods createGoods() {

		// Test用のデータ作成
		Goods goods = new Goods();
		goods.setName("avocado");
		goods.setPrice(180);
		return goods;
	}
}
