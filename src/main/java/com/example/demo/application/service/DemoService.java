package com.example.demo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.repository.DemoRepository;
import com.example.demo.bean.Goods;

// 業務処理を行うクラス
@Service
public class DemoService {

	@Autowired
	DemoRepository demoRepository;

	//
	public boolean decide(Goods goods) {
		Goods ordinaryGoods = demoRepository.findByName(goods.getName());
		if (goods.getPrice() <= ordinaryGoods.getPrice())
			return true;
		return false;
	}
}
