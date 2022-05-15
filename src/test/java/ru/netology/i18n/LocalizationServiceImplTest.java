package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LocalizationServiceImplTest {

	private LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

	static Stream<Arguments> input() {
		return Stream.of(
				arguments(Country.RUSSIA, "Добро пожаловать"),
				arguments(Country.USA, "Welcome"),
				arguments(Country.BRAZIL, "Welcome"),
				arguments(Country.GERMANY, "Welcome")
		);
	}

	@ParameterizedTest
	@MethodSource("input")
	void testLocale(Country country, String welcome) {
		assertEquals(welcome, localizationService.locale(country));
	}
}