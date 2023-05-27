package com.bmo.common.market_service.core.controller;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.mapper.ProductMapper;
import com.bmo.common.market_service.core.service.ProductService;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductFiltersCriteria;
import com.bmo.common.market_service.model.product.ProductResponseDto;
import com.bmo.common.market_service.model.product.ProductUpdateDto;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/product")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody ProductCreateDto productCreateDto) {

        Product product = productService.createProduct(productCreateDto);
        ProductResponseDto responseDto = productMapper.mapToResponseDto(product);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/product")
    public ResponseEntity<Page<ProductResponseDto>> getProductsFiltered(
        ProductFiltersCriteria productFiltersCriteria) {

        Page<Product> productPage = productService.getProductsFiltered(productFiltersCriteria);
        Page<ProductResponseDto> responsePage = productPage.map(productMapper::mapToResponseDto);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(
        @PathVariable("id") UUID productId) {

        Product product = productService.getProductById(productId);
        ProductResponseDto productResponseDto = productMapper.mapToResponseDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
        @NotNull @PathVariable("id") UUID productId,
        @RequestBody ProductUpdateDto productUpdateDto) {

        Product product = productService.updateProduct(productId, productUpdateDto);
        ProductResponseDto productResponseDto = productMapper.mapToResponseDto(product);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(
        @NotNull @PathVariable("id") UUID productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
