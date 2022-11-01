package integration.com.inditex.similarproductsbackend.providers;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;
import com.inditex.similarproductsbackend.providers.implementation.RESTProductDataProvider;
import integration.com.inditex.similarproductsbackend.base.BaseTest;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductDataProviderTest extends BaseTest {

    @Mock
    private RestTemplate restClient;

    @Mock
    private CircuitBreaker circuitBreaker;

    @InjectMocks
    private RESTProductDataProvider dataProvider;

    @Test
    public void testGetProductByIdWithNotFoundResult() throws ProductDataProviderException {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null);
        Mockito.when(restClient.getForEntity("baseUrl/213213", ProductDTO.class)).thenThrow(exception);
        ProductDTO result = dataProvider.getProduct("213213");
        assertNull(result);
    }

    @Test
    public void testGetProductByIdWithRestClientException() {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new RestClientException("REST Exception");
        Mockito.when(restClient.getForEntity("baseUrl/213213", ProductDTO.class)).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getProduct("213213"));
    }

    @Test
    public void testGetProductByIdWithOKResult() throws ProductDataProviderException {
        ProductDTO productData = getSampleProductDTO();
        dataProvider.setBaseUrl("baseUrl");
        ResponseEntity<ProductDTO> response = ResponseEntity.ok(productData);
        Mockito.when(restClient.getForEntity("baseUrl/213213", ProductDTO.class)).thenReturn(response);
        assertEquals(productData, dataProvider.getProduct("213213"));
    }

    @Test
    public void testGetSimilarProductIdsNotFoundResult() throws ProductDataProviderException {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null);
        Mockito.when(restClient.getForEntity("baseUrl/213213/similarids", String[].class)).thenThrow(exception);
        assertNull(dataProvider.getSimilarIds("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWithRestClientException() {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new RestClientException("REST Exception");
        Mockito.when(restClient.getForEntity("baseUrl/213213/similarids", String[].class)).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getSimilarIds("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWithOKResult() throws ProductDataProviderException {
        dataProvider.setBaseUrl("baseUrl");
        String[] dataArray = getSampleProductIdsList();
        List<String> data = List.of(dataArray);
        ResponseEntity<String[]> response = ResponseEntity.ok(dataArray);
        Mockito.when(restClient.getForEntity("baseUrl/213213/similarids", String[].class)).thenReturn(response);
        assertEquals(data, dataProvider.getSimilarIds("213213"));
    }

    @Test
    public void testGetProductByIdWhenCircuitBreakerRunsOK() throws Exception {
        dataProvider.setBaseUrl("baseUrl");
        ProductDTO productDTO = getSampleProductDTO();
        Mockito.when(circuitBreaker.executeCallable(any())).thenReturn(productDTO);
        assertEquals(productDTO, dataProvider.getProductById("213213"));
        assertEquals(productDTO, dataProvider.getProductById("213213"));
    }

    @Test
    public void testGetProductByIdWhenCircuitBreakerThrowsProductDataProviderException() throws Exception {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new ProductDataProviderException("error getting data");
        Mockito.when(circuitBreaker.executeCallable(any())).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getProductById("213213"));
    }

    @Test
    public void testGetProductByIdWhenCircuitBreakerThrowsException() throws Exception {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new Exception("error getting data");
        Mockito.when(circuitBreaker.executeCallable(any())).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getProductById("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWhenCircuitBreakerRunsOK() throws Exception {
        dataProvider.setBaseUrl("baseUrl");
        String[] dataArray = getSampleProductIdsList();
        List<String> data = List.of(dataArray);
        Mockito.when(circuitBreaker.executeCallable(any())).thenReturn(data);
        assertEquals(data, dataProvider.getSimilarProductIds("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWhenCircuitBreakerThrowsProductDataProviderException() throws Exception {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new ProductDataProviderException("error getting data");
        Mockito.when(circuitBreaker.executeCallable(any())).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getSimilarProductIds("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWhenCircuitBreakerThrowsException() throws Exception {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new Exception("error getting data");
        Mockito.when(circuitBreaker.executeCallable(any())).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getSimilarProductIds("213213"));
    }
}
