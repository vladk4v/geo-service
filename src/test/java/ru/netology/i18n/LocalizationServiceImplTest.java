package ru.netology.i18n;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {
	LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

	@Test
	void locale() {
		assertEquals("Добро пожаловать", localizationService.locale(Country.RUSSIA));
		assertEquals("Welcome", localizationService.locale(Country.USA));
	}
}