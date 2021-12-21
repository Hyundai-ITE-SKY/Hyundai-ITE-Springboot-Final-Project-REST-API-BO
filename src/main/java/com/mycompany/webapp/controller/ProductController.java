package com.mycompany.webapp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.CategoryLarge;
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
	public Map<String, Object> createProduct(@RequestBody Product product) {		
		productService.createProduct(product); //product테이블에 insert
		
		Map<String, Object> map = new HashMap<>();
		map.put("product", product);
		
		return map;
	}
	
	//상품 상세 조회
	@GetMapping("/{pid}")
	public Map<String, Object> getProduct(@PathVariable String pid) {
		log.info("실행");
		Map<String, Object> map = productService.selectWithPid(pid);
	
		return map;
	}
	
	@PostMapping("/update")
	public Map<String, Object> updateProduct(@RequestBody Product afterproduct) {
		log.info("실행");
		productService.updateProduct(afterproduct);
		
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
	public Map<String, Object> getStockList(@PathVariable int pageNo){
		log.info("실행");
		int totalRows = productService.getTotalStock();
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		List<StockList> stockList = productService.getStockList(pager);
		
		Map<String, Object> stockListMap = new HashMap<String, Object>();
		stockListMap.put("stockLists", stockList);
		log.info("stockList : "+stockList);
		return stockListMap;
	}
	
	//재고수정
	@PostMapping("/stock/update")
	public Map<String, Object> updateStock(Stock stock) {
		log.info("실행");
		productService.updateStock(stock);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stock", stock);
		return map;
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


 //전체 카테고리 조회
	@GetMapping("/category")
	public Map<String, Object> getCategoryList() {
		log.info("실행");
		
		List<CategoryLarge> clarges = productService.getCategoryList();
		
		Map<String, Object> map = new HashMap<>();
		map.put("category", clarges);
		
		return map;
	}
	
	@GetMapping("/allbrand")
	public Map<String, Object> getBrandList(){
		log.info("실행");
		Map<String, Object> map = new HashMap<>();
		List<Brand> brandList = productService.getBrandList();
		map.put("brands", brandList);
		
		return map;
	}
	
	@GetMapping("/stock/totalrows")
	public Map<String, Object> getTotalRows(){
		Map<String, Object> map = new HashMap<>();

		map.put("value", productService.getTotalStock());
		
		log.info(map+"");
		return map;
	}

	@GetMapping("/getSearchList")
	public Map<String, Object> getSearchList(@RequestParam("type") String type,
											 @RequestParam("w") String keyword,
											 @RequestParam("pageNo") int pageNo,
											 @RequestParam("clarge") String clarge,
											 @RequestParam("cmedium") String cmedium,
											 @RequestParam("csmall") String csmall
											 ){
		log.info("실행");
		Product product = new Product();
		product.setClarge(clarge);
		product.setCmedium(cmedium);
		product.setCsmall(csmall);
		product.setType(type);
		product.setKeyword(keyword);
		
		int totalRows = productService.getSearchListCount(product);
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		
		List<Product> productList = productService.getSearchList(product, pager);
		Map<String, Object> map = new HashMap<>();
		map.put("products", productList);
		map.put("totalRows", totalRows);

		return map;
	}
	
	@GetMapping("/getSearchStockList")
	public Map<String, Object> getSearchStockList(@RequestParam("type") String type,
												  @RequestParam("w") String keyword,
												  @RequestParam("pageNo") int pageNo)
	{
		log.info("실행");
		int totalRows = productService.getStockSearchListCount(type, keyword);
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		
		List<StockList> stockList = productService.getStockSearchList(type,keyword, pager);
		Map<String, Object> map = new HashMap<>();
		map.put("stockLists", stockList);
		map.put("totalRows", totalRows);
		return map;
	}
	
}
