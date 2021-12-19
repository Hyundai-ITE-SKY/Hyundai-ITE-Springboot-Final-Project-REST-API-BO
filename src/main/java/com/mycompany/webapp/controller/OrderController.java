package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public Map<String, Object> getOrderList(@RequestParam("pageNo") int pageNo) {
		log.info("실행");
		int totalRows = orderService.getTotalOrderList();
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		List<OrderList> orderLists = orderService.getOrderLists(pager);

		Map<String, Object> orderListMap = new HashMap<>();
		orderListMap.put("orderlists", orderLists);

		return orderListMap;
	}

	@RequestMapping("/detail")
	public Map<String, Object> getOrderDetail(@RequestParam("oid") String oid) {
		log.info("실행");

		OrderList orderList = orderService.getOrderList(oid);
		List<OrderItem> orderItem = orderService.getOrderItem(oid);

		Map<String, Object> orderDetailMap = new HashMap<>();
		orderDetailMap.put("orderlist", orderList);
		orderDetailMap.put("orderitem", orderItem);

		return orderDetailMap;
	}

	@GetMapping("/totalrows")
	public Map<String, Object> getTotalRows() {
		// Integer totalRows = orderService.getTotalOrderList();
		Map<String, Object> map = new HashMap<>();

		map.put("value", orderService.getTotalOrderList());

		log.info(map + "");
		return map;
	}

	@GetMapping("/random/create")
	public Map<String, Object> createRandomOrder(int count) {
		Map<String, Object> map = new HashMap<>();

		for (int i = 0; i < count; i++) {
			if (orderService.createRandomOrder().equals("fail")) {
				map.put("result", "fail");
				return map;
			}
		}

		map.put("result", "success");
		return map;
	}
	
	@GetMapping("/getSearchList")
	public Map<String, Object> getSearchList(@RequestParam String type, @RequestParam String keyword, @RequestParam int pageNo, @RequestParam String startdate, @RequestParam String enddate){
		log.info("실행");
		log.info(type);
		log.info(keyword);
		log.info(startdate);
		log.info(enddate);
		
		int totalRows = orderService.getSearchListCount(type, keyword, startdate, enddate);
		Pager pager = new Pager(12, 5, totalRows, pageNo);
		log.info(totalRows+"");
		List<OrderList> orderlist = orderService.getSearchList(type, keyword, pager, startdate, enddate);
		log.info(orderlist+"");
		Map<String, Object> orderListMap = new HashMap<>();
		orderListMap.put("orderlists", orderlist);
		orderListMap.put("totalRows", totalRows);
		
		return orderListMap;
	}
}
