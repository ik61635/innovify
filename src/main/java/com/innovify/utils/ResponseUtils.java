package com.innovify.utils;

import io.restassured.response.Response;

public class ResponseUtils {
	public static Response response;
	
	public static String getDataFromResponseUsingJsonPath(String jsonPath) {
        return response.then().extract().jsonPath().getString(jsonPath);
    }
	
	public static void assertResponseStatusCode(int expectedStatusCode) {
		response.then().statusCode(expectedStatusCode);
	}

}
