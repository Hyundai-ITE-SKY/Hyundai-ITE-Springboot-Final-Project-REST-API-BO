package com.mycompany.webapp.dao.orderdb;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;

@Mapper
public interface OrderDao {
	public List<OrderList> getOrderLists();
	public OrderList getOrderList(String oid);
	public List<OrderItem> getOrderItem(String oid);
}
