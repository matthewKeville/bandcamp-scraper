import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractSerializationTest<T> {

  private static final ObjectMapper mapper = new ObjectMapper();
  protected Logger LOG = provideLogger();

  protected abstract Logger provideLogger();
  protected abstract Stream<Arguments> provideTestCases();

  //protected abstract Class<T> getModelClass();

  @ParameterizedTest
  @MethodSource("provideTestCases")
  void serializesCorrectly(T model, String expectedJson) throws Exception {
    String actualJson = mapper.writeValueAsString(model);
    Assertions.assertEquals(expectedJson.trim(), actualJson.trim(), "Serialized JSON should match expected");
  }

}
