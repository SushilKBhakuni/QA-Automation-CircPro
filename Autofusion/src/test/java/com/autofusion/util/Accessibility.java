package com.autofusion.util;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.autofusion.BaseClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Accessibility {

	WebDriver webDriver = BaseClass.getDriver();
	private static final URL scriptUrl = Accessibility.class
			.getResource("/axe.min.js");
	public static List<String> acopErrorList = new ArrayList<String>();
	public List<String> runAcopChecks() {
		testAccessibility();
		/*testAccessibilityWithOptions();
		testAccessibilityWithIncludesAndExcludes();
		testAccessibilityWithSelector();
		testAccessibilityWithWebElement();*/
		return acopErrorList;
	}

	/**
	 * Basic test
	 * 
	 * @return
	 */
	public void testAccessibility() {
		JSONObject responseJSON = new AXE.Builder(webDriver, scriptUrl)
				.analyze();
		String resp = responseJSON.toString();
		JSONArray violations = responseJSON.getJSONArray("violations");
		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(resp);
			String prettyJsonString = gson.toJson(je);
			System.out.println(violations);
			AXE.writeResults(prettyJsonString);
			acopErrorList.add(AXE.report(violations));
		}
	}

	/**
	 * Test with options
	 */
	public void testAccessibilityWithOptions() {
		JSONObject responseJSON = new AXE.Builder(webDriver, scriptUrl)
				.options("{ rules: { 'accesskeys': { enabled: false } } }")
				.analyze();

		JSONArray violations = responseJSON.getJSONArray("violations");
		String resp = responseJSON.toString();
		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(resp);
			String prettyJsonString = gson.toJson(je);
			System.out.println(violations);
			AXE.writeResults(prettyJsonString);
			acopErrorList.add(AXE.report(violations));

		}
	}

	/**
	 * Test a specific selector or selectors
	 */
	public void testAccessibilityWithSelector() {
		JSONObject responseJSON = new AXE.Builder(webDriver, scriptUrl)
				.include("title").include("p").analyze();

		JSONArray violations = responseJSON.getJSONArray("violations");
		String resp = responseJSON.toString();
		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(resp);
			String prettyJsonString = gson.toJson(je);
			System.out.println(violations);
			AXE.writeResults(prettyJsonString);
			acopErrorList.add(AXE.report(violations));
		}
	}

	/**
	 * Test includes and excludes
	 */
	public void testAccessibilityWithIncludesAndExcludes() {
		JSONObject responseJSON = new AXE.Builder(webDriver, scriptUrl)
				.include("div").exclude("h1").analyze();

		JSONArray violations = responseJSON.getJSONArray("violations");
		String resp = responseJSON.toString();
		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(resp);
			String prettyJsonString = gson.toJson(je);
			System.out.println(prettyJsonString);
			AXE.writeResults(prettyJsonString);
			acopErrorList.add(AXE.report(violations));
		}
	}

	/**
	 * Test a WebElement
	 */
	public void testAccessibilityWithWebElement() {
		JSONObject responseJSON = new AXE.Builder(webDriver, scriptUrl)
				.analyze(webDriver.findElement(By.xpath("//img")));

		JSONArray violations = responseJSON.getJSONArray("violations");
		String resp = responseJSON.toString();
		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			try {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonParser jp = new JsonParser();
				JsonElement je = jp.parse(resp);
				String prettyJsonString = gson.toJson(je);
				System.out.println(prettyJsonString);
				AXE.writeResults(prettyJsonString);
				acopErrorList.add(AXE.report(violations));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
