package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;
import com.mycompany.webapp.dto.StockList;
import com.mycompany.webapp.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/product")
@RestController
@Slf4j
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	//상품 목록 불러오기
	@GetMapping("/list/{pageNo}")
	public List<Product> getProductList(@PathVariable int pageNo) {
		log.info("실행");
		int totalRows = productService.getTotalProduct();
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		
		List<Product> productList = productService.getProductList(pager);
		return productList;
	}
	
	//상품 등록하기
	//product, color, stock테이블에 순차적으로 insert
	
	//상품 상세 조회
	@GetMapping("/{pid}")
	public Product getProduct(@PathVariable String pid) {
		log.info("실행");
		Product product = productService.selectWithPid(pid);
		product.setColors(productService.getProductColors(pid));
		
		for (Color color : product.getColors()) {
			List<Stock> stocks = productService.selectStock(pid, color.getCcolorcode());
			color.setStocks(stocks);
		}
		
		return product;
	}
	
	//상품 수정하기
//	@PostMapping("/update/{pid}")
//	public Product updateProduct(@PathVariable String pid) {
//		log.info("실행");
//		
//	}
	//상품 삭제하기
	@DeleteMapping("/{pid}")
	public Map<String, String> deleteProduct(@PathVariable String pid) {
		log.info("실행");
		productService.removeProduct(pid);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	//상품 재고 목록 불러오기
	@GetMapping("/stock/list/{pageNo}")
	public List<StockList> getStockList(@PathVariable int pageNo){
		log.info("실행");
		int totalRows = productService.getTotalStock();
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		List<StockList> stockList = productService.getStockList(pager);
		return stockList;
	}
	
	//재고수정
	@PostMapping("/stock/update")
	public Stock updateStock(Stock stock) {
		log.info("실행");
		productService.updateStock(stock);
		return stock;
	}
}
