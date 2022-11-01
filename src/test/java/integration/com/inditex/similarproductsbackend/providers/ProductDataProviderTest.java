package integration.com.inditex.similarproductsbackend.providers;

import com.inditex.similarproductsbackend.dto.ProductDTO;
import com.inditex.similarproductsbackend.exception.ProductDataProviderException;
import com.inditex.similarproductsbackend.providers.implementation.RESTProductDataProvider;
import integration.com.inditex.similarproductsbackend.base.BaseTest;
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

@ExtendWith(MockitoExtension.class)
public class ProductDataProviderTest extends BaseTest {

    @Mock
    private RestTemplate restClient;

    @InjectMocks
    private RESTProductDataProvider dataProvider;

    @Test
    public void testGetProductByIdWithNotFoundResult() throws ProductDataProviderException {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null);
        Mockito.when(restClient.getForEntity("baseUrl/213213", ProductDTO.class)).thenThrow(exception);
        ProductDTO result = dataProvider.getProductById("213213");
        assertNull(result);
    }

    @Test
    public void testGetProductByIdWithRestClientException() {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new RestClientException("REST Exception");
        Mockito.when(restClient.getForEntity("baseUrl/213213", ProductDTO.class)).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getProductById("213213"));
    }

    @Test
    public void testGetProductByIdWithOKResult() throws ProductDataProviderException {
        ProductDTO productData = getSampleProductDTO();
        dataProvider.setBaseUrl("baseUrl");
        ResponseEntity<ProductDTO> response = ResponseEntity.ok(productData);
        Mockito.when(restClient.getForEntity("baseUrl/213213", ProductDTO.class)).thenReturn(response);
        assertEquals(productData, dataProvider.getProductById("213213"));
    }

    @Test
    public void testGetSimilarProductIdsNotFoundResult() throws ProductDataProviderException {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null);
        Mockito.when(restClient.getForEntity("baseUrl/213213/similarids", String[].class)).thenThrow(exception);
        assertNull(dataProvider.getSimilarProductIds("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWithRestClientException() {
        dataProvider.setBaseUrl("baseUrl");
        Throwable exception = new RestClientException("REST Exception");
        Mockito.when(restClient.getForEntity("baseUrl/213213/similarids", String[].class)).thenThrow(exception);
        assertThrows(ProductDataProviderException.class, () -> dataProvider.getSimilarProductIds("213213"));
    }

    @Test
    public void testGetSimilarProductIdsWithOKResult() throws ProductDataProviderException {
        dataProvider.setBaseUrl("baseUrl");
        String[] dataArray = getSampleProductIdsList();
        List<String> data = List.of(dataArray);
        ResponseEntity<String[]> response = ResponseEntity.ok(dataArray);
        Mockito.when(restClient.getForEntity("baseUrl/213213/similarids", String[].class)).thenReturn(response);
        assertEquals(data, dataProvider.getSimilarProductIds("213213"));
    }
}
