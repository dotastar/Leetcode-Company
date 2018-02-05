package projects.crawler.data.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.mongojack.internal.MongoJackModule;

import static com.fasterxml.jackson.databind.MapperFeature.INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES;


/**
 * Serializes and deserializes to/from JSON.
 */
@Slf4j
public class ModelMapper {

  public static final ObjectMapper MONGO_MAPPER;

  static {
    // Create a custom Mongo BSON mapper
    MONGO_MAPPER = new ObjectMapper();
    MONGO_MAPPER.registerModule(MongoJackModule.INSTANCE);
    MONGO_MAPPER.registerModule(new JavaTimeModule());

    MONGO_MAPPER.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
    MONGO_MAPPER.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
    MONGO_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
    MONGO_MAPPER.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
    // Don't include null props or empty lists
    MONGO_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);


    // Only include whitelisted props
//    MONGO_MAPPER.setVisibility(
//        MONGO_MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
//            .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
//            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
//            .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
//            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
//            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
  }

}
