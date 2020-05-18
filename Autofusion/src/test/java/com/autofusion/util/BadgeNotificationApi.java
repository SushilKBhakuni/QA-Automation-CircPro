package com.autofusion.util;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;


public class BadgeNotificationApi {

	private String getTokenUrl = "http://tst-piapi-internal.dev-openclass.com/tokens";
	private String generateEventUrl = "https://notifications-api-qa.dev-prsn.com/events";
	String value;

	public void badgeNotificationTrigsted() {
		getToken();
		generateEventTrigsted();
	}

	public void badgeNotificationCicarelli() {
		getToken();
		generateEventCicarelli();
	}

	public String getToken() {
		try {
			Response res = given().contentType("application/json")
					.body("{\n\"userName\":\"globallogic_system\",\n\"password\":\"fHdM0m9zPFIceKZ1GPhy7Qqq1mg0sJ69\"\n}")
					.post(getTokenUrl);
			this.value = res.then().extract().body().jsonPath().getString("data").toString();
			System.out.println("Extracted token:- " + this.value);
			return value;
		} catch (Throwable e) {
			return null;
		}

	}

	public void generateEventTrigsted() {
		try {
			Response res = given().contentType("application/json").header("x-Authorization", value)
					.body("{\n  \"appType\":\"poc\",\n  \"productType\":\"GlobalLogic\",\n  \"eventType\":\"assignments.reminder\",\n  \"recipientIds\":\n  [\n    \"580867559b0d5f6ad588f03a\"\n  ],\n  \"eventModel\":\n  {  \n    \"user\":\"Jayant\",\n    \"data\": \"{\\\"chapterName\\\":\\\"Ch1 Quiz - College Algebra 3E\\\",\\\"points\\\":\\\"5\\\",\\\"startQuizUrl\\\":\\\"\\\\/lm-learnerCourse\\\\/lms\\\\/v1\\\\/assets\\\\/ASSESMENT-1?learnerId=46456450&amp;courseId=TRIGSTED-1&amp;q=SELF\\\",\\\"dueDate\\\":\\\"Sun Jan 15, 2017\\\",\\\"heading\\\":\\\"Quiz Reminder\\\",\\\"productId\\\":\\\"TRIGSTED\\\",\\\"courseName\\\":\\\"College Algebra 3E\\\"}\"\n  }\n}")
					.post(generateEventUrl);
			
			System.out.println("Response for Trigested Event API:- "+ res.asString());
			System.out.println("Status for Trigested Event API:- "+ res.getStatusLine());
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}

	}

	public void generateEventCicarelli() {
		try {
			Response res = given().contentType("application/json").header("x-Authorization", value)
					.body("{\r\n  \"appType\":\"poc\",\r\n  \"productType\":\"GlobalLogic\",\r\n  \"eventType\":\"assignments.reminder\",\r\n  \"recipientIds\":\r\n  [\r\n    \"580867559b0d5f6ad588f03a\"\r\n  ],\r\n  \"eventModel\":\r\n  {  \r\n    \"user\":\"Jayant\",\r\n    \"data\": \"{\\\"chapterName\\\":\\\"Chapter 1 Sensation and Perception\\\",\\\"points\\\":\\\"18\\\",\\\"startQuizUrl\\\":\\\"\\\\/lm-learnerCourse\\\\/lms\\\\/v1\\\\/assets\\\\/READING\\\\/assetGraph\\\\/next?learnerId=580867559b0d5f6ad588f03a&amp;courseId=PSYCHOLOGY-1&amp;q=SELF\\\",\\\"dueDate\\\":\\\"Sun Jan 15, 2017\\\",\\\"heading\\\":\\\"Quiz Reminder\\\",\\\"productId\\\":\\\"Ciccarelli\\\",\\\"courseName\\\":\\\"The ABCs of Perception\\\"}\"\r\n  }\r\n}")
					.post(generateEventUrl);

			System.out.println("Response for Cicarelli Event API:- " + res);
			System.out.println("Status for Cicarelli Event API:- " + res.getStatusLine());
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}

	}
}
