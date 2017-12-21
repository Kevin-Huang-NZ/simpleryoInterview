package co.nz.simpleryo.shop.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.nz.simpleryo.shop.domain.common.BaseService;
import co.nz.simpleryo.shop.domain.model.Goods;
import co.nz.simpleryo.shop.domain.service.GoodsService;

/**
 * Created by huanghao on 2016-8-16.
 */
@Service
public class GoodsServiceImpl extends BaseService<Goods> implements GoodsService {
    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    
}
