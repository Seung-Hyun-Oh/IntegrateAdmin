package com.concentrix.lgintegratedadmin.mapper;

import com.concentrix.lgintegratedadmin.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    void insertProduct(Product product);
    void upsertProduct(Product product);
    void bulkInsertProducts(@Param("products") List<Product> products);
}
