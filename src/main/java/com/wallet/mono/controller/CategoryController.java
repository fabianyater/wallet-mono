package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("categories/")
public class CategoryController {
    private final CategoryService  categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }


}
