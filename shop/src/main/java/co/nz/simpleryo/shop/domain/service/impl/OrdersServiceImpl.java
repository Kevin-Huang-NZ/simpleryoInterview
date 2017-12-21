package co.nz.simpleryo.shop.domain.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.nz.simpleryo.shop.domain.common.BaseService;
import co.nz.simpleryo.shop.domain.dao.GoodsMapper;
import co.nz.simpleryo.shop.domain.dao.OrderDetailMapper;
import co.nz.simpleryo.shop.domain.dao.OrdersMapper;
import co.nz.simpleryo.shop.domain.dto.BuyGoodDto;
import co.nz.simpleryo.shop.domain.dto.OrderDto;
import co.nz.simpleryo.shop.domain.model.Goods;
import co.nz.simpleryo.shop.domain.model.OrderDetail;
import co.nz.simpleryo.shop.domain.model.Orders;
import co.nz.simpleryo.shop.domain.service.OrdersService;
import co.nz.simpleryo.shop.util.DateUtil;

/**
 * Created by huanghao on 2016-8-16.
 */
@Service
public class OrdersServiceImpl extends BaseService<Orders> implements OrdersService {
	private static Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);
    
    @Autowired
    private OrdersMapper ordersMapper;
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    @Autowired
    private GoodsMapper goodsMapper;

	@Override
	public OrderDto selectLatestOrder() {
		return ordersMapper.selectLatestOrder();
	}

	@Override
	public int buy(List<BuyGoodDto> buyGoodDto) {
//		Long idBase = System.currentTimeMillis();
		for(int i = 0; i < buyGoodDto.size(); i++) {
			BuyGoodDto oneBuy = buyGoodDto.get(i);
			Goods goods = goodsMapper.selectByPrimaryKey(oneBuy.getId());
			Integer newAmount = goods.getAmount() - oneBuy.getBuyAmount();
			goods.setAmount(newAmount);
			goodsMapper.updateByPrimaryKeySelective(goods);
		}
		
		String buyDate = DateUtil.getYYYYMMDDHHMISS();
		Orders orders = new Orders();
//		idBase = idBase + 1;
//		orders.setId(idBase);
		orders.setBuyDate(buyDate);
//		logger.info(orders.getBuyDate());
		ordersMapper.insertUseGeneratedKeys(orders);
		
		logger.info("新的订单id是：" +  orders.getId());
		
		for(int i = 0; i < buyGoodDto.size(); i++) {
			BuyGoodDto oneBuy = buyGoodDto.get(i);
			OrderDetail orderDetail = new OrderDetail();
//			idBase = idBase + 1;
//			orderDetail.setId(idBase);
			orderDetail.setOrderId(orders.getId());
			orderDetail.setGoodId(oneBuy.getId());
			orderDetail.setBuyAmount(oneBuy.getBuyAmount());
			orderDetailMapper.insertUseGeneratedKeys(orderDetail);
		}
		
		return buyGoodDto.size();
	}

    
}
