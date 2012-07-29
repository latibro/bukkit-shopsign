package com.latibro.util;


public class Converter {

	private Converter() {
	}

	/* Numbers */
	
	public static Byte toByte(Object obj) throws NumberFormatException {
		if (obj == null || obj instanceof Byte) {
			return (Byte) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).byteValue();
		} else {
			return Byte.valueOf(toString(obj));
		}
	}

	public static Double toDouble(Object obj) throws NumberFormatException {
		if (obj == null || obj instanceof Double) {
			return (Double) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		} else {
			return Double.valueOf(toString(obj));
		}
	}

	public static Float toFloat(Object obj) throws NumberFormatException {
		if (obj == null || obj instanceof Float) {
			return (Float) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		} else {
			return Float.valueOf(toString(obj));
		}
	}
	
	public static Integer toInteger(Object obj) throws NumberFormatException {
		if (obj == null || obj instanceof Integer) {
			return (Integer) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			return Integer.valueOf(toString(obj));
		}
	}

	public static Long toLong(Object obj) throws NumberFormatException {
		if (obj == null || obj instanceof Long) {
			return (Long) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else {
			return Long.valueOf(toString(obj));
		}
	}

	public static Short toShort(Object obj) throws NumberFormatException {
		if (obj == null || obj instanceof Short) {
			return (Short) obj;
		} else if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		} else {
			return Short.valueOf(toString(obj));
		}
	}

	/* Characters */
	
	public static Character toCharacter(Object obj) throws IllegalArgumentException {
		if (obj == null || obj instanceof Character) {
			return (Character) obj;
		} else {
			String string = toString(obj);
			if (string == null || string.length() != 1) {
				throw new IllegalArgumentException("For obj" + obj);
			}
			return string.charAt(0);
		}
	}

	public static String toString(Object obj) {
		if (obj == null || obj instanceof String) {
			return (String) obj;
		} else {
			return String.valueOf(obj);
		}
	}

	/* Boolean */
	
	public static Boolean toBoolean(Object obj) {
		if (obj == null || obj instanceof Boolean) {
			return (Boolean) obj;
		} else {
			return Boolean.valueOf(toString(obj));
		}
	}
	
}
