package com.mycompany.webapp.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.OrderPerDay;
import com.mycompany.webapp.dto.OrderState;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.service.OrderService;
import com.mycompany.webapp.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dash")
@Slf4j
public class DashboardController {
	@Resource
	private ProductService productService;
	@Resource
	private OrderService orderService;
	
	//주문/배송
	@GetMapping("/orderState")
	public Map<String, Object> totalOrderState(){
		log.info("실행");
		Map<String, Object> map = new HashMap<String, Object>();
		OrderState ostate = orderService.getTotalOrderState();
		map.put("finPayCount", ostate.getFinPayCount());
		map.put("shippingCount", ostate.getShippingCount());
		map.put("finShippingCount", ostate.getFinShippingCount());
		map.put("confirmOrderCount", ostate.getConfirmOrderCount());
		return map;
	}
	
	//총 매출액
	@GetMapping("/totalprice")
	public Map<String, Object> totalPrice() {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", productService.getTotalPrice());
		return map;
	}
	
	//12월 매출액
	@GetMapping("/monthprice")
	public Map<String, Object> monthPrice() {
		log.info("실행");
		Map<String, Object> map = new HashMap<String, Object>();
		int monthPrice = 0;
		//orderlist에서 12월 주문 목록을 가져온다.
		List<OrderList> orderLists = orderService.getMonthOrderList();	
		
		//oid로 orderItem을 가져온다.
		for(OrderList order : orderLists) {
			List<OrderItem> orderItems = orderService.getOrderItem(order.getOid());
			for(OrderItem item : orderItems) {
				Product product = productService.getProduct(item.getPid());
				//price와 orderItem의 개수와 곱하여 더한다.
				monthPrice += product.getPprice() * item.getOamount();
			}
		}
		map.put("value", monthPrice);
		return map;
	}
	
	//금일 매출액
	@GetMapping("/todayprice")
	public Map<String, Object> todayPrice() {
		log.info("실행");
		Map<String, Object> map = new HashMap<String, Object>();
		int todyaPrice = 0;
		//orderlist에서 당일 주문 목록을 가져온다.
		List<OrderList> orderLists = orderService.getTodayOrderList();
		
		//oid로 orderItem을 가져온다.
		for(OrderList order : orderLists) {
			List<OrderItem> orderItems = orderService.getOrderItem(order.getOid());
			for(OrderItem item : orderItems) {
				Product product = productService.getProduct(item.getPid());
				//price와 orderItem의 개수와 곱하여 더한다.
				todyaPrice += product.getPprice() * item.getOamount();
			}
		}
		map.put("value", todyaPrice);
		return map;
	}
	
	//날짜별 주문수
	@GetMapping("/order/day")
	public Map<String, Object> orderPerDay(){
		log.info("실행");
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderPerDay> orderPerDay = orderService.getOrderPerDay();
		//OrderPerDays Dto로 받는다.
		map.put("orderPerDays", orderPerDay);
		return map;
	}
	
	//날짜별 주문자수
	@GetMapping("/order/dayuser")
	public Map<String, Object> orderPerDayWithPerson(){
		log.info("실행");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderPerDays", orderService.orderPerDayWithPerson());
		return map;
	}
	
	//월별, 카테고리별 매출
	@GetMapping("/monthtotalprice")
	public String orderMonthCategoryTotalprice(){
		String result = "";
		Map<Integer, Map<String, Integer>> monthMap = new HashMap<>();
		
		//orderlist에서 월별 orderlist(oid) 얻는다.
		for(int i=0; i<3; i++) {
			int month = LocalDate.now().getMonth().getValue();
			Map<String, Integer> categoryMap = new HashMap<>();
			monthMap.put(month-i, categoryMap);
			//(현재 월 - i)월의 orderlist를 얻는다.
			List<OrderList> orderListPerMonth = orderService.getOrderListByMonth(month-i);
			
			//oid를 통해서 orderItem의 pid를 얻는다.
			for(OrderList order : orderListPerMonth) {
				List<OrderItem> orderItems = orderService.getOrderItem(order.getOid());
				
				//pid를 통해서 product의 category와 price를 얻는다.
				for(OrderItem item : orderItems) {
					Product product = productService.getProduct(item.getPid());
					
					monthMap.get(month-i).put(product.getClarge(), categoryMap.getOrDefault(product.getClarge(), 0) + (product.getPprice()*item.getOamount()));
				}
			}
			
			monthMap.get(month-i).put("WOMEN", categoryMap.getOrDefault("WOMEN", 0));
			monthMap.get(month-i).put("MEN", categoryMap.getOrDefault("MEN", 0));
			monthMap.get(month-i).put("KIDS", categoryMap.getOrDefault("KIDS", 0));
			monthMap.get(month-i).put("LIFESTYLE", categoryMap.getOrDefault("LIFESTYLE", 0));
		}
		result = monthMap.toString();
		return result;
	}
}
