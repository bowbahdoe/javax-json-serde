package dev.mccue.json_serde;

import javax.json.JsonValue;

public interface JsonDecoder<T> {
    T decode(JsonValue json) throws DecodingException;
}
