package com.example.demo.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.application.repository.DemoRepository;
import com.example.demo.bean.Goods;

// 業務処理を行うクラス
@Service
public class DemoService {

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	@Autowired
	DemoRepository demoRepository;

	//
	public boolean decide(Goods goods) throws Exception {
		try {
			Goods ordinaryGoods = demoRepository.findByName(goods.getName());
			if (goods.getPrice() <= ordinaryGoods.getPrice())
				return true;
			return false;
		} catch (DataAccessException e) {
			logger.debug("データベースで問題が発生しました");
			throw new Exception(e);
		}

	}
}
