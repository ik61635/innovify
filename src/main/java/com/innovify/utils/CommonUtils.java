package com.innovify.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovify.cucumber.constants.Constants;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import cucumber.api.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

public class CommonUtils {

	
	public static String PhoneNumberGenerator()
    {
        int num1, num2, num3; //3 numbers in area code
        int set2, set3; //sequence 2 and 3 of the phone number
        String number;
        
        Random generator = new Random();
        
        //Area code number; Will not print 8 or 9
        num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin  
        num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
        num3 = generator.nextInt(8);
        
        // Sequence two of phone number
        // the plus 100 is so there will always be a 3 digit number
        // randomize to 643 because 0 starts the first placement so if i randomized up to 642 it would only go up yo 641 plus 100
        // and i used 643 so when it adds 100 it will not succeed 742 
        set2 = generator.nextInt(643) + 100;
        
        //Sequence 3 of numebr
        // add 1000 so there will always be 4 numbers
        //8999 so it wont succed 9999 when the 1000 is added
        set3 = generator.nextInt(8999) + 1000;
        
        number =  ( "" + num1 + "" + num2 + "" + num3 + "" + "" + set2 + "" + set3 );        
		return number;
        
    }
	
	public static String readJson(String jsonName) {
		String jsonString = null;
		try {
			InputStream file = new FileInputStream(
					"src/test/resources/configFiles/jsonFiles/RequestJson/" + jsonName + ".json");
			jsonString = IOUtils.toString(file, "UTF-8");
		} catch (Exception e) {
			assertValues("Error while reading json file: " + jsonName + " " + e.getMessage(), true);
		}
		return jsonString;
	}

	public static String updateRequestBody(DataTable table, String jsonBody) {
		for (int i = 0; i < table.raw().size(); i++) {
			switch (table.raw().get(i).get(0).toLowerCase()) {
			case "key to be updated": {
				jsonBody = keyToBeUpdatedInJson(jsonBody, table.raw().get(i).get(1), table.raw().get(i).get(2));
				break;
			}
			case "key to be added": {
				jsonBody = keyToBeAddedInJson(jsonBody, table.raw().get(i).get(1), table.raw().get(i).get(2));
				break;
			}
			case "key to be removed": {
				jsonBody = keyToBeRemovedInJson(jsonBody, table.raw().get(i).get(1));
				break;
			}
			}
		}
		return jsonBody;
	}

