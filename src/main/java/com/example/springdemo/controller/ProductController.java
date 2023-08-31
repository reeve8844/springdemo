package com.example.springdemo.controller;

import com.example.springdemo.dto.ProductWithCategoryDTO;
import com.example.springdemo.entity.*;
import com.example.springdemo.repository.CategoryRepository;
import com.example.springdemo.repository.ProdCateRepository;
import com.example.springdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProdCateRepository prodCateRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping("/search-product")
    public ResponseEntity<List<ProductWithCategoryDTO>> getProductsByName(@RequestParam(required = false) String name) {
        System.out.println("get Product: ");
        try {
            List<ProductWithCategoryDTO> productWithCategory = new ArrayList<>();
            List<Products> products = new ArrayList<Products>();

            if (name.isEmpty()) {
                productRepository.findAll().forEach(products::add);
            } else {
                productRepository.findByNameLike("%"+name+"%").forEach(products::add);
            }
            for (Products product : products) {
                // error
                Optional<Product_Categories> product_categories = prodCateRepository.findByProductId(product.getProduct_id());
                ProductWithCategoryDTO dto = new ProductWithCategoryDTO();
                dto.setProducts(product);
                if (product_categories.isPresent()){
                    Product_Categories productCategory = product_categories.get();
                    Optional<Categories> category = categoryRepository.findById(productCategory.getCategories().getCategory_id());
                    dto.setCategories(category.get());
                }
                productWithCategory.add(dto);
            }

            if (productWithCategory.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productWithCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/category-list")
//    public ResponseEntity<List<Categories>> getCategoryList() {
//        System.out.println("get Category List: ");
//        try {
//            List<Categories> categories = new ArrayList<>();
//
//            categoryRepository.findAll().forEach(categories::add);
//
//            if (categories.isEmpty()) {
//                System.out.println("Category not found");
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(categories, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Products> createProduct(@ModelAttribute Products products) {
        System.out.println("create: ");
        if(products.getName().isEmpty()){
            System.out.println("not empty");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (productRepository.findByName(products.getName())!=null) {
            System.out.println("product exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            Products product = new Products();
            product.setName(products.getName());
            productRepository.save(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }

    }

    @PostMapping("/product_category")
    public ResponseEntity<Product_Categories> createProduct_Category(@RequestBody Map<String, Object> requestData) {
        System.out.println("create product with category: ");
        Map<String, Object> productData = (Map<String, Object>) requestData.get("productData");
        Products product = new Products();
        product.setName((String) productData.get("name"));
        String description = (String) productData.get("description");
        if (!description.isEmpty()) {
            product.setDescription(description);
        }
        Integer price = Integer.parseInt((String) productData.get("price"));
        if (price!=null) {
            product.setPrice(price);
        }
        Integer quantity = Integer.parseInt((String) productData.get("stock_quantity"));
        if (quantity!=null) {
            product.setStock_quantity(quantity);
        }
        String url = (String) productData.get("image_url");
        if (!url.isEmpty()) {
            product.setImage_url(url);
        }

        Integer c_id = (Integer) requestData.get("selectedCategoryId");

        if(product.getName().isEmpty()){
            System.out.println("not blank");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else if (productRepository.findByName(product.getName()).isPresent()) {
            System.out.println("product exist");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            System.out.println("save data");
            productRepository.save(product);
            Categories categories = categoryRepository.findById(c_id).get();
            Product_Categories product_category = prodCateRepository.save(new Product_Categories(product, categories));
            return new ResponseEntity<>(product_category, HttpStatus.CREATED);
        }

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable("id") Integer id, @RequestBody Map<String, Object> requestData) {
        System.out.println("update product: ");
        Optional<Products> products = productRepository.findById(id);
        Date date = new Date();

        Products product = products.get();
        product.setName((String) requestData.get("name"));

        if (Optional.ofNullable((String) requestData.get("description")).isPresent()) {
            product.setDescription((String) requestData.get("description"));
        }
        if (Optional.ofNullable((int) requestData.get("price")).isPresent()) {
            product.setPrice((int) requestData.get("price"));
        }
        if (Optional.ofNullable((int) requestData.get("stock_quantity")).isPresent()) {
            product.setStock_quantity((int) requestData.get("stock_quantity"));
        }
        if (Optional.ofNullable((String) requestData.get("image_url")).isPresent()) {
            product.setImage_url((String) requestData.get("image_url"));
        }

        Integer c_id = (Integer) requestData.get("category_id");

        product.setUpdated_at(date);

        if (products.isPresent()) {

            if (c_id != null) {
                Optional<Categories> categories = categoryRepository.findById(c_id);
                Optional<Product_Categories> product_categories = prodCateRepository.findByProductId(product.getProduct_id());
                Categories category = categories.get();
                if (product_categories.isPresent()) {
                    Product_Categories product_category = product_categories.get();
                    product_category.setCategories(category);
                    prodCateRepository.save(product_category);
                } else {
                    Product_Categories product_category = prodCateRepository.save(new Product_Categories(product, category));
                }
            }
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Employees> deleteProduct(@PathVariable("id") Integer id) {
        System.out.println("delete product: ");
        Optional<Products> products = productRepository.findById(id);
        Optional<Product_Categories> product_categories = prodCateRepository.findByProductId(products.get().getProduct_id());

        if (product_categories.isPresent()) {
            try {
                prodCateRepository.deleteById(product_categories.get().getProduct_category_id());
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Products product = products.get();
        productRepository.deleteById(product.getProduct_id());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
