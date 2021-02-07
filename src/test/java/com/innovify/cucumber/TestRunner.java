package com.innovify.cucumber;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

import com.innovify.testbase.TestBase;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/SecureVideo/WeatherData.feature"})
public class TestRunner extends TestBase {}

