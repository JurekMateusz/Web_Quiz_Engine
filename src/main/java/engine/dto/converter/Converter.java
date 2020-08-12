package engine.dto.converter;

public interface Converter<T, U> {
  U convert(T objectToConvert);
}
