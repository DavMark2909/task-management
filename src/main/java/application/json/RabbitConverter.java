package application.json;

import application.dto.feign.FeignMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RabbitConverter {

    public static String convertToTaskJson(FeignMessage message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(message);
        return json;
    }
}
