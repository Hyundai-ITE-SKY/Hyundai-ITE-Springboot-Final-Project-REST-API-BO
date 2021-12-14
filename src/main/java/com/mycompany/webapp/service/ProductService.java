package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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

	@Transactional
	public Map<String, Object> selectWithPid(String pid) {
		Map<String, Object> map = new HashMap<>();
		try {
			Product product = productDao.selectProductWithPid(pid);
			product.setColors(getProductColors(pid));

			for (Color color : product.getColors()) {
				List<Stock> stocks = selectStock(pid, color.getCcolorcode());
				color.setStocks(stocks);
			}

			List<Brand> brandList = getBrandList();
			map.put("product", product);
			map.put("brands", brandList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
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

	@Transactional
	public void createProduct(Product product) {
		try {
			productDao.insertProduct(product);

			List<Color> colors = product.getColors();

			for (Color color : colors) {
				createColor(color);
				List<Stock> stocks = color.getStocks();

				for (Stock stock : stocks) {
					createStock(stock);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createColor(Color color) {
		productDao.insertColor(color);
	}

	public void createStock(Stock stock) {
		productDao.insertStock(stock);
	}

	@Transactional
	public void updateProduct(Product afterproduct) {
		try {
			Product beforeproduct = selectWithPno(afterproduct.getPno());
			String beforePid = beforeproduct.getPid();
			removeProduct(beforePid);

			createProduct(afterproduct); // product테이블에 insert

			List<Color> colors = afterproduct.getColors();
			for (Color color : colors) {
				createColor(color);
				List<Stock> stocks = color.getStocks();

				for (Stock stock : stocks) {
					createStock(stock);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Transactional
	public List<CategoryLarge> getCategoryList() {
		List<CategoryLarge> clarges = null;
		try {
			clarges = productDao.selectClarge();

			for (CategoryLarge clarge : clarges) {
				List<CategoryMedium> cmediums = getCmedium(clarge);

				for (CategoryMedium cmedium : cmediums) {
					List<String> csmalls = getCsmall(clarge.getClarge(), cmedium.getCmedium());
					cmedium.setCsmall(csmalls);
					cmedium.setCmedium(cmedium.getCmedium());
				}
				clarge.setCmedium(cmediums);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clarges;
	}

	public List<CategoryMedium> getCmedium(CategoryLarge clarge) {
		return productDao.selectCmedium(clarge);
	}

	public List<String> getCsmall(String clarge, String cmedium) {
		return productDao.selectCsmall(clarge, cmedium);
	}
}
