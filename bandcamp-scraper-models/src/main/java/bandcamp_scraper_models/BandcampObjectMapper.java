package bandcamp_scraper_models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public final class BandcampObjectMapper {

    private BandcampObjectMapper() {}

    public static ObjectMapper newInstance() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }
}