	public static String keyToBeRemovedInJson(String json, String jsonPath) {
		DocumentContext updateJson = null;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
			updateJson = JsonPath.parse(document)
					.delete(PropertyHolder.booleanProperty(jsonPath) ? PropertyHolder.getProperty(jsonPath) : jsonPath);
		} catch (Exception e) {
			assertValues("Error while removing key: " + jsonPath + ", " + e.getMessage(), false);
		}
		return updateJson.jsonString();
	}

	public static String keyToBeAddedInJson(String json, String jsonPath, String keyValue) {
		DocumentContext updateJson = null;
		try {

			Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
			Object addObject = Configuration.defaultConfiguration().jsonProvider()
					.parse(checkIfGivenStringIsSomeKeyword(keyValue.split("-")[1]));
			updateJson = JsonPath.parse(document).put(jsonPath, keyValue.split("-")[0], addObject);
		} catch (Exception e) {
			assertValues("Error while adding keyValue: " + keyValue + ", " + e.getMessage(), false);
		}
		return updateJson.jsonString();
	}

	public static String keyToBeUpdatedInJson(String json, String jsonPath, String valueToBeUpdated) {
		DocumentContext updateJson = null;
		boolean bool = false;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
			bool = valueToBeUpdated.contains("-") && !valueToBeUpdated.contains("Date");
			valueToBeUpdated = checkIfGivenStringIsSomeKeyword(
					bool ? valueToBeUpdated.split("-")[1] : valueToBeUpdated);
			jsonPath = PropertyHolder.booleanProperty(jsonPath) ? PropertyHolder.getProperty(jsonPath) : jsonPath;
			Object addObject = Configuration.defaultConfiguration().jsonProvider().parse(valueToBeUpdated);
			updateJson = JsonPath.parse(document).set(jsonPath, bool ? addObject : String.valueOf(addObject));
		} catch (Exception e) {
			assertValues("Error while updating key: " + jsonPath + ", " + e.getMessage(), false);
		}
		return updateJson.jsonString();
	}
	
	public static String getKeyValueFromJsonUsingJsonPath(String json,
            String jsonPath) {
        String updateJson = null;
        try {
            Object document = Configuration.defaultConfiguration()
                    .jsonProvider().parse(json);
            updateJson = JsonPath.parse(document)
                    .read(PropertyHolder.getProperty(jsonPath) == null ? jsonPath
                            : PropertyHolder.getProperty(jsonPath)).toString();
        } catch (Exception e) {
        	assertValues("Error while fetching key: " + jsonPath + ", " + e.getMessage(), false);
        }
        return updateJson;
    }

	public static String checkIfGivenStringIsSomeKeyword(String valueToBeUpdated) {
		String updatedValue = "";
		if(valueToBeUpdated.equalsIgnoreCase("RandomName")) {
		//	updatedValue =  getRandomString(10);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomPatientId")) {
			//updatedValue =  getRandomNumeric(7);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomMobNum")) {
			updatedValue =  randomUsPhoneNumberGenerator();
			PropertyHolder.setProperty("PREV_NUM", updatedValue);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomDob")) {
		//	updatedValue =  getRandomNumeric(10);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomEmail")) {
			//updatedValue =  getRandomString(10).concat(".auto@docasap.com");
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomGender")) {
			updatedValue =  getRandomGender();
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomString")) {
			//updatedValue =  getRandomString(8);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("RandomNumber")) {
		//	updatedValue =  getRandomNumeric(10);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("NextHourTimeInMilliSecond")) {
			updatedValue =  String.valueOf(System.currentTimeMillis() + 3600000);
		}
		else if(valueToBeUpdated.equalsIgnoreCase("CampaignName")) {
			updatedValue =  "Automation Campaign - " + PropertyHolder.getProperty("FILE_NAME");
		}
		else if(valueToBeUpdated.equalsIgnoreCase("CampaignCode")) {
			updatedValue =  "AutomationCampaign_" + PropertyHolder.getProperty("FILE_NAME");
		}
		else if(valueToBeUpdated.contains("Date")) {
			//updatedValue =  getDateInYYYYMMDD(valueToBeUpdated);
		}
		else {
			updatedValue = PropertyHolder.booleanProperty(valueToBeUpdated) ? PropertyHolder.getProperty(valueToBeUpdated)
					: valueToBeUpdated;
		}
 		return updatedValue;
	}



	public static String randomUsPhoneNumberGenerator() {
		StringBuffer sb = new StringBuffer(10);
		String code = "301";
		sb.append(code);
		for (int i = 0; i < 3; i++) {
			sb.append(String.valueOf(new Random().nextInt(7) + 2));
		} 
		for (int i = 0; i < 4; i++) {
		//	int ndx = (int)(Math.random() * NUMERICS.length());
		//	sb.append(NUMERICS.charAt(ndx));
		} 
		return sb.toString();
	}

	public static String getRandomGender() {
		String []array = {"M","F","U"};
		int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}

	public static void putVariablesInMap(Class clz) throws Exception {
		Field[] fields = clz.getFields();
		for (Field field : fields) {
			PropertyHolder.setProperty(field.getName(), (String) field.get(field));
		}
	}
	
	public static Map<String, String> setValuesInMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String updatedValue = checkIfGivenStringIsSomeKeyword(
                    entry.getValue());
            map.put(entry.getKey(), updatedValue);
        }
        return map;
    }
	
	public static Map<String, String> getMapFromDataTableUsingKey(DataTable table, String key) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < table.raw().size(); i++) {
			if (table.raw().get(i).get(0).equalsIgnoreCase(key)) {
				map.put(table.raw().get(i).get(1), checkIfGivenStringIsSomeKeyword(table.raw().get(i).get(2)));
			}
		}
		return map;
	}

	public static String getEncodingStatus(DataTable table) {
		for (int i = 0; i < table.raw().size(); i++) {
            if (table.raw().get(i).get(0).equalsIgnoreCase("urlEncoding")) {
                return  table.raw().get(i).get(1);
            }
        }
        return null;
	}
	
	public static Response doRequest(String methodType) {
		RequestSpecification reqSpec = SerenityRest.given().spec(ReuseableSpecifications.requestSpecification);
		switch (methodType.toLowerCase()) {
		case "get":
			return reqSpec.get(PropertyHolder.getProperty(Constants.URL));
		case "post":
			return reqSpec.body(PropertyHolder.getProperty(Constants.REQUEST_JSON))
					.post(PropertyHolder.getProperty(Constants.URL));
		case "put":
			return reqSpec.body(PropertyHolder.getProperty(Constants.REQUEST_JSON))
					.put(PropertyHolder.getProperty(Constants.URL));
		case "delete":
			return reqSpec.delete(PropertyHolder.getProperty(Constants.URL));
		case "patch":
			return reqSpec.patch(PropertyHolder.getProperty(Constants.URL));
		default:
			assertValues("Invalid method type passed: " + methodType, false);
			return null;
		}
	}
	

	
	public static void assertValues(String message, boolean bool) {
		Assert.assertTrue(message, bool);
	}

	


	
}