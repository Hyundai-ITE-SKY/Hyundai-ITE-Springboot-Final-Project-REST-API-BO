package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.orderdb.OrderDao;
import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	@Resource
	private OrderDao orderDao;
	
	public List<OrderList> getOrderLists(Pager pager){
		log.info("실행");
		return orderDao.getOrderLists(pager);
	}
	
	public OrderList getOrderList(String oid) {
		log.info("실행");
		return orderDao.getOrderList(oid);
	}

	public List<OrderItem> getOrderItem(String oid) {
		log.info("실행");
		return orderDao.getOrderItem(oid);
	}
	
	public int getTotalOrderList() {
		log.info("실행");
		return orderDao.getTotalOrderList();
	}
}
