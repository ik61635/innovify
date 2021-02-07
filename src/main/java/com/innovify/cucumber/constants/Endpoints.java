package com.innovify.cucumber.constants;

public class Endpoints {
	public static final String ENDPOINT_GET_PATIENT_MAPPING = "/epm-integration/appointment/getPatientMapping";
	public static final String ENDPOINT_CHECK_SLOT_AVAILABILITY= "/epm-integration/appointment/check-slot-availability";
	public static final String ENDPOINT_CAMPAIGN = "/campaign-service/campaigns";
	public static final String ENDPOINT_CREATE_MAPPER = "/outreach-etl/mapper";
	public static final String ENDPOINT_MAPPER_ENTITY_MAPPING = "/outreach-etl/mapperEntityMapping";
	public static final String ENDPOINT_CAMPAIGN_TRIGGER_IMPORT = "/campaign-service/campaigns/{campaignId}/trigger-import";
	public static final String ENDPOINT_CAMPAIGN_TRIGGER_SEND = "/campaign-service/campaigns/{campaignId}/trigger-send";
	public static final String ENDPOINT_CAMPAIGN_SCHEDULE_JOB = "/campaign-service/campaigns/{campaignId}/schedules";
}
