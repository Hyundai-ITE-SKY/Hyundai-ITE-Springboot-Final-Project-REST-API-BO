package com.mycompany.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.memberdb.MemberDao;
import com.mycompany.webapp.dao.orderdb.OrderDao;
import com.mycompany.webapp.dao.productdb.ProductDao;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.OrderItem;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Stock;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	@Resource
	private OrderDao orderDao;

	@Resource
	private MemberDao memberDao;

	@Resource
	private ProductDao productDao;

	public List<OrderList> getOrderLists(Pager pager) {
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

	public int createOrderItem(OrderItem orderItem) {
		return orderDao.createOrderItem(orderItem);
	}

	@Transactional
	public String createOrderList(OrderList orderList) {
		try {
			orderDao.createOrderList(orderList);
			String oid = String.valueOf(orderDao.getCurrentOid());
			return oid;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "fail";
	}

	@Transactional
	public String createRandomOrder() {
		try {
			String[] memberIds = { "admin1", "admin2", "admin3", "mid1", "mid2", "mid3" };
			int rand = (int) (Math.random() * 5);

			// 주문하는 사람
			Member member = memberDao.selectByMid(memberIds[rand]);
			log.info(member.toString());

			// 주문
			OrderList orderlist = new OrderList();
			orderlist.setMid(member.getMid());
			orderlist.setOzipcode(member.getMzipcode());
			orderlist.setOaddress1(member.getMaddress1());
			orderlist.setOaddress2(member.getMaddress2());
			orderlist.setOreceiver(member.getMname());
			orderlist.setOtel(member.getMtel());
			orderlist.setOusedmileage(0);
			orderlist.setOusedcoupon(0);
			orderlist.setOpayment(0);
			orderlist.setOstatus(0);
			orderlist.setBackDate((int) (Math.random() * 60));

			int n = (int) (Math.random() * 3) + 1;
			int sumVal = 0;

			List<Product> products = new ArrayList<Product>();

			for (int i = 0; i < n; i++) {
				int pno = (int) (Math.random() * 10000) + 1;
				Product product = productDao.selectWithPno(pno);
				sumVal += product.getPprice();
				products.add(product);
			}

			orderlist.setOdiscounted((int) (sumVal * 0.2));
			orderlist.setOtotal((int) (sumVal * 0.8));

			orderDao.createOrderList(orderlist);
			String oid = String.valueOf(orderDao.getCurrentOid());
			log.info("oid: " + oid);

			for (Product product : products) {
				product.setColors(productDao.selectProductColors(product.getPid()));
				HashMap<String, Object> pidColorcode = new HashMap<String, Object>();
				pidColorcode.put("pid", product.getPid());

				for (Color color : product.getColors()) {
					pidColorcode.put("colorcode", color.getCcolorcode());
					List<Stock> stocks = productDao.selectProductStock(pidColorcode);
					color.setStocks(stocks);
				}

				OrderItem orderItem = new OrderItem();

				orderItem.setOid(oid);
				orderItem.setPid(product.getPid());
				orderItem.setCcolorcode(product.getColors().get(0).getCcolorcode());
				orderItem.setPsize(product.getColors().get(0).getStocks().get(0).getSsize());
				orderItem.setOamount(1);

				log.info("orderItem: " + orderItem.toString());
				createOrderItem(orderItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

		return "success";
	}
}
