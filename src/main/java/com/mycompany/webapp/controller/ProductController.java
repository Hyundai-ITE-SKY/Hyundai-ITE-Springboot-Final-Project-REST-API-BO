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

import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.CategoryLarge;
import com.mycompany.webapp.dto.CategoryMedium;
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
	public Map<String, Object> getProductList(@PathVariable int pageNo) {
		log.info("실행");
		int totalRows = productService.getTotalProduct();
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		
		List<Product> productList = productService.getProductList(pager);
		Map<String, Object> map = new HashMap<>();
		map.put("products", productList);
		map.put("totalRows", totalRows);
		return map;
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
	public Map<String, Object> getProduct(@PathVariable String pid) {
		log.info("실행");
		Product product = productService.selectWithPid(pid);
		product.setColors(productService.getProductColors(pid));
		
		for (Color color : product.getColors()) {
			List<Stock> stocks = productService.selectStock(pid, color.getCcolorcode());
			color.setStocks(stocks);
		}
		
		Map<String, Object> map = new HashMap<>();
		List<Brand> brandList = productService.getBrandList();
		map.put("product", product);
		map.put("brands", brandList);
		return map;
	}
	
	@PostMapping("/update")
	public Map<String, Object> updateProduct(@RequestBody Product afterproduct) {
		//afterproduct와 같은 pno를 갖고 있는 놈의 pid를 찾아서 정보를 삭제한다.
		log.info("실행");
		Product beforeproduct = productService.selectWithPno(afterproduct.getPno());
		String beforePid = beforeproduct.getPid();
		productService.removeProduct(beforePid);
		
		productService.createProduct(afterproduct); //product테이블에 insert
		
		List<Color> colors = afterproduct.getColors();
		for(Color color : colors) {
			productService.createColor(color);
			List<Stock> stocks = color.getStocks();
			
			for(Stock stock: stocks) {
				productService.createStock(stock);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("product", afterproduct);
		return map;
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

 //전체 카테고리 조회
	@GetMapping("/category")
	public Map<String, Object> getCategoryList() {
		log.info("실행");
		
		List<CategoryLarge> clarges = productService.getClarge();
		
		for(CategoryLarge clarge: clarges) {
			List<CategoryMedium> cmediums =productService.getCmedium(clarge);
			
			for(CategoryMedium cmedium : cmediums) {
				List<String> csmalls = productService.getCsmall(clarge.getClarge(), cmedium.getCmedium());
				cmedium.setCsmall(csmalls);
				cmedium.setCmedium(cmedium.getCmedium());
			}
			clarge.setCmedium(cmediums);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("category", clarges);
		
		return map;
	}
	
}
