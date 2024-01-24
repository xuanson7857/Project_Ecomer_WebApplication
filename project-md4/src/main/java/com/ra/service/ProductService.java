package com.ra.service;

import com.ra.dto.request.ProductDTO;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductService {
    Boolean createProduct(ProductDTO productDTO);

    Boolean updateProduct(Product product,Integer id);

    Product findByIdPro(Integer id);

    List<Product> listProduct();
    List<Product> Pagination(Integer page, Integer limit);
    List<Product> listProductByCategory(Integer id);
    List<Product> listProductByRequest(Integer id);
    List<Product> listAllProductByCategory(Integer id);

    void deleteProduct(Integer id);
}
