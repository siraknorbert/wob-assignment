package hu.wob.assignment.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.constant.IExternalPathConstants;
import hu.wob.assignment.dto.ListingStatusDto;
import hu.wob.assignment.exception.DataSyncRestException;

/**
 * Helper class for external REST API communication - listing status resource handling.
 */
@Component
public class ListingStatusApiClient extends AbstractBaseApiClient {

    public ListingStatusApiClient(RestTemplate restTemplate, MockarooConfigProperties mockarooConfigs) {
        super(restTemplate, mockarooConfigs);
    }

    public List<ListingStatusDto> getListingStatusList() throws DataSyncRestException {
        ParameterizedTypeReference<List<ListingStatusDto>> typeReference = new ParameterizedTypeReference<>() {
        };
        return getExternalData(typeReference);
    }

    @Override
    protected String getPath() {
        return IExternalPathConstants.POSTFIX_LISTING_STATUS;
    }
}
