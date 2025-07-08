package id.my.hendisantika.springsecurityjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : Spring-Security-JWT
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 09/07/25
 * Time: 06.55
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
public class TokenPair {
    private String accessToken;
    private String refreshToken;
}
