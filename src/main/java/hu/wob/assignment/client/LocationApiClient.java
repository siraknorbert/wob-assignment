package hu.wob.assignment.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.constant.IExternalPathConstants;
import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.exception.DataSyncRestException;

/**
 * Helper class for external REST API communication - location resource handling.
 */
@Component
public class LocationApiClient extends AbstractBaseApiClient {

    public LocationApiClient(RestTemplate restTemplate, MockarooConfigProperties mockarooConfigs) {
        super(restTemplate, mockarooConfigs);
    }

    public List<LocationDto> getLocationList() throws DataSyncRestException {
        ParameterizedTypeReference<List<LocationDto>> typeReference = new ParameterizedTypeReference<>() {
        };
        return getExternalData(typeReference);
    }

    @Override
    protected String getPath() {
        return IExternalPathConstants.POSTFIX_LOCATION;
    }
}
