package com.mycompany.webapp.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderList {
	private String oid;
	private String mid;
	private String ozipcode;
	private String oaddress1;
	private String oaddress2;
	private Date odate;
	private String oreceiver;
	private String otel;
	private int ousedmileage;
	private int ousedcoupon;
	private int opayment;
	private int ostatus;
	private Date oaccountdeadline;
	private int odiscounted;
	private int ototal;
	private List<OrderItem> orderitem;
	
	//주문 상품 개수 추가
	private int countitem;
	// 랜덤 추가용 변수
	private int backDate;
}
