package dev.mccue.json_serde;

import javax.json.JsonValue;

public interface JsonEncoder<T> {
    JsonValue encode(T t);
}
