package com.latibro.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class ConverterTest {

	@Test(expected = IllegalArgumentException.class)
	public void toCharacter_EmptyString() throws Exception {
		Integer.valueOf("");
	}

}
