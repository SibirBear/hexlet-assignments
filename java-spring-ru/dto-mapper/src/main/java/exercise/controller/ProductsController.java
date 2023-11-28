package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired
    private ProductMapper productMapper;

    //* *GET /products* – вывод списка всех товаров
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> listProducts() {
        var list = productRepository.findAll();
        return productMapper.map(list);
    }

    //* *GET /products/{id}* – просмотр конкретного товара
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO show(@PathVariable long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        var dto = productMapper.map(product);
        return dto;
    }

    //* *POST /products* – создание нового товара
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateDTO bodyCreateDto) {
        var product = productMapper.map(bodyCreateDto);
        productRepository.save(product);
        var dto = productMapper.map(product);
        return dto;
    }

    //* *PUT /products/{id}* – редактирование товара
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO update(@RequestBody ProductUpdateDTO bodyUpdateDto, @PathVariable long id ) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        productMapper.update(bodyUpdateDto, product);
        productRepository.save(product);
        var dto = productMapper.map(product);
        return dto;
    }
    // END
}
