package com.mycompany.webapp.dao.productdb;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.Brand;
import com.mycompany.webapp.dto.CategoryLarge;
import com.mycompany.webapp.dto.CategoryMedium;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Exhibition;
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
	public int insertProduct(Product product);
	public int insertColor(Color color);
	public int insertStock(Stock stock);
	public int updateProduct(@Param("product") Product product, @Param("beforepid") String beforePid);
	public int updateProductColors(@Param("color") Color color, @Param("beforepid") String beforePid, @Param("beforecolor") String beforecolor);
	public int updateProductStocks(@Param("stock")Stock stock, @Param("pid")String beforePid, @Param("color")String beforecolor, @Param("size")String beforesize);
	public Product selectWithPno(int pno);
	public int deleteByPidColor(@Param("pid") String beforePid, @Param("color") String beforecolor);
	public int deleteByPidColorSize(@Param("pid") String beforePid, @Param("color") String beforecolor, @Param("size") String beforesize);
	public List<Exhibition> getExhibition();
	public void updateExhibition(Exhibition exhibition);
	public List<Brand> selectBrandList();
	public List<CategoryLarge> selectClarge();
	public List<CategoryMedium> selectCmedium(CategoryLarge clarge);
	public List<String> selectCsmall(@Param("clarge")String clarge, @Param("cmedium")String cmedium);
	public int insertProductSamePno(Product afterproduct);
}