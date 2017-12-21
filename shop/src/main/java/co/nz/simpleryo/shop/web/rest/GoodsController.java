package co.nz.simpleryo.shop.web.rest;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.nz.simpleryo.shop.domain.model.Goods;
import co.nz.simpleryo.shop.domain.service.GoodsService;
import co.nz.simpleryo.shop.web.common.ResponseResult;
import co.nz.simpleryo.shop.web.common.RestResultGenerator;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
	Logger logger = LoggerFactory.getLogger(GoodsController.class);

	@Autowired
	private GoodsService goodsService;
	
	@GetMapping(value= {"/"})
	public ResponseResult<List<Goods>> getAllGoods() {
		List<Goods> goods = this.goodsService.selectByExample(null);
		return RestResultGenerator.genResult(goods, "");
	}

}
