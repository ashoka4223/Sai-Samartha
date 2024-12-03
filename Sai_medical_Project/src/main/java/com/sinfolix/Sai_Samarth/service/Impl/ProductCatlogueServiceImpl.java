package com.sinfolix.Sai_Samarth.service.Impl;

import com.sinfolix.Sai_Samarth.DTO.ProductCatlogueDTO;
import com.sinfolix.Sai_Samarth.entities.ProductCatlogue;
import com.sinfolix.Sai_Samarth.exceptions.ResourceNotFoundException;
import com.sinfolix.Sai_Samarth.repositories.ProductCatlogueRepo;
import com.sinfolix.Sai_Samarth.service.ProductCatlogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCatlogueServiceImpl implements ProductCatlogueService {

    @Autowired
    private ProductCatlogueRepo productCatlogueRepo;  // Assuming ProductCatlogueRepo is defined in the same package

    @Override
    public ProductCatlogueDTO createProductCatlogue(ProductCatlogueDTO product) {
        ProductCatlogue productCatlogue = DTOtoProductCatlogue(product);
        productCatlogueRepo.save(productCatlogue);
        return productCatlogueToDTO(productCatlogue);

    }

    @Override
    public ProductCatlogueDTO updateProductCatlogue(ProductCatlogueDTO product, Long productId) {
    // Implement logic to update product in the database and return updated DTO
        ProductCatlogue productCatlogue = this.productCatlogueRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Catlogue","ID ",productId));
        productCatlogue.setQuantity(product.getQuantity());
        productCatlogue.setPrice(product.getPrice());
        productCatlogue.setDiscount(product.getRealMrp()-product.getDiscountMrp());
        productCatlogue.setCompanyName(product.getCompanyName());
        productCatlogue.setMedicineName(product.getMedicineName());
        productCatlogue.setMinAge(product.getMinAge());
        productCatlogue.setMaxAge(product.getMaxAge());
        productCatlogue.setRealMrp(product.getRealMrp());
        productCatlogue.setDiscountMpr(product.getDiscountMrp());
        productCatlogue.setCategories(product.getCategories());
        return productCatlogueToDTO(this.productCatlogueRepo.save(productCatlogue));
    }

    @Override
    public ProductCatlogueDTO getProductCatlogueById(Long productId) {
        ProductCatlogue productCatlogue = this.productCatlogueRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Catlogue","ID ",productId));

        return productCatlogueToDTO(productCatlogue);
    }

    @Override
    public List<ProductCatlogueDTO> getAllProductCatlogue() {
        List<ProductCatlogue> productCatlogue = this.productCatlogueRepo.findAll();
        List<ProductCatlogueDTO> productCatlogueDto=productCatlogue.stream().map(e -> this.productCatlogueToDTO(e)).collect(Collectors.toList());


        return productCatlogueDto;
    }

    @Override
    public void deleteProductCatlogue(Long productId) {
        ProductCatlogue productCatlogue = this.productCatlogueRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Catlogue","ID ",productId));
        this.productCatlogueRepo.delete(productCatlogue);
    }

    @Override
    public void disableProduct(Long id) {
        ProductCatlogue productCatlogue = productCatlogueRepo.findById(id).orElseThrow();
        productCatlogue.setDisabled(true);
        productCatlogueRepo.save(productCatlogue);
    }

    public List<ProductCatlogueDTO> getProductByCategory(List<String> categories) {
        List<ProductCatlogue> productCatalogue = this.productCatlogueRepo.findByCategoriesIn(categories);
        return productCatalogue.stream().map(e-> this.productCatlogueToDTO(e)).collect(Collectors.toList());
    }


        public List<ProductCatlogueDTO> searchProducts(String keyword) {
        List<ProductCatlogue> productCatlogues=  productCatlogueRepo.findByMedicineNameContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(keyword, keyword);
            return productCatlogues.stream().map(this::productCatlogueToDTO).collect(Collectors.toList());
    }


    private ProductCatlogue DTOtoProductCatlogue(ProductCatlogueDTO productCatlogueDTO) {
        ProductCatlogue productCatlogue = new ProductCatlogue();
        // Mapping fields from DTO to Entity

        productCatlogue.setId(productCatlogueDTO.getId());
        productCatlogue.setImage(productCatlogueDTO.getImage());
        productCatlogue.setQuantity(productCatlogueDTO.getQuantity());
        productCatlogue.setPrice(productCatlogueDTO.getPrice());
        productCatlogue.setDiscount(productCatlogueDTO.getRealMrp()-productCatlogueDTO.getDiscountMrp());
        productCatlogue.setCompanyName(productCatlogueDTO.getCompanyName());
        productCatlogue.setMedicineName(productCatlogueDTO.getMedicineName());
        productCatlogue.setMinAge(productCatlogueDTO.getMinAge());
        productCatlogue.setMaxAge(productCatlogueDTO.getMaxAge());
        productCatlogue.setRealMrp(productCatlogueDTO.getRealMrp());
        productCatlogue.setDiscountMpr(productCatlogueDTO.getDiscountMrp());
        productCatlogue.setProdDescription(productCatlogueDTO.getProdDescription());
        productCatlogue.setDisabled(productCatlogueDTO.isDisabled());
        productCatlogue.setComments(productCatlogueDTO.getComments());
        productCatlogue.setCategories(productCatlogueDTO.getCategories());

        return productCatlogue;

    }

    private ProductCatlogueDTO productCatlogueToDTO(ProductCatlogue productCatlogue) {
        ProductCatlogueDTO productCatlogueDTO = new ProductCatlogueDTO();
        // Mapping fields from Entity to DTO

        productCatlogueDTO.setId(productCatlogue.getId());
        productCatlogueDTO.setQuantity(productCatlogue.getQuantity());
        productCatlogueDTO.setImage(productCatlogue.getImage());
        productCatlogueDTO.setPrice(productCatlogue.getPrice());
        productCatlogueDTO.setDiscount(productCatlogue.getDiscount());
        productCatlogueDTO.setCompanyName(productCatlogue.getCompanyName());
        productCatlogueDTO.setMedicineName(productCatlogue.getMedicineName());
        productCatlogueDTO.setMinAge(productCatlogue.getMinAge());
        productCatlogueDTO.setMaxAge(productCatlogue.getMaxAge());
        productCatlogueDTO.setRealMrp(productCatlogue.getRealMrp());
        productCatlogueDTO.setDiscountMrp(productCatlogue.getDiscountMpr());
        productCatlogueDTO.setProdDescription(productCatlogue.getProdDescription());
        productCatlogueDTO.setDisabled(productCatlogue.isDisabled());
        productCatlogueDTO.setComments(productCatlogue.getComments());
        productCatlogueDTO.setCategories(productCatlogue.getCategories());
        return productCatlogueDTO;
    }



}
