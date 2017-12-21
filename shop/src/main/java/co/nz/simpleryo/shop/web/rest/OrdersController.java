package co.nz.simpleryo.shop.web.rest;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nz.simpleryo.shop.domain.dto.BuyGoodDto;
import co.nz.simpleryo.shop.domain.dto.OrderDto;
import co.nz.simpleryo.shop.domain.model.Goods;
import co.nz.simpleryo.shop.domain.service.GoodsService;
import co.nz.simpleryo.shop.domain.service.OrdersService;
import co.nz.simpleryo.shop.web.common.ResponseErrorEnum;
import co.nz.simpleryo.shop.web.common.ResponseResult;
import co.nz.simpleryo.shop.web.common.RestResultGenerator;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	Logger logger = LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private GoodsService goodsService;
	
	@GetMapping(value= {"/latest"})
	public ResponseResult<OrderDto> getLatestOrder() {
		OrderDto order = this.ordersService.selectLatestOrder();
		return RestResultGenerator.genResult(order, "");
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "")
	public ResponseResult save(@RequestBody List<BuyGoodDto> buyGoodDtos) {
		List<Goods> checkGoods = this.goodsService.selectByExample(null);

		for (int i = 0; i < buyGoodDtos.size(); i++) {
			BuyGoodDto buyGoodDto = buyGoodDtos.get(i);
			logger.info(buyGoodDto.getId() + "----------------" + buyGoodDto.getBuyAmount());
		}
		boolean noMore = false;
		String msg = "";
		
		for (int i = 0; i < checkGoods.size(); i++) {
			Goods oneGood = checkGoods.get(i);
			for (int j = 0; j < buyGoodDtos.size(); j++) {
				BuyGoodDto buyGoodDto = buyGoodDtos.get(j);
				if (oneGood.getId().equals(buyGoodDto.getId())) {
					if (oneGood.getAmount().compareTo(buyGoodDto.getBuyAmount()) < 0) {
						noMore = true;
						msg = oneGood.getName() + "被他人购买，存货不足。请刷新页面重新购买。";
					}
					break;
				}
			}
			
			if (noMore) {
				break;
			}
		}
		
		if (noMore) {
			return RestResultGenerator.genErrorResult(ResponseErrorEnum.NOT_ENOUGH_RESOURCE, msg);
		} else {
			this.ordersService.buy(buyGoodDtos);
			return RestResultGenerator.genResult(this.goodsService.selectByExample(null), "");
		}
	}
}
