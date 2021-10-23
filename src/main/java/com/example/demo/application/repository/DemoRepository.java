package com.example.demo.application.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bean.Goods;
//リポジトリのインターフェース
@Mapper
public interface DemoRepository {

	Goods findByName(String name);
}
