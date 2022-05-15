package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MessageSenderImplTest {

	private Location locationRussia = new Location("Moscow", Country.RUSSIA, null, 0);
	private Location locationUSA = new Location("New York", Country.USA, null, 0);
	private Map<String, String> ipAddresses = new HashMap<>();

	static Stream<Arguments> input() {
		return Stream.of(
				arguments(Country.RUSSIA, "Добро пожаловать", "172.0.32.11"),
				arguments(Country.RUSSIA, "Добро пожаловать", "172."),
				arguments(Country.USA, "Welcome", "96.44.183.149"),
				arguments(Country.USA, "Welcome", "96.")
		);
	}

	@ParameterizedTest
	@MethodSource("input")
	void testSend(Country country, String welcome, String ip) {

		//Mock for GeoService
		GeoService geoService = Mockito.mock(GeoService.class);

		Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
				.thenReturn(locationRussia);
		Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
				.thenReturn(locationUSA);

		//Mock for LocalizationService
		LocalizationService localizationService = Mockito.mock(LocalizationService.class);

		Mockito.when(localizationService.locale(country))
				.thenReturn(welcome);

		//Создать объект
		MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

		//положить значение в Map
		ipAddresses.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

		//проверить результат
		assertEquals(welcome, messageSender.send(ipAddresses));

	}
}