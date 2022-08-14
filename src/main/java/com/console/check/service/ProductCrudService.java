package com.console.check.service;

import com.console.check.dao.ProductDao;
import com.console.check.dto.ProductCreateDto;
import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Product;
import com.console.check.entity.Promo;
import com.console.check.exception.WrongIdException;
import com.console.check.mapper.ProductCreateMapper;
import com.console.check.mapper.ProductReadMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.console.check.util.Constants.DEFAULT_SIZE_PAGE;
import static com.console.check.util.Constants.NUMBER_PAGE;

@Service
@RequiredArgsConstructor
public class ProductCrudService implements CrudService<Integer, ProductReadDto, ProductCreateDto> {

    private final ProductDao productDao;
    private final ProductReadMapper mapper;
    private final ProductCreateMapper createMapper;

    public List<ProductReadDto> findAll(String size, String page){
        int pageSize = size != null ? Integer.parseInt(size) : DEFAULT_SIZE_PAGE;
        int pageNumber = page != null ? (Integer.parseInt(page) * pageSize) : NUMBER_PAGE;

        return productDao.findAll(pageSize, pageNumber).stream()
                .map(mapper::map)
                .toList();
    }

    public ProductReadDto findById(Integer id){
        return productDao.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new WrongIdException("Invalid id"));
    }

    public boolean delete(Integer id){
        return productDao.delete(id);
    }

    public void update(Integer id, ProductCreateDto productCreateDto){
        Product product = productDao.findById(id).orElseThrow();

        if (productCreateDto.getQua() != null){
            product.setQua(Integer.valueOf(productCreateDto.getQua()));
        }
        if (productCreateDto.getName() != null){
            product.setName(productCreateDto.getName());
        }
        if (productCreateDto.getCost() != null){
            product.setCost(Double.valueOf(productCreateDto.getCost()));
        }
        if (productCreateDto.getPromo() != null){
            product.setPromo(Promo.valueOf(productCreateDto.getPromo()));
        }

        product.setId(id);

        productDao.update(product);

    }

    public ProductReadDto save(ProductCreateDto productCreateDto){
        Product product = productDao.save(createMapper.map(productCreateDto));
        return mapper.map(product);
    }
}
