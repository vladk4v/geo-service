package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderImplTest {
	@Mock
	Location locationRussia = new Location("Moscow", Country.RUSSIA, null, 0);
	Location locationUSA = new Location("New York", Country.USA, null, 0);
	String privet = "Добро пожаловать";
	String welcome = "Welcome";
	Map<String, String> ipAddresses = new HashMap<>();

	@ParameterizedTest
	@ValueSource(strings = {"172.0.32.11", "172.", GeoServiceImpl.MOSCOW_IP,
			GeoServiceImpl.NEW_YORK_IP, "96.44.183.149", "96."})
	void send(String string) {

		//Mock for GeoService
		GeoService geoService = Mockito.mock(GeoService.class);

		Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
				.thenReturn(locationRussia);
		Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
				.thenReturn(locationUSA);

		//Mock for LocalizationService
		LocalizationService localizationService = Mockito.mock(LocalizationService.class);

		Mockito.when(localizationService.locale(Country.RUSSIA))
				.thenReturn(privet);
		Mockito.when(localizationService.locale(Country.USA))
				.thenReturn(welcome);

		//Создать объект
		MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

		//положить значение в Map
		ipAddresses.put(MessageSenderImpl.IP_ADDRESS_HEADER, string);

		//проверить результат
		if (string.startsWith("172.")) {
			assertEquals(privet, messageSender.send(ipAddresses));
		} else if (string.startsWith("96.")) {
			assertEquals(welcome, messageSender.send(ipAddresses));
		}
	}
}