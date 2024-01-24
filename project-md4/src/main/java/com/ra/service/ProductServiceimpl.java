package com.ra.service;

import com.ra.dto.request.ProductDTO;
import com.ra.model.dao.ProductDAO;
import com.ra.model.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceimpl implements ProductService{
    @Autowired
    private ProductDAO productDAO;

    @Override
    public Boolean createProduct(ProductDTO productDTO) {
        ModelMapper modelMapper=new ModelMapper();
        Product product = modelMapper.map(productDTO,Product.class);
        product.setImage(productDTO.getImage().getOriginalFilename());
        return productDAO.createProduct(product);
    }

    @Override
    public Boolean updateProduct(Product product, Integer id) {
        return productDAO.updateProduct(product,id);
    }

    @Override
    public Product findByIdPro(Integer id) {
        return productDAO.findByIdPro(id);
    }

    @Override
    public List<Product> listProduct() {
        return productDAO.listProduct();
    }

    @Override
    public List<Product> Pagination(Integer page, Integer limit) {
        return productDAO.Pagination(page, limit);
    }

    @Override
    public List<Product> listProductByCategory(Integer id) {
        return productDAO.listProductByCategory(id);
    }
    @Override
    public List<Product> listProductByRequest(Integer id) {
        return productDAO.listProductByRequest(id);
    }
    @Override
    public List<Product> listAllProductByCategory(Integer id) {
        return productDAO.listAllProductByCategory(id);
    }

    @Override
    public void deleteProduct(Integer id) {
        productDAO.deleteProduct(id);
    }
}
