package com.microservice.productservice.product_service.mapper;

import com.microservice.productservice.product_service.dto.discount.DiscountDto;
import com.microservice.productservice.product_service.model.DisCountModel;
import com.microservice.productservice.product_service.model.ProductModel;
import com.microservice.productservice.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountMapper {
    private final ProductRepository productRepository;

    public List<ProductModel> getAllProductsByIds(List<Long>ids){
       return productRepository.findAllById(ids);
    }

    public DisCountModel toDiscountModel(DiscountDto data){
        DisCountModel res=new DisCountModel();
        System.out.println("the coupon image and the coupon type "+data.getCouponImage() + data.getDiscountType());
        res.setDiscountPercentage(data.getDiscountPercentage());
        res.setCouponDescription(data.getCouponDescription());
        res.setDiscountType(data.getDiscountType());
        res.setCouponName(data.getCouponName());
        res.setValidFrom(data.getValidFrom());
        res.setValidTill(data.getValidTill());
        res.setCouponImage(data.getCouponImage());
        res.setAllProducts(getAllProductsByIds(data.getAllProductsId()));
        return res;
    }


    public DiscountDto toDiscountDto(DisCountModel  disCountModel){
        DiscountDto res=new DiscountDto();
        res.setId(disCountModel.getId());
        res.setDiscountPercentage(disCountModel.getDiscountPercentage());
        res.setCouponName(disCountModel.getCouponName());
        res.setCouponDescription(disCountModel.getCouponDescription());
        res.setValidTill(disCountModel.getValidTill());
        res.setValidFrom(disCountModel.getValidFrom());
        res.setDiscountType(disCountModel.getDiscountType());
        res.setCouponImage(disCountModel.getCouponImage());
        return res;
    }
}
