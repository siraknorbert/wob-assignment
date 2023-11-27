package hu.wob.assignment.client;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import hu.wob.assignment.configuration.MockarooConfigProperties;
import hu.wob.assignment.exception.DataSyncRestException;

/**
 * Abstract base class for external REST API communication.
 */
public abstract class AbstractBaseApiClient {

    private final RestTemplate restTemplate;
    private final MockarooConfigProperties mockarooConfigs;
    private String url;

    protected AbstractBaseApiClient(RestTemplate restTemplate, MockarooConfigProperties mockarooConfigs) {
        this.restTemplate = restTemplate;
        this.mockarooConfigs = mockarooConfigs;
    }

    protected <T> List<T> getExternalData(ParameterizedTypeReference<List<T>> typeReference) throws DataSyncRestException {
        ResponseEntity<List<T>> response;
        try {
            response = restTemplate.exchange(getUrl(), HttpMethod.GET, null, typeReference);
        } catch (RestClientException e) {
            throw newDataSyncRestCommunicationException(e);
        }

        is2xxSuccessfulEx(response.getStatusCode());
        return response.getBody();
    }

    protected String getExternalData() throws DataSyncRestException {
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity(getUrl(), String.class);
        } catch (RestClientException e) {
            throw newDataSyncRestCommunicationException(e);
        }

        is2xxSuccessfulEx(response.getStatusCode());
        return response.getBody();
    }

    protected abstract String getPath();

    private String getUrl() {
        if (!StringUtils.hasLength(url)) {
            url = buildUrl();
        }
        return url;
    }

    private String buildUrl() {
        StringBuilder url = new StringBuilder();
        url.append(mockarooConfigs.getBaseUrl());
        url.append(getPath());
        url.append("?key=");
        url.append(mockarooConfigs.getApiKey());
        return url.toString();
    }

    private DataSyncRestException newDataSyncRestCommunicationException(RestClientException e) {
        return new DataSyncRestException("Something went wrong while communicating with external API", e);
    }

    private void is2xxSuccessfulEx(HttpStatusCode httpStatusCode) throws DataSyncRestException {
        if (!httpStatusCode.is2xxSuccessful()) {
            String errorMsg = MessageFormat.format("External API response status was not 2xx success but {0}", httpStatusCode.value());
            throw new DataSyncRestException(errorMsg);
        }
    }
}
