package com.latibro.bukkit.configuration.serialization;

import java.util.Map;

public abstract class AbstractSerializationHelper<T> {

	public AbstractSerializationHelper() {
		super();
	}

	public abstract T deserialize(Map<String, Object> map);

	public abstract Map<String, Object> serialize(T obj);
	

	private String toString(Object obj) {
		if (obj == null || obj instanceof String) {
			return (String) obj;
		} else {
			return String.valueOf(obj);
		}
	}

	protected Double toDouble(Object obj) {
		if (obj == null || obj instanceof Double) {
			return (Double) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		} else {
			return Double.valueOf(toString(obj));
		}
	}

	protected Float toFloat(Object obj) {
		if (obj == null || obj instanceof Float) {
			return (Float) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		} else {
			return Float.valueOf(toString(obj));
		}
	}

}
