package com.mycompany.webapp.dao.productdb;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;
import com.mycompany.webapp.dto.StockList;

@Mapper
public interface ProductDao {

	public int countProduct();
	public List<Product> selectProductList(@Param("pager") Pager pager);
	public Product selectProductWithPid(String pid);
	public List<Color> selectProductColors(String pid);
	public List<Stock> selectProductStock(HashMap<String, Object> pidColorcode);
	public int countStock();
	public List<StockList> selectStockList(@Param("pager") Pager pager);
	public int updateStock(Stock stock);
	public int deleteByPid(String pid);
}