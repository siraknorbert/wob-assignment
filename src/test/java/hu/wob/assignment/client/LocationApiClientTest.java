package hu.wob.assignment.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.exception.DataSyncRestException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class LocationApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MockarooConfigProperties mockarooConfigProperties;

    @InjectMocks
    private LocationApiClient locationApiClient;

    @Test
    void testGetLocationList200Ok() throws DataSyncRestException {
        // given
        List<LocationDto> expectedResponse = new ArrayList<>();
        ResponseEntity<List<LocationDto>> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        // when
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.isNull(),
                Mockito.any(ParameterizedTypeReference.class))).thenReturn(responseEntity);
        locationApiClient.getLocationList();

        // then
        Mockito.verify(restTemplate).exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.isNull(),
                Mockito.any(ParameterizedTypeReference.class));
    }
}
