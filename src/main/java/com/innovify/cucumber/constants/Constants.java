package com.innovify.cucumber.constants;

public class Constants {
    public static final String REQUEST_JSON = "requestJson";
	public static final String URL = "url";
	public static final String CAMPAIGN_INSTANCE = "SELECT * FROM db_campaign_staging.campaign_instances WHERE campaign_id = 'campaignId';";
	public static final String CAMPAIGN_INTSTANCE_NO_OF_PATIENT = "SELECT COUNT(*) AS patient_records FROM db_campaign_staging.patient_records AS PR JOIN db_campaign_staging.campaign_instances AS CI ON CI.id = PR.campaign_instance_id WHERE CI.campaign_id = 'campaignId';";
	public static final String EMAIL_TEXT_NOTIFICATION_STATUS = "SELECT ND.* FROM db_campaign_staging.patient_records AS PR JOIN db_campaign_staging.campaign_instances AS CI ON PR.campaign_instance_id = CI.id JOIN db_outreach_notification_staging.notification_detail AS ND ON PR.id = ND.entity_type_identifier_value WHERE CI.campaign_id = 'campaignId' AND ND.notification_type = 'notificationType';";
	public static final String EMAIL_TEXT_NOTIFICATION_OFF_STATUS = "SELECT COUNT(*) AS data FROM db_campaign_staging.patient_records AS PR JOIN db_campaign_staging.campaign_instances AS CI ON PR.campaign_instance_id = CI.id JOIN db_outreach_notification_staging.notification_detail AS ND ON PR.id = ND.entity_type_identifier_value WHERE CI.campaign_id = 'campaignId' AND ND.notification_type = 'notificationType';";
	public static final String PATIENT_RECORD = "SELECT PR.* FROM db_campaign_staging.patient_records AS PR JOIN db_campaign_staging.campaign_instances AS CI ON CI.id = PR.campaign_instance_id WHERE CI.campaign_id = 'campaignId';";
	public static final String UPDATE_CAMPAIGN_INSTANCE = "UPDATE db_campaign_staging.campaign_instances SET keysToBeUpdated WHERE campaign_id = 'campaignId';";
	public static final String CAMPAIGN_SCHEDULER = "SELECT COUNT(*) AS data FROM db_campaign_staging.campaign_scheduler_audit WHERE campaign_id = 'campaignId' AND schedule_type = 'jobType';";
	public static final String TRIGGERED_NOTIFICATION_SUBJECT = "Campaign campaignName type Triggered";
	public static final String COMPLETION_NOTIFICATION_SUBJECT = "Campaign campaignName type Completed Successfully";
	public static final String FAILED_NOTIFICATION_SUBJECT = "Campaign campaignName type Failed";
	public static final String HOLD_NOTIFICATION_SUBJECT = "Campaign campaignName type Put on Hold";
	public static final String IMPORT_NOTIFICATION_TRIGGERED_CONTENT = "Hello Campaign Managers,We have triggered the reading of the Patient File for the Campaign with following details:Organization Name: orgNameCampaign Name: campNameRegards,DocASAP Team";
	public static final String IMPORT_NOTIFICATION_SUCCESSFUL_CONTENT = "Hello Campaign Managers,Reading of the Patient File for the Campaign is successful. Here are the details:Organization Name: orgNameCampaign Name: campNameNumber of Patient Records added: noOfPatientRegards,DocASAP Team";
	public static final String IMPORT_NOTIFICATION_FAILED_CONTENT = "Hello Campaign Managers,Reading of the Patient File for the Campaign failed. Here are the details:Organization Name: orgNameCampaign Name: campNameReason of Failure: failureReasonRegards,DocASAP Team";
	public static final String SEND_NOTIFICATION_TRIGGERED_CONTENT = "Hello Campaign Managers,Send notifications for the Campaign has been triggered. Here are the details:Organization Name: orgNameCampaign Name: campNameRegards,DocASAP Team";
	public static final String SEND_NOTIFICATION_SUCCESSFUL_CONTENT = "Hello Campaign Managers,Sending notifications for the Campaign is successful. Here are the details:Organization Name: orgNameCampaign Name: campNameRegards,DocASAP Team";
	public static final String SEND_NOTIFICATION_FAILED_CONTENT = "Hello Campaign Managers,Send notifications for the Campaign failed. Here are the details:Organization Name: orgNameCampaign Name: campNameReason of Failure: failureReasonRegards,DocASAP Team";
	public static final String SEND_NOTIFICATION_HOLD_CONTENT = "Hello Campaign Managers,Send notifications for the Campaign has been triggered but waiting for Import Patient to complete. Here are the details:Organization Name: orgNameCampaign Name: campNameRegards,DocASAP Team";
	public static final String DELETE_PATIENT_RECORD = "DELETE db_campaign_staging.patient_records FROM db_campaign_staging.patient_records JOIN db_campaign_staging.campaign_instances ON db_campaign_staging.campaign_instances.id = db_campaign_staging.patient_records.campaign_instance_id WHERE db_campaign_staging.campaign_instances.campaign_id = 'campaignId';";
	public static final String GET_MEETING = "SELECT * FROM database.tb_tele_user_meeting WHERE meeting_id = 'meetingId';";
	public static final String GET_PROVIDER = "SELECT * FROM database.tb_tele_user_account WHERE da_id = 'providerId';";
}
