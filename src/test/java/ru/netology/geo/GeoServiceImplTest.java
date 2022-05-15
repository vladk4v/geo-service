package ru.netology.geo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GeoServiceImplTest {

	private GeoService geoService = new GeoServiceImpl();

	static Stream<Arguments> input() {
		return Stream.of(
				arguments("172.0.32.11", "Moscow", Country.RUSSIA),
				arguments("96.44.183.149", "New York", Country.USA),
				arguments("172.", "Moscow", Country.RUSSIA),
				arguments("96.", "New York", Country.USA)
		);
	}

	@ParameterizedTest
	@MethodSource("input")
	void testByIp(String ip, String city, Country country) {

		System.out.println("Your city is: " + city);
		assertEquals(city, geoService.byIp(ip).getCity());

		System.out.println("Your country is: " + country);
		assertEquals(country, geoService.byIp(ip).getCountry());
	}

	@Test
	void testByCoordinates() {
		Throwable ex = assertThrows(RuntimeException.class, () -> geoService.byCoordinates(25.0, 25.0));
		assertEquals("Not implemented", ex.getMessage());
	}
}