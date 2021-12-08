package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.productdb.ProductDao;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;
import com.mycompany.webapp.dto.StockList;

@Service
public class ProductService {
	
	@Resource
	private ProductDao productDao;

	public int getTotalProduct() {
		return productDao.countProduct();
	}

	public List<Product> getProductList(Pager pager) {
		return productDao.selectProductList(pager);
	}
	
	public Product selectWithPid(String pid) {
		return productDao.selectProductWithPid(pid);
	}

	public List<Color> getProductColors(String pid) {
		return productDao.selectProductColors(pid);
	}
	
	public List<Stock> selectStock(String pid, String colorcode) {
		HashMap<String, Object> pidColorcode = new HashMap<String, Object>();
		pidColorcode.put("pid", pid);
		pidColorcode.put("colorcode", colorcode);

		return productDao.selectProductStock(pidColorcode);
	}

	public int getTotalStock() {
		return productDao.countStock();
	}

	public List<StockList> getStockList(Pager pager) {
		return productDao.selectStockList(pager);
	}
	
	public void updateStock(Stock stock) {
		productDao.updateStock(stock);
	}
	
	public void removeProduct(String pid) {
		productDao.deleteByPid(pid);
	}
}
