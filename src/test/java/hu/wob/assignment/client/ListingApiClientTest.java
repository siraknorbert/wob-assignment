package hu.wob.assignment.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.exception.DataSyncRestException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ListingApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MockarooConfigProperties mockarooConfigProperties;

    @InjectMocks
    private ListingApiClient listingApiClient;

    @Test
    void testGetListingList200Ok() throws DataSyncRestException {
        // given
        String expectedResponse = "expectedString";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        // when
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(responseEntity);
        listingApiClient.getListingList();

        // then
        Mockito.verify(restTemplate).getForEntity(Mockito.anyString(), Mockito.any(Class.class));
    }
}
