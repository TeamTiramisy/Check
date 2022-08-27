package com.console.check.service;

import com.console.check.dto.ProductCreateDto;
import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.ProductCreateMapper;
import com.console.check.mapper.ProductReadMapper;

import com.console.check.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.console.check.util.Constants.DEFAULT_SIZE_PAGE;
import static com.console.check.util.Constants.NUMBER_PAGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper mapper;
    private final ProductCreateMapper createMapper;

    public List<ProductReadDto> findAll(String size, String page){
        int pageSize = size != null ? Integer.parseInt(size) : DEFAULT_SIZE_PAGE;
        int pageNumber = page != null ? (Integer.parseInt(page)) : NUMBER_PAGE;

        return productRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream()
                .map(mapper::map)
                .toList();
    }

    public ProductReadDto findById(Integer id){
        return productRepository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new WrongIdException("Invalid id"));
    }

    @Transactional
    public boolean delete(Integer id){
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public ProductReadDto update(Integer id, ProductCreateDto productCreateDto){
        return productRepository.findById(id)
                .map(product -> createMapper.map(product, productCreateDto))
                .map(productRepository::saveAndFlush)
                .map(mapper::map)
                .orElseThrow(() -> new WrongIdException("Invalid id"));

    }

    @Transactional
    public ProductReadDto save(ProductCreateDto productCreateDto){
        return Optional.of(productCreateDto)
                .map(createMapper::map)
                .map(productRepository::save)
                .map(mapper::map)
                .orElseThrow();
    }
}
