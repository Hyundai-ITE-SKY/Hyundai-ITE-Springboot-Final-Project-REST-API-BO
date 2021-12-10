package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.productdb.ProductDao;
import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.CategoryLarge;
import com.mycompany.webapp.dto.CategoryMedium;
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

	public void createProduct(Product product) {
		productDao.insertProduct(product);
	}

	public void createColor(Color color) {
		productDao.insertColor(color);
	}

	public void createStock(Stock stock) {
		productDao.insertStock(stock);
	}

	public void updateProduct(Product product, String beforePid) {
		productDao.updateProduct(product, beforePid);
	}

	public void updateProductColors(Color color, String beforePid, String beforecolor) {
		productDao.updateProductColors(color, beforePid, beforecolor);
	}

	public void updateProductStocks(Stock stock, String beforePid, String beforecolor, String beforesize) {
		productDao.updateProductStocks(stock, beforePid, beforecolor, beforesize);
	}

	public Product selectWithPno(int pno) {
		return productDao.selectWithPno(pno);
	}

	public void removeProductColors(String beforePid, String beforecolor) {
		productDao.deleteByPidColor(beforePid, beforecolor);
	}

	public void removeProductStocks(String beforePid, String beforecolor, String beforesize) {
		productDao.deleteByPidColorSize(beforePid, beforecolor, beforesize);
	}

	public List<Brand> getBrandList() {
		return productDao.selectBrandList();
	}

	public List<CategoryLarge> getClarge() {
		return productDao.selectClarge();
	}

	public List<CategoryMedium> getCmedium(CategoryLarge clarge) {
		return productDao.selectCmedium(clarge);
	}

	public List<String> getCsmall(String clarge, String cmedium){
		return productDao.selectCsmall(clarge, cmedium);
	}
}
