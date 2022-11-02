package integration.com.inditex.similarproductsbackend.controllers;

import com.inditex.similarproductsbackend.controllers.SimilarProductsController;
import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.dto.SimilarProductsResponseDTO;
import com.inditex.similarproductsbackend.exception.ProductNotFoundException;
import com.inditex.similarproductsbackend.exception.ServiceException;
import com.inditex.similarproductsbackend.services.implementation.ProductsServiceImplementation;
import integration.com.inditex.similarproductsbackend.base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SimilarProductsControllerTest extends BaseTest {

    @Mock
    private ProductsServiceImplementation productsServiceImplementation;

    @InjectMocks
    private SimilarProductsController similarProductsController;

    @Test
    public void testGetSimilarProductsDataProductNotFound() throws ServiceException, ProductNotFoundException {
        Mockito.when(productsServiceImplementation.getSimilarProducts(any())).thenThrow(new ProductNotFoundException());
        ResponseEntity<?> response = similarProductsController.getSimilarProductsData("213123");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetSimilarProductsDataWithInternalError() throws ServiceException, ProductNotFoundException {
        Throwable exception = new ServiceException("service exception");
        Mockito.when(productsServiceImplementation.getSimilarProducts(any())).thenThrow(exception);
        ResponseEntity<?> response = similarProductsController.getSimilarProductsData("213123");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetSimilarProductsDataFoundProduct() throws ServiceException, ProductNotFoundException {
        List<ProductDTO> products = List.of(getSampleProductDTO());
        Mockito.when(productsServiceImplementation.getSimilarProducts(any())).thenReturn(products);
        ResponseEntity<?> response = similarProductsController.getSimilarProductsData("213123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, ((SimilarProductsResponseDTO) Objects.requireNonNull(response.getBody())).getProducts());
    }

}
