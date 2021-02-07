@WeatherData
Feature: Weather Data

  Scenario: Returns a Current Observation - Given a lat/lon
    When I send the "get" request to "/current?lat={lat}&lon={lon}&key={key}" to get current observation
      | lat    | lon    |
      | 11     | -11 |
    Then I should see response status code as "200"
    And I save and print the following values from response
      | jsonpath           | key        |
      |data[0].timezone | TIMEZONE |
      |data[0].wind_dir |WIND_DIRECTION|
      |data[0].sunrise |SUNRISE|
      |data[0].sunset |SUNSET|
      |data[0].city_name|CITYNAME|
      |data[0].country_code|COUNTRY_CODE|
    And I will assert following value from response
      | jsonpath           | value        |
      |data[0].timezone | Africa/Conakry |
