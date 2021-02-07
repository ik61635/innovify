package com.innovify.cucumber.weatherApp;

import static com.innovify.utils.ResponseUtils.response;

import java.util.Arrays;
import java.util.List;

import com.innovify.cucumber.constants.Constants;
import com.innovify.utils.CommonUtils;
import com.innovify.utils.PropertyHolder;
import com.innovify.utils.ResponseUtils;
import com.innovify.utils.ReuseableSpecifications;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class StepDef {
	
	@When("^I send the \"([^\"]*)\" request to \"([^\"]*)\" to get current observation$")
	public void i_send_the_request_to_to_get_current_observation(String methodType, String endpoint, DataTable dt) throws Throwable {
		List<List<String>> infoInTheRaw = Arrays.asList(Arrays.asList("desc", "keys", "value"),
				Arrays.asList("Header", "Content-Type", "application/json"),
			    Arrays.asList("PathParam", "lat", dt.raw().get(1).get(0)),
			    Arrays.asList("PathParam", "lon", dt.raw().get(1).get(1)),
			    Arrays.asList("PathParam", "key", "f64ad7b7a039489887c16a31072072eb"));
		PropertyHolder.setProperty(Constants.URL, endpoint);
		DataTable dataTable = DataTable.create(infoInTheRaw);
		ReuseableSpecifications.buildRequest(dataTable);
		response = CommonUtils.doRequest(methodType);
	}

	@Then("^I should see response status code as \"([^\"]*)\"$")
	public void i_should_see_response_status_code_as(int statusCode) {
		ResponseUtils.assertResponseStatusCode(statusCode);
	}
	
	@Then("^I save and print the following values from response$")
	public void i_save_the_following_values_from_response(DataTable dataTable) {
		for (int i = 1; i < dataTable.raw().size(); i++) {
			String jsonpath = PropertyHolder.booleanProperty(dataTable.raw().get(i).get(0))
					? PropertyHolder.getProperty(dataTable.raw().get(i).get(0))
					: dataTable.raw().get(i).get(0);
			PropertyHolder.setProperty(dataTable.raw().get(i).get(1),
					ResponseUtils.getDataFromResponseUsingJsonPath(jsonpath));
			System.out.println(ResponseUtils.getDataFromResponseUsingJsonPath(jsonpath));
		}
	}

	@Then("^I will assert following value from response$")
	public void i_will_assert_following_value_from_response(DataTable dataTable) throws Throwable {
		Assert.assertEquals(ResponseUtils.getDataFromResponseUsingJsonPath(dataTable.raw().get(1).get(0)), dataTable.raw().get(1).get(1));
	}
}
