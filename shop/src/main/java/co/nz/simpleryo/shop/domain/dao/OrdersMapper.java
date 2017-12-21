package co.nz.simpleryo.shop.domain.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import co.nz.simpleryo.shop.domain.basemapper.ShopMapper;
import co.nz.simpleryo.shop.domain.dto.OrderDto;
import co.nz.simpleryo.shop.domain.model.Orders;

public interface OrdersMapper extends ShopMapper<Orders> {
	
	@Select(value="select t1.id, t1.buy_date, t2.id as detail_id,t3.name, t2.buy_amount"
			+ " from (select max(id) as maxId from orders) v1, orders t1, order_detail t2, goods t3"
			+ " where v1.maxId = t1.id and v1.maxId = t2.order_id and t2.good_id = t3.id"
			)
	@ResultMap("co.nz.simpleryo.shop.domain.dao.OrdersMapper.OrderDtoMap")
	OrderDto selectLatestOrder();
}