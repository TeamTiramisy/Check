package com.console.check.service;

import com.console.check.dto.ProductCreateDto;
import com.console.check.dto.ProductReadDto;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.ProductCreateMapper;
import com.console.check.mapper.ProductReadMapper;

import com.console.check.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper mapper;
    private final ProductCreateMapper createMapper;

    public List<ProductReadDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(mapper::map)
                .toList();
    }

    public ProductReadDto findById(Integer id) {
        return productRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public boolean delete(Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    productRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ProductReadDto update(Integer id, ProductCreateDto productCreateDto) {
        return productRepository.findById(id)
                .map(product -> createMapper.map(product, productCreateDto))
                .map(productRepository::saveAndFlush)
                .map(mapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Transactional
    public ProductReadDto save(ProductCreateDto productCreateDto) {
        return Optional.of(productCreateDto)
                .map(createMapper::map)
                .map(productRepository::save)
                .map(mapper::map)
                .orElseThrow();
    }
}
