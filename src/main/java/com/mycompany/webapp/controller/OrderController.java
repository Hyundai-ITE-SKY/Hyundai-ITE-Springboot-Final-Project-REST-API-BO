package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Order;
import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	@GetMapping("/list")
	public List<OrderList> getOrderList(){
		log.info("실행");
		
		return orderService.getOrderLists();
	}
	
	@GetMapping("/detail")
	public Order getOrderDetail(String oid){
		log.info("실행");
		OrderList orderList = orderService.getOrderList(oid);
		List<OrderItem> orderItem = orderService.getOrderItem(oid);
		
		Order order = new Order();
		order.setOrderlist(orderList);
		order.setOrderitem(orderItem);
		
		return order;
	}
	
}
