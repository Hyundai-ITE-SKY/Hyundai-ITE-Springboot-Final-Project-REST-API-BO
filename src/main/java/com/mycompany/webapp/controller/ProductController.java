package com.mycompany.webapp.controller;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Exhibition;
import com.mycompany.webapp.dto.Exhibitions;
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
	@PostMapping("/create")
	public Product createProduct(@RequestBody Product product) {
		productService.createProduct(product); //product테이블에 insert
		
		List<Color> colors = product.getColors();
		for(Color color : colors) {
			productService.createColor(color);
			List<Stock> stocks = color.getStocks();
			
			for(Stock stock: stocks) {
				productService.createStock(stock);
			}
		}
		return product;
	}
	
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
	@PostMapping("/update")
	public Product updateProduct(@RequestBody Product product) {
		//내가 수정하려는 정보가 담긴 객체는 product product의 pno와 같은 애의 정보를 받아와야함(update할 때 pk가 필요하기 때문)
		log.info("실행");
		Product beforeproduct = productService.selectWithPno(product.getPno());
		beforeproduct.setColors(productService.getProductColors(beforeproduct.getPid()));
		for(Color color : beforeproduct.getColors()) {
			List<Stock> stocks = productService.selectStock(beforeproduct.getPid(), color.getCcolorcode());
			color.setStocks(stocks);
		}
		String beforePid = beforeproduct.getPid();
		productService.updateProduct(product, beforePid);
		
		List<Color> beforecolors = beforeproduct.getColors(); //이전의 색상들
		List<Color> aftercolors = product.getColors(); //바꾸려는 색상들
		
		//이전 색상들의 길이가 더 길다는 건 컬러를 삭제했다는 것 --> 길이가 같을 때까지만 update, 그 이상은 delete
		//이전 색상들의 길이가 더 짧다는 건 컬러를 추가했다는 것 --> 길이가 같을 때까지만 update, 그 이상은 insert
		if(beforecolors.size() >= aftercolors.size()) {
			//길이가 같을 때까지만 update
			for(int i=0; i<aftercolors.size(); i++) {
				String beforecolor = beforecolors.get(i).getCcolorcode();
				productService.updateProductColors(aftercolors.get(i), beforePid, beforecolor);
				
				//-------------------------test--------------------------------
				List<Stock> beforestocks = beforecolors.get(i).getStocks();
				List<Stock> afterstocks = aftercolors.get(i).getStocks();
				
				if(beforestocks.size() >= afterstocks.size()) {
					for(int j=0; j<afterstocks.size(); j++) {
						String beforesize = beforestocks.get(j).getSsize();
						productService.updateProductStocks(afterstocks.get(j), beforePid, beforecolor, beforesize);
					}
					
					if(beforestocks.size() > afterstocks.size()) {
						for(int j=afterstocks.size(); j<beforestocks.size(); j++) {
							String beforesize = beforestocks.get(j).getSsize();
							productService.removeProductStocks(beforePid, beforecolor, beforesize);
						}
					}
				}else {
					for(int j=0; j<beforestocks.size(); j++) {
						String beforesize = beforestocks.get(j).getCcolorcode();
						productService.updateProductStocks(afterstocks.get(j), beforePid, beforecolor, beforesize);
					}
					for(int j=beforestocks.size(); j<afterstocks.size(); j++) {
						productService.createStock(afterstocks.get(j));
					}
				}
				//-------------------------test 끝--------------------------------
			}
			//컬러 삭제
			if(beforecolors.size() > aftercolors.size()) {
				for(int i=aftercolors.size(); i<beforecolors.size(); i++) {
					String beforecolor = beforecolors.get(i).getCcolorcode();
					productService.removeProductColors(beforePid, beforecolor);
				}
			}
		}else {//컬러 추가시
			//길이가 같을 때까지만 update
			for(int i=0; i<beforecolors.size(); i++) {
				String beforecolor = beforecolors.get(i).getCcolorcode();
				productService.updateProductColors(aftercolors.get(i), beforePid, beforecolor);
				
				//-------------------------test--------------------------------
				List<Stock> beforestocks = beforecolors.get(i).getStocks();
				List<Stock> afterstocks = aftercolors.get(i).getStocks();
				
				if(beforestocks.size() >= afterstocks.size()) {
					for(int j=0; j<afterstocks.size(); j++) {
						String beforesize = beforestocks.get(j).getSsize();
						productService.updateProductStocks(afterstocks.get(j), beforePid, beforecolor, beforesize);
					}
					
					if(beforestocks.size() > afterstocks.size()) {
						for(int j=afterstocks.size(); j<beforestocks.size(); j++) {
							String beforesize = beforestocks.get(j).getSsize();
							productService.removeProductStocks(beforePid, beforecolor, beforesize);
						}
					}
				}else {
					for(int j=0; j<beforestocks.size(); j++) {
						String beforesize = beforestocks.get(j).getCcolorcode();
						productService.updateProductStocks(afterstocks.get(j), beforePid, beforecolor, beforesize);
					}
					for(int j=beforestocks.size(); j<afterstocks.size(); j++) {
						productService.createStock(afterstocks.get(j));
					}
				}
				//-------------------------test 끝--------------------------------
			}
			//그 이상은 insert
			for(int i=beforecolors.size(); i<aftercolors.size(); i++) {
				productService.createColor(aftercolors.get(i));
				
				List<Stock> afterstocks = aftercolors.get(i).getStocks();
				for(Stock afterstock : afterstocks) {
					productService.createStock(afterstock);
				}
			}
		}
		
		
		return beforeproduct;
	}
	
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
	
	@GetMapping("/exhibition/list")
	public Map<String, Object> getExhibition() {
		log.info("실행");
		Map<String, Object> map = new HashMap<>();
		
		map.put("exhibitions", productService.getExhibition());
		return map;
	}
	
	@PostMapping("exhibition/update")
	public void updateExhibition(@RequestBody Exhibitions exhibitions){
		for(Exhibition ex : exhibitions.getExhibitions()) {
			productService.updateExhibition(ex);
		}
	}
}