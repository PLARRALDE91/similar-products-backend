package integration.com.inditex.similarproductsbackend.base;

import com.inditex.similarproductsbackend.dto.ProductDTO;

public abstract class BaseTest {

    protected ProductDTO getSampleProductDTO() {
        return new ProductDTO("21312", "Sample Product", 132.3, true);
    }
}
