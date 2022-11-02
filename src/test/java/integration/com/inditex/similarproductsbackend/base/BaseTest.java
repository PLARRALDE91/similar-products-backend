package integration.com.inditex.similarproductsbackend.base;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import org.assertj.core.util.Arrays;

import java.util.List;

public abstract class BaseTest {

    protected ProductDTO getSampleProductDTO() {
        return new ProductDTO("21312", "Sample Product", 132.3, true);
    }

    protected String[] getSampleProductIdsList() {
        return Arrays.array("1", "2", "3");
    }
}
