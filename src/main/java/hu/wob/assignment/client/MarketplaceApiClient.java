package hu.wob.assignment.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.constant.IExternalPathConstants;
import hu.wob.assignment.dto.MarketplaceDto;
import hu.wob.assignment.exception.DataSyncRestException;

/**
 * Helper class for external REST API communication - marketplace resource handling.
 */
@Component
public class MarketplaceApiClient extends AbstractBaseApiClient {

    public MarketplaceApiClient(RestTemplate restTemplate, MockarooConfigProperties mockarooConfigs) {
        super(restTemplate, mockarooConfigs);
    }

    public List<MarketplaceDto> getMarketplaceList() throws DataSyncRestException {
        ParameterizedTypeReference<List<MarketplaceDto>> typeReference = new ParameterizedTypeReference<>() {
        };
        return getExternalData(typeReference);
    }

    @Override
    protected String getPath() {
        return IExternalPathConstants.POSTFIX_MARKETPLACE;
    }
}
