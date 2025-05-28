package ru.netology;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.Visible;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MeetingTest {

    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterCityInput() {
        String planingDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("[data-test-id=city] input").shouldBe(visible, enabled).setValue("Казань");
        $("[data-test-id=date] input").
                sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id=name] input").setValue("Хафизов Ленар");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(text("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + planingDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldRegisterCityList() {
        String planingDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("[data-test-id=city] input").shouldBe(visible, enabled).setValue("Каз");
        $$(".menu-item").findBy(text("Казань")).shouldBe(visible).click();
        $("[data-test-id=date] input").
                sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id=name] input").setValue("Хафизов Ленар");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(text("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + planingDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

}
