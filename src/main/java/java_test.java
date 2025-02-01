import com.chang.mall.entity.Product;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class java_test {
    public static void main(String[] args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Product product = new Product();
        product.setProductId(keyHolder.getKey().intValue());
//        int i = keyHolder.getKey().intValue();
        System.out.println(product.getProductId());
    }
}
