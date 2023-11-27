package hu.wob.assignment.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.constant.IExternalPathConstants;
import hu.wob.assignment.exception.DataSyncRestException;

/**
 * Helper class for external REST API communication - listing resource handling.
 */
@Component
public class ListingApiClient extends AbstractBaseApiClient {

    public ListingApiClient(RestTemplate restTemplate, MockarooConfigProperties mockarooConfigs) {
        super(restTemplate, mockarooConfigs);
    }

    public String getListingList() throws DataSyncRestException {
        return getExternalData();
    }

    @Override
    protected String getPath() {
        return IExternalPathConstants.POSTFIX_LISTING;
    }
}
