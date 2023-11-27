package hu.wob.assignment.helper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.wob.assignment.exception.DataMappingException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonMappingHelper {

    private final ObjectMapper objectMapper;

    public <T> String mapToJSON(T object) throws DataMappingException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new DataMappingException("Error occurred while mapping object to JSON");
        }
    }
}
