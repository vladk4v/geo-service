package ru.netology.geo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {
	GeoService geoService = new GeoServiceImpl();

	@ParameterizedTest
	@ValueSource(strings = {GeoServiceImpl.MOSCOW_IP, "172.0.32.11", "172.",
			GeoServiceImpl.NEW_YORK_IP, "96.44.183.149", "96.",
			GeoServiceImpl.LOCALHOST})
	void byIp(String ip) {
		if (ip.startsWith("172.")) {
			assertEquals("Moscow", geoService.byIp(ip).getCity());
			assertEquals(Country.RUSSIA, geoService.byIp(ip).getCountry());
		} else if (ip.startsWith("96.")) {
			assertEquals("New York", geoService.byIp(ip).getCity());
			assertEquals(Country.USA, geoService.byIp(ip).getCountry());
		} else if (ip.equals(GeoServiceImpl.LOCALHOST)) {
			assertNull(geoService.byIp(ip).getCity());
		}
	}

	@Test
	void byCoordinates() {
		Throwable ex = assertThrows(RuntimeException.class, () -> geoService.byCoordinates(25.0, 25.0));
		assertEquals("Not implemented", ex.getMessage());
	}
}