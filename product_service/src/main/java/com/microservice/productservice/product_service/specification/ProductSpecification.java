package com.microservice.productservice.product_service.specification;

import com.microservice.productservice.product_service.dto.filter.ProductFilterResponseDto;
import com.microservice.productservice.product_service.model.ProductModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<ProductModel> filter(ProductFilterResponseDto f) {
        return (root, query, cb) -> {

            List<Predicate> p = new ArrayList<>();

            if (f.getCategories() != null && !f.getCategories().isEmpty())
                p.add(root.get("category").get("categoryName").in(f.getCategories()));

            if (f.getBrands() != null && !f.getBrands().isEmpty())
                p.add(root.get("brandName").in(f.getBrands()));

            if (f.getFabrics() != null && !f.getFabrics().isEmpty())
                p.add(root.get("fabric").in(f.getFabrics()));

            if (f.getFits() != null && !f.getFits().isEmpty())
                p.add(root.get("fit").in(f.getFits()));

            if (f.getSleeveTypes() != null && !f.getSleeveTypes().isEmpty())
                p.add(root.get("sleeveType").in(f.getSleeveTypes()));

            if (f.getNeckTypes() != null && !f.getNeckTypes().isEmpty())
                p.add(root.get("neckType").in(f.getNeckTypes()));

            if (f.getPatterns() != null && !f.getPatterns().isEmpty())
                p.add(root.get("pattern").in(f.getPatterns()));

            if (f.getOccasions() != null && !f.getOccasions().isEmpty())
                p.add(root.get("occasion").in(f.getOccasions()));

            if (f.getSeasons() != null && !f.getSeasons().isEmpty())
                p.add(root.get("season").in(f.getSeasons()));

            // 🔥 PRICE FILTER
            if (f.getMinPrice() != null)
                p.add(cb.greaterThanOrEqualTo(root.get("productPrice"), f.getMinPrice()));

            if (f.getMaxPrice() != null)
                p.add(cb.lessThanOrEqualTo(root.get("productPrice"), f.getMaxPrice()));

            return cb.and(p.toArray(new Predicate[0]));
        };
    }
}
