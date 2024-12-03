package com.sinfolix.Sai_Samarth.repositories;

import com.sinfolix.Sai_Samarth.DTO.ProductCatlogueDTO;
import com.sinfolix.Sai_Samarth.entities.ProductCatlogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCatlogueRepo extends JpaRepository<ProductCatlogue,Long> {



    List<ProductCatlogue> findByCategoriesIn(List<String> categories);
    List<ProductCatlogue> findByMedicineNameContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String keyword, String keyword1);
}
