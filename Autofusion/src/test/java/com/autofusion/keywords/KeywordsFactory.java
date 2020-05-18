package com.autofusion.keywords;
/**
 * @author nitin.singh
 */
import java.util.HashMap;

import org.apache.log4j.Logger;

public class KeywordsFactory {

	public HashMap<String, Keyword> objKeywordsMap = new HashMap<String, Keyword>();

	public Keyword getInstance(String keywordClass, Logger logger) {
		if (keywordClass == null) {
			return null;
		}

		if (keywordClass.startsWith("type")) {
			if (objKeywordsMap.containsKey("keywordClass")) {
				return objKeywordsMap.get("keywordClass");
			} else {
				Keyword objKeyword = new TypeKeywords(logger);
				objKeywordsMap.put("keywordClass", objKeyword);
				return objKeyword;
			}

		} else if (keywordClass.startsWith("click")) {
			if (objKeywordsMap.containsKey("clickClass")) {
				return objKeywordsMap.get("clickClass");
			} else {
				Keyword objKeyword = new ClickKeywords(logger);
				objKeywordsMap.put("clickClass", objKeyword);
				return objKeyword;
			}

		} else if (keywordClass.startsWith("verify")) {
			if (objKeywordsMap.containsKey("verifyClass")) {
				return objKeywordsMap.get("verifyClass");
			} else {
				Keyword objKeyword = new VerificationKeywords(logger);
				objKeywordsMap.put("verifyClass", objKeyword);
				return objKeyword;
			}

		} else if (keywordClass.startsWith("app")) {
			if (objKeywordsMap.containsKey("appClass")) {
				return objKeywordsMap.get("appClass");
			} else {
				Keyword objKeyword = new ApplicationSpecificKeywords(null, logger);
				objKeywordsMap.put("appClass", objKeyword);
				return objKeyword;
			}

		} else if (keywordClass.startsWith("action")) {
			if (objKeywordsMap.containsKey("actionClass")) {
				return objKeywordsMap.get("actionClass");
			} else {
				Keyword objKeyword = new ActionKeywords(logger);
				objKeywordsMap.put("actionClass", objKeyword);
				return objKeyword;
			}
		} else if (keywordClass.startsWith("select")) {
			if (objKeywordsMap.containsKey("selectClass")) {
				return objKeywordsMap.get("selectClass");
			} else {
				Keyword objKeyword = new SelectKeywords(logger);
				objKeywordsMap.put("selectClass", objKeyword);
				return objKeyword;
			}

		} else if (keywordClass.startsWith("api")) {
			if (objKeywordsMap.containsKey("apiClass")) {
				return objKeywordsMap.get("apiClass");
			} else {
				Keyword objKeyword = new ApiKeywords(logger);
				objKeywordsMap.put("apiClass", objKeyword);
				return objKeyword;
			}

		} else if (keywordClass.startsWith("and")) {
			if (objKeywordsMap.containsKey("androidClass")) {
				return objKeywordsMap.get("androidClass");
			} else {
				Keyword objKeyword = new AndroidKeywords(logger);
				objKeywordsMap.put("androidClass", objKeyword);
				return objKeyword;
			}
		 } else {
			if (objKeywordsMap.containsKey("commonClass")) {
				return objKeywordsMap.get("commonClass");
			} else {
				Keyword objKeyword = new CommonKeywords(logger);
				objKeywordsMap.put("commonClass", objKeyword);
				return objKeyword;
			}
		}

	}

}
