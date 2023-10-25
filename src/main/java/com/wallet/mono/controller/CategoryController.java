package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.CategoryRequest;
import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("categories/")
public class CategoryController {
    private final CategoryService  categoryService;

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }


}
