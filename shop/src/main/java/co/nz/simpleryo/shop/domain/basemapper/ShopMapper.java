package co.nz.simpleryo.shop.domain.basemapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ShopMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
