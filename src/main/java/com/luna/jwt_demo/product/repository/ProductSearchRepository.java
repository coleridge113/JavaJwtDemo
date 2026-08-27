package com.luna.jwt_demo.product.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.luna.jwt_demo.product.model.ProductDocument;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    List<ProductDocument> findByNameContaining(String name);

    List<ProductDocument> findByNameStartingWith(String prefix);

    Page<ProductDocument> findByNameContaining(String keyword, Pageable pageable);

}
