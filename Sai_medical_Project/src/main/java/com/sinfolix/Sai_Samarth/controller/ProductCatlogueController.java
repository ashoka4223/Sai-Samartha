package com.sinfolix.Sai_Samarth.controller;

import com.sinfolix.Sai_Samarth.DTO.ApiResponse;
import com.sinfolix.Sai_Samarth.DTO.ProductCatlogueDTO;
import com.sinfolix.Sai_Samarth.service.Impl.ProductCatlogueServiceImpl;
import com.sinfolix.Sai_Samarth.service.ProductCatlogueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")// Mapping the API endpoint to root URL
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Product APIs")// Allowing all origins to access the API
//@Api(value = "Product Catlogue API", description = "API for managing product catalogue") //This annotation is used for documenting the API, typically with Swagger or similar tools. The value provides a brief title, while the description gives more context about what the API does.
//@Tag(name = "Product Catlogue API")  // Adding a tag to the API documentation

public class ProductCatlogueController {

    @Autowired
    private ProductCatlogueService productCatlogueService;

//    DISABLE disable the product
    @PatchMapping("/{id}/disable")
    public void disableProduct(@PathVariable Long id){
        productCatlogueService.disableProduct(id);
    }


// ADMIN/USER can see all the products listed on website
    @GetMapping("/")
    @Operation(summary = "Get all the products")
    public ResponseEntity<List<ProductCatlogueDTO>> getAllProducts(){
        return ResponseEntity.ok(this.productCatlogueService.getAllProductCatlogue());
    }

// Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductCatlogueDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(this.productCatlogueService.getProductCatlogueById(id));
    }

//  Search product by Categories
   @GetMapping("/category")
   @Operation(summary = "Give the product by categories")
   public ResponseEntity<List<ProductCatlogueDTO>> getProductsByCategory(@RequestParam List<String> categories ){
    return ResponseEntity.ok(this.productCatlogueService.getProductByCategory(categories));
}

// USER/ADMIN can search product by keyword
    @GetMapping("/keyword")
    @Operation(summary = "You can search product by keyword")
    public List<ProductCatlogueDTO> searchProducts(@RequestParam String keyword) {
        return productCatlogueService.searchProducts(keyword);
    }
}
