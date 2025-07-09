package id.my.hendisantika.springsecurityjwt.service;

import id.my.hendisantika.springsecurityjwt.model.Product;
import id.my.hendisantika.springsecurityjwt.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : Spring-Security-JWT
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 09/07/25
 * Time: 07.04
 * To change this template use File | Settings | File Templates.
 */
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // Get all the product
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Save/Update product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
