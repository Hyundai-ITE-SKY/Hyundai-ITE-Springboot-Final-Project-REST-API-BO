package com.mycompany.webapp.dao.orderdb;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.OrderPerDay;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface OrderDao {
	public List<OrderList> getOrderLists(Pager pager);
	public OrderList getOrderList(String oid);
	public List<OrderItem> getOrderItem(String oid);
	public int getTotalOrderList();
	public int createOrderItem(OrderItem orderItem);
	public int getCurrentOid();
	public int createOrderList(OrderList orderList);
	public int selectCountSearchList(@Param("type") String type, @Param("keyword") String keyword, @Param("startdate") String startdate, @Param("enddate") String enddate);
	public List<OrderList> selectSearchList(@Param("type") String type,
											@Param("keyword") String keyword,
											@Param("pager") Pager pager,
											@Param("startdate") String startdate,
											@Param("enddate") String enddate);
	public List<OrderList> getMonthOrderList();
	public List<OrderList> getTodayOrderList();
	public List<OrderPerDay> getOrderPerDay();
	public List<OrderPerDay> orderPerDayWithPerson();
	public List<OrderList> getOrderListByMonth(int month);
	public int selectCountFinPay();
	public int selectCountShipping();
	public int selectCountFinShipping();
	public int selectCountConfirmOrder();
}
