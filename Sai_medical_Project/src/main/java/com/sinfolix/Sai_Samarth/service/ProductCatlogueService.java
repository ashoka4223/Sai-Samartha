package com.sinfolix.Sai_Samarth.service;

import com.sinfolix.Sai_Samarth.DTO.ProductCatlogueDTO;
import com.sinfolix.Sai_Samarth.entities.ProductCatlogue;

import java.util.List;

public interface ProductCatlogueService {

    ProductCatlogueDTO createProductCatlogue(ProductCatlogueDTO product);
    ProductCatlogueDTO updateProductCatlogue(ProductCatlogueDTO product,Long ProductId);
    ProductCatlogueDTO getProductCatlogueById(Long ProductId);
    List<ProductCatlogueDTO> getAllProductCatlogue();
    void deleteProductCatlogue(Long ProductId);
    void disableProduct(Long id);
    List<ProductCatlogueDTO> getProductByCategory(List<String> categories);
    List<ProductCatlogueDTO> searchProducts(String keyword);
}
