package dev.mccue.json_serde;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

public final class Decoders {
    private Decoders() {
    }

    public static int decodingInt(JsonValue value) throws DecodingException {
        if (value instanceof JsonNumber num) {
            try {
                return num.intValueExact();
            } catch (ArithmeticException ex) {
                throw new DecodingException("Expected an integer value");
            }
        } else {
            throw new DecodingException("Expected an integer value");
        }
    }

    public static String decodingString(JsonValue value) throws DecodingException {
        if (value instanceof JsonString str) {
            return str.getString();
        } else {
            throw new DecodingException("Expected a string value");
        }
    }

    public static <T> T decodingField(JsonValue value, String field, JsonDecoder<T> decoder) throws DecodingException {
        if (value instanceof JsonObject obj) {
            final var fieldValue = obj.get(field);
            if (fieldValue == null) {
                throw new DecodingException("No field named \"" + field + "\" in the given object");
            }
            return decoder.decode(fieldValue);
        } else {
            throw new DecodingException("Expected a Json Object");
        }
    }

    public static <T> List<T> decodingList(JsonValue value, JsonDecoder<T> decoder) throws DecodingException {
        if (value instanceof JsonArray arr) {
            final var decoded = new ArrayList<T>(arr.size());
            for (var item : arr) {
                decoded.add(decoder.decode(item));
            }
            return decoded;
        } else {
            throw new DecodingException("Expected a Json Array");
        }
    }

    public static <T> T decodingNull(JsonValue value) throws DecodingException {
        if (value == JsonValue.NULL) {
            return null;
        } else {
            throw new DecodingException("Expected null");
        }
    }

    public static <T> T oneOf(JsonValue value, JsonDecoder<T> decoder1, JsonDecoder<T> decoder2) throws DecodingException {
        try {
            return decoder1.decode(value);
        } catch (DecodingException e) {
            return decoder2.decode(value);
        }
    }
}
