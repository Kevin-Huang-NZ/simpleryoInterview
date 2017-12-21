package co.nz.simpleryo.shop.domain.service;

import java.util.List;

import co.nz.simpleryo.shop.domain.common.IService;
import co.nz.simpleryo.shop.domain.dto.BuyGoodDto;
import co.nz.simpleryo.shop.domain.dto.OrderDto;
import co.nz.simpleryo.shop.domain.model.Orders;

/**
 * Created by huanghao on 2016-8-16.
 */
public interface OrdersService extends IService<Orders> {
	OrderDto selectLatestOrder();
	
	int buy(List<BuyGoodDto> buyGoodDto);
}