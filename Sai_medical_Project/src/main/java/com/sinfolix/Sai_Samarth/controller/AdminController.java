package com.sinfolix.Sai_Samarth.controller;

import com.sinfolix.Sai_Samarth.DTO.ApiResponse;
import com.sinfolix.Sai_Samarth.DTO.OrderDTO;
import com.sinfolix.Sai_Samarth.DTO.ProductCatlogueDTO;
import com.sinfolix.Sai_Samarth.DTO.UserDTO;
import com.sinfolix.Sai_Samarth.service.Impl.ProductCatlogueServiceImpl;
import com.sinfolix.Sai_Samarth.service.Impl.UserService;
import com.sinfolix.Sai_Samarth.service.OrderService;
import com.sinfolix.Sai_Samarth.service.ProductCatlogueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductCatlogueService productCatalogueService;


// Create the new ADMIN
    @PostMapping("/create-admin")
    public ResponseEntity<String> addAdmin(@RequestBody UserDTO user){
        String result = userService.saveAdmin(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

// Delete the Admin
    @DeleteMapping("/delete-admin/{id}")
    @Operation(summary = "Delete ADMIN")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id){
        userService.deleteAdmin(id);
        return new ResponseEntity<>("Admin deleted successfully",HttpStatus.NO_CONTENT);
    }

// Get all Users
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        System.out.println("Hello Ashok");
        List<UserDTO> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//Get All Placed orders
    @GetMapping("/all-orders")
    @Operation(summary = "Get all the placed orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllPlacedOrders(){
        return orderService.getAllOrders();
    }



    //  Admin will ADD/CREATE the new product to website

    @PostMapping("/add-product")
    @Operation(summary = "Will add product")
    public ResponseEntity<ProductCatlogueDTO> createProductCatlogue(@RequestBody ProductCatlogueDTO productCatlogueDTO){
        System.out.println("Hello Ashok");
        return new ResponseEntity<>(productCatalogueService.createProductCatlogue(productCatlogueDTO), HttpStatus.CREATED);
    }


//  Admin will UPDATE the existing product


    @PutMapping("/update-product/{id}")
    public ResponseEntity<ProductCatlogueDTO> updateProductCatlogue(@PathVariable Long id, @RequestBody ProductCatlogueDTO productCatlogueDTO){
        ProductCatlogueDTO updatedProductCatlogueDTO = this.productCatalogueService.updateProductCatlogue(productCatlogueDTO, id);
        return new ResponseEntity<>(updatedProductCatlogueDTO, HttpStatus.OK);
    }


//  Admin will DELETE the product

    @DeleteMapping("/delete-product/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<ApiResponse> deleteProductCatlogue(@PathVariable Long id){
        this.productCatalogueService.deleteProductCatlogue(id);
        return ResponseEntity.ok(new ApiResponse("Product Deleted Successfully",true));
    }

}
