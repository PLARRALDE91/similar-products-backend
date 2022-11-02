package integration.com.inditex.similarproductsbackend.services;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;
import com.inditex.similarproductsbackend.exception.ProductNotFoundException;
import com.inditex.similarproductsbackend.exception.ServiceException;
import com.inditex.similarproductsbackend.providers.implementation.RESTProductDataProvider;
import com.inditex.similarproductsbackend.services.implementation.ProductsServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import integration.com.inditex.similarproductsbackend.base.BaseTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest extends BaseTest {

    @Mock
    private RESTProductDataProvider dataProvider;

    @InjectMocks
    private ProductsServiceImplementation productsService;

    @Test
    public void testGetProductByIdWhenProviderThrowsException() throws ProductDataProviderException {
        Throwable exception = new ProductDataProviderException("data exception");
        Mockito.when(dataProvider.getProductById(any())).thenThrow(exception);
        assertThrows(ServiceException.class, () -> productsService.getProductById("12"));
    }

    @Test
    public void testGetProductByIdWhenProviderProvidesOK() throws ProductDataProviderException, ServiceException {
        ProductDTO productDTO = getSampleProductDTO();
        Mockito.when(dataProvider.getProductById("21312")).thenReturn(productDTO);
        ProductDTO result = productsService.getProductById("21312");
        assertEquals(productDTO, result);
    }

    @Test
    public void testGetSimilarProductsWhenProviderThrowsException() throws ProductDataProviderException {
        ProductDTO productDTO = getSampleProductDTO();
        Throwable exception = new ProductDataProviderException("data exception");
        Mockito.when(dataProvider.getProductById("21312")).thenReturn(productDTO);
        Mockito.when(dataProvider.getSimilarProductIds(any())).thenThrow(exception);
        assertThrows(ServiceException.class, () -> productsService.getSimilarProducts("21312"));
    }

    @Test
    public void testGetSimilarProductsWhenUnexistingProvidedProduct() throws ProductDataProviderException {
        Mockito.when(dataProvider.getProductById("21312")).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> productsService.getSimilarProducts("21312"));
    }

    @Test
    public void testGetSimilarProductsWhenUnexistingSimilarItem() throws ProductDataProviderException, ServiceException, ProductNotFoundException {
        ProductDTO productDTO = getSampleProductDTO();
        Mockito.when(dataProvider.getProductById("21312")).thenReturn(productDTO);
        Mockito.when(dataProvider.getProductById("12345")).thenReturn(null);
        Mockito.when(dataProvider.getSimilarProductIds(any())).thenReturn(List.of("12345"));
        assertTrue(productsService.getSimilarProducts("21312").isEmpty());
    }
}
