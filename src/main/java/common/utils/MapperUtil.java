package common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

public class MapperUtil {
        public static <T> T mapToObj(Map<String, Object> map, Class<T> cls) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return mapper.convertValue(map, cls);
        }
}