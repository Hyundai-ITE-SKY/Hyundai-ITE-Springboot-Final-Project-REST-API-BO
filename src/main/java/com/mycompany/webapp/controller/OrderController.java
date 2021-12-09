package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.IntegerVariable;
import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	@GetMapping("/list")
	public Map<String, Object> getOrderList(@RequestParam("pageNo") int pageNo){
		log.info("실행");
		int totalRows = orderService.getTotalOrderList();
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		List<OrderList> orderLists = orderService.getOrderLists(pager);
		
		Map<String, Object> orderListMap = new HashMap<>();
		orderListMap.put("orderlists", orderLists);
		
		return orderListMap;
	}
	
	@RequestMapping("/detail")
	public Map<String, Object> getOrderDetail(@RequestParam("oid") String oid){
		log.info("실행");
		
		OrderList orderList = orderService.getOrderList(oid);
		List<OrderItem> orderItem = orderService.getOrderItem(oid);
		
		Map<String, Object> orderDetailMap = new HashMap<>();
		orderDetailMap.put("orderlist", orderList);
		orderDetailMap.put("orderitem", orderItem);

		return orderDetailMap;
	}
	
	@GetMapping("/totalrows")
	public Map<String, Object> getTotalRows(){
		//Integer totalRows = orderService.getTotalOrderList();
		Map<String, Object> map = new HashMap<>();

		map.put("value", orderService.getTotalOrderList());
		
		log.info(map+"");
		return map;
	}
	
}
