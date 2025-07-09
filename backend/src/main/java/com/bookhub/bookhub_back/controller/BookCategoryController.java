package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.common.enums.CategoryType;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.category.request.CategoryCreateRequestDto;
import com.bookhub.bookhub_back.dto.category.request.CategoryUpdateRequestDto;
import com.bookhub.bookhub_back.dto.category.response.CategoryCreateResponseDto;
import com.bookhub.bookhub_back.dto.category.response.CategoryTreeResponseDto;
import com.bookhub.bookhub_back.dto.category.response.CategoryUpdateResponseDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyDetailResponseDto;
import com.bookhub.bookhub_back.service.BookCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.V1)
@RequiredArgsConstructor
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;

    private final String BOOK_CATEGORY_ADMIN = ApiMappingPattern.ADMIN + "/categories";
    private final String BOOK_CATEGORY_COMMON = ApiMappingPattern.COMMON + "/categories";

    // 카테고리 생성
    @PostMapping(BOOK_CATEGORY_ADMIN)
    public ResponseEntity<ResponseDto<CategoryCreateResponseDto>> createCategory(
            @Valid @RequestBody CategoryCreateRequestDto dto
    ) {
        ResponseDto<CategoryCreateResponseDto> response = bookCategoryService.createCategory(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 트리형 카테고리 전체 조회
    @GetMapping(BOOK_CATEGORY_ADMIN + "/tree")
    public ResponseEntity<ResponseDto<List<CategoryTreeResponseDto>>> getCategoryTree(
            @RequestParam CategoryType type
    ) {
        ResponseDto<List<CategoryTreeResponseDto>> response =bookCategoryService.getCategoryTree(type);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 대분류 카테고리 조회
    @GetMapping( BOOK_CATEGORY_ADMIN + "/roots")
    public ResponseEntity<ResponseDto<List<CategoryTreeResponseDto>>> getRootCategories() {
        ResponseDto<List<CategoryTreeResponseDto>> response = bookCategoryService.getRootCategories();
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 카테고리 수정
    @PutMapping(BOOK_CATEGORY_ADMIN + "/{categoryId}")
    public ResponseEntity<ResponseDto<CategoryUpdateResponseDto>> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryUpdateRequestDto dto
    ) {
        ResponseDto<CategoryUpdateResponseDto> response = bookCategoryService.updateCategory(categoryId, dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 카테고리 삭제
    @PutMapping(BOOK_CATEGORY_ADMIN + "/{categoryId}/isInactive")
    public ResponseEntity<ResponseDto<Void>> deleteCategory(
            @PathVariable Long categoryId
    ) {
        ResponseDto<Void> response = bookCategoryService.deleteCategory(categoryId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    // 카테고리 ID로 적용된 할인 정책 조회
    @GetMapping(BOOK_CATEGORY_COMMON + "/{categoryId}/policy")
    public ResponseEntity<ResponseDto<DiscountPolicyDetailResponseDto>> getPolicyByCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        ResponseDto<DiscountPolicyDetailResponseDto> response = bookCategoryService.getPolicyByCategoryId(categoryId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }
}
