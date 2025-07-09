package id.my.hendisantika.springsecurityjwt.service;

import id.my.hendisantika.springsecurityjwt.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

}
