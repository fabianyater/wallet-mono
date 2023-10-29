package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.CategoryDeleteRequest;
import com.wallet.mono.domain.dto.CategoryRequest;
import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.exception.CategoryAlreadyDoesNotExists;
import com.wallet.mono.exception.CategoryAlreadyExists;
import com.wallet.mono.exception.DefatulCategory;
import com.wallet.mono.exception.TypeNotSelectedException;
import com.wallet.mono.service.CategoryService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("categories/")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws CategoryAlreadyExists, DefatulCategory, TypeNotSelectedException {
        categoryService.addCategory(categoryRequest);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Categoría agregada correctamente");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setPagination(null);
        apiResponse.setData(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories(@PathVariable("userId") int userId) {
        List<CategoryResponse> categoryResponses = categoryService.getCategories(userId);
        ApiResponse<List<CategoryResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setMessage("Categorías obtenidas correctamente");
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponses);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("{categoryId}/user/{userId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryDetails(
            @PathVariable("categoryId") int categoryId,
            @PathVariable("userId") int userId)
            throws CategoryAlreadyDoesNotExists {
        Optional<CategoryResponse> categoryResponse = categoryService.getCategory(categoryId, userId);
        ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();

        apiResponse.setMessage("Categoria obtenida correctamente");
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(categoryResponse.get());
        apiResponse.setPagination(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<ApiResponse<Void>> updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestBody CategoryRequest categoryRequest) throws Exception {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        categoryService.updateCategory(categoryId, categoryRequest);

        apiResponse.setMessage("Categoría actualizada correctamente");
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @RequestBody CategoryDeleteRequest categoryRequest)
            throws Exception {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        categoryService.deleteCategory(categoryRequest);

        apiResponse.setMessage("Categoría eliminada correctamente");
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
