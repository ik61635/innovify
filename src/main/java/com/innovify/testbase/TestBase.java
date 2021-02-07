package com.innovify.testbase;

import com.innovify.cucumber.constants.Constants;
import com.innovify.cucumber.constants.Endpoints;
import com.innovify.cucumber.constants.Jsonpaths;
import com.innovify.utils.CommonUtils;

import io.restassured.RestAssured;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestBase {
  
  @BeforeClass
  public static void setUp() throws Exception {
 
    RestAssured.baseURI = "https://api.weatherbit.io/v2.0/";
	CommonUtils.putVariablesInMap(Endpoints.class);
	CommonUtils.putVariablesInMap(Jsonpaths.class);
	CommonUtils.putVariablesInMap(Constants.class);
  }
  
  @AfterClass
  public static void tearDown() {

   }
  
}
