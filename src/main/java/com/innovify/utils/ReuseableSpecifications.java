package com.innovify.utils;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.config.EncoderConfig.encoderConfig;

import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;

import cucumber.api.DataTable;

public class ReuseableSpecifications {
  public static RequestSpecBuilder rspec;
  
  public static RequestSpecification requestSpecification;
  
  public static ResponseSpecBuilder respec;
  
  public static ResponseSpecification responseSpecification;
  
  

public static RequestSpecification buildRequest(DataTable table) {
	rspec = new RequestSpecBuilder();
	rspec.addHeaders(CommonUtils.setValuesInMap(
            		CommonUtils.getMapFromDataTableUsingKey(table, "header")));
	rspec.addFormParams(CommonUtils.setValuesInMap(
            		CommonUtils.getMapFromDataTableUsingKey(table, "formParam")));
	rspec.addPathParams(CommonUtils.setValuesInMap(
            		CommonUtils.getMapFromDataTableUsingKey(table, "pathParam")));
	rspec.addQueryParams(CommonUtils.setValuesInMap(
            		CommonUtils.getMapFromDataTableUsingKey(table, "queryParam")));
	rspec.setUrlEncodingEnabled(CommonUtils.getEncodingStatus(table) == null ? false : true);
	rspec.setRelaxedHTTPSValidation();
	rspec.setConfig(RestAssured.config().encoderConfig(encoderConfig()
                    .appendDefaultContentCharsetToContentTypeIfUndefined(
                            false)));
	requestSpecification = rspec.build();
    return requestSpecification;
}
}

