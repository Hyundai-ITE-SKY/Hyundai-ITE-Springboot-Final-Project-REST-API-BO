package com.mycompany.webapp.dao.orderdb;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface OrderDao {
	public List<OrderList> getOrderLists(Pager pager);
	public OrderList getOrderList(String oid);
	public List<OrderItem> getOrderItem(String oid);
	public int getTotalOrderList();
}