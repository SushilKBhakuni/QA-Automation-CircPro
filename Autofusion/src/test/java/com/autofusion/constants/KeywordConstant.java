package com.autofusion.constants;
/**
 * @author nitin.singh
 */
public interface KeywordConstant {
	public String ELEMENT_INPUT_VALUE = "InputValue";
	public String ELEMENT_LOCATOR = "ElementId";
	public String COMPONENT_NAME = "ComponentName";
	public String ACTION_TO_PERFORM = "ActionName";
	public String STEP_DESCRIPTION = "StepDescription";
	public String ELEMENT_LOCATOR1 = "ElementId1";
	public String ACTION_SWITCH_TO_FRAME = "switchToFrame";
	public String ACTION_GENERATE_STRING = "randomstring";
	/****************** Type ****************************/
	
	public String ACTION_TYPE = "type";
	public String ACTION_TYPE_BY_JS = "typeByJs";
	public String ACTION_TYPE_AFTER_CLICK_VIA_JS = "typeAfterClickViaJs";
	public String ACTION_TYPE_AFTER_CLEAR = "typeAfterClear";
	public String ACTION_TYPE_AFTER_CLICK = "typeAfterClick";
	public String ACTION_TYPE_VIA_JS = "typeViaJs";
	public String ACTION_VERIFY_IMAGES = "verifyImages";
	public String ACTION_VERIFY_VIDEOS = "verifyvideosPlayback";
	/****************** Click ****************************/
	public String ACTION_CLICK = "click";
	public String ACTION_CLICK_ON_TEXT = "clickOnText";
	public String ACTION_CLICK_INDEX_POSITION = "clickIndexPosition";
	public String ACTION_CLICK_AND_WAIT = "clickAndWait";
	public String ACTION_CLICK_DOUBLE = "clickDouble";
	public String ACTION_CLICK_BY_JS = "clickByJs";
	public String ACTION_CLICK_HOLDING_MODIFIER_KEY = "clickHoldingModifierKey";
	public String ACTION_CLICK_CHECKBOX_LIST = "clickCheckBoxInList";
	public String ACTION_CLICK_LINK = "clickLink";
	public String ACTION_CLICK_CONTENT_DESC_FROM_LIST = "clickContentDescFromList";

	
	/****************** Other ****************************/
	
	public String ACTION_GET_LIST_ITEM_ONINDEX = "getListItemOnIndex";
	public String ACTION_GET_TEXT = "getText";
	public String ACTION_GET_TITLE = "getTitle";
	public String ACTION_GET_LIST_ITEMS = "getListItems";
	public String ACTION_GET_SUBSTRING = "getSubstring";
	public String ACTION_GET_LISTSUBSTRING = "getListSubstring";
	public String ACTION_MOUSEHOVER_MOVETOELEMENT = "mouseHoverOnMoveToElement";

	/****************** Verify ****************************/

	public String ACTION_VERIFY_TITLE = "verifyTitle";
	public String ACTION_VERIFY_TEXT_IN_LIST = "verifyTextContainsInList";
	public String ACTION_SCROLL_TO_GIVEN_ELEMENT = "andScrollToElementGiven";// Android
	public String ACTION_SCROLL_TO_EXACT = "andScrollToExact";// Android
	public String ACTION_SCROLL_TO_GIVEN_ELEMENT_MBL = "andScrollToElementGivenMbl";// Android
	public String ACTION_SCROLL_TO = "scrollTo";
	public String ACTION_VERIFY_ELEMENT_PRESENT = "verifyElementPresent";

	public String ACTION_VERIFY_TEXT = "verifyText";
	public String ACTION_VERIFY_NOT_VISIBLE = "verifyNotVisible";
	public String ACTION_VERIFY_ALERT_PRESENT = "verifyAlertPresent";
	public String ACTION_VERIFY_ALERT_NOT_PRESENT = "verifyAlertNotPresent";
	public String ACTION_VERIFY_TEXT_PRESENT = "verifyTextPresent";
	public String ACTION_VERIFY_SELECTED_VALUE = "verifySelectedValue";
	public String ACTION_VERIFY_ELEMENT_NOT_PRESENT = "verifyElementNotPresent";
	public String ACTION_VERIFY_NOT_EMPTY = "verifyNotEmpty";
	public String ACTION_VERIFY_EMPTY = "verifyEmpty";
	public String ACTION_VERIFY_HIGHLIGHT_ELEMENT_BY_STYLE = "verifyHighLightElementByStyle";
	public String ACTION_VERIFY_ATTRIBUTE_VALUE = "verifyAttributeValue";
	public String ACTION_VERIFY_TEXT_CONTAINS = "verifyTextContains";
	public String ACTION_VERIFY_INPUT_TEXT = "verifyInputText";
	public String ACTION_VERIFY_IS_LINK_BROKEN = "verifyIsLinkBroken";
	public String ACTION_VERIFY_TEXT_CONTAINS_IN_LIST = "verifyTextContainsInList";
	public String ACTION_VERIFY_TEXT_CONTAINS_IN_LIST_BY_INDEX = "verifyTextContainsInListByIndex";
	public String ACTION_VERIFY_ISSELECTED = "verifyIsSelected";
	public String ACTION_VERIFY_ISDISPLAYED = "verifyisDispalyed";
	public String ACTION_VERIFY_ITALIC = "verifyisItalic";
	public String ACTION_VERIFY_FONT = "verifyFontWeight";
	public String ACTION_VERIFY_DATE = "verifyDate";
	public String ACTION_VERIFY_ISENABLED = "verifyisEnabled";
	public String ACTION_VERIFY_ISCLICKABLE_IN_LIST = "verifyIsClickableForList";
	public String ACTION_VERIFY_ISBOLD = "verifyisBold";
	public String ACTION_VERIFY_TEXT_COLOUR = "verifyTextColour";
	public String ACTION_VERIFY_ISDISPLAYED_IN_LIST_BY_INDEX = "verifyIsDisplayedInListByIndex";
	public String ACTION_VERIFY_ELEMENT_IS_NOT_EDITABLE = "verifyIsElementEditable";
	public String ACTION_VERIFY_DROPDOWNOPTIONS = "verifyDropDownOptions";
	public String ACTION_VERIFY_IS_ELEMENT_FOCUSED = "verifyIsElementFocused";
	 public String ACTION_VERIFY_BACKGROUND_COLOR = "verifyBackgroundColor";
	/****************** select ****************************/

	public String ACTION_SELECT_INTEGER_VALUE = "selectIntegerValue";
	public String ACTION_SELECT = "select";
	public String ACTION_SELECT_DROPDOWN_VISIBLE_TEXT = "selectDropdownVisibleText";
	public String ACTION_SELECT_DROPDOWN_VALUE = "selectDropdownValue";
	public String ACTION_SELECT_TYPE = "selectType";
	public String ACTION_SELECT_BY_VISIBLE_TEXT = "selectByVisibleText";
	public String ACTION_SELECT_VALUE_FROM_LIST = "selectvalueFromlist";

	public String ACTION_MOVE_TO_ELEMENT = "actionMoveToElement";
	// public String ACTION_MOUSE_HOVER = "mouseHover";
	public String ACTION_NAVIGATE_BROWSER_BACK = "navigateBrowserBack";
	public String ACTION_NAVIGATE_URL = "navigateToUrl";
	public String ACTION_MOUSE_HOVER = "mouseHoverOnElement";

	public String ACTION_EXECUTE_AUTOIT_SCRIPT = "executeAutoItScript";
	//public String ACTION_SWITCH_TO_FRAME = "switchToFrame";
	public String ACTION_ADD_DELAY = "addDelay";
	public String ACTION_UPLOAD_FILE = "uploadFile";
	public String ACTION_SWITCH_BROWSER_WINDOW = "switchBrowserWindow";
	public String ACTION_CLOSE_BROWSER = "closeBrowser";
	public String ACTION_ACCEPT_ALERT = "acceptAlert";
	public String ACTION_SAVE_UI_VALUE = "saveUiValue";
	public String ACTION_COMPARE_SAVED_VALUE = "compareSavedValue";
	public String ACTION_ENTER_KEY = "enterKey";
	public String ACTION_APPLY_IMPLICIT_WAIT = "implicitWait";
	public String ACTION_APPLY_EXPLICIT_WAIT = "explicitWait";
	public String ACTION_WAIT_FOR_TITLE = "waitForTitle";
	public String ACTION_APPLY_FLUENT_WAIT = "fluentWait";
	public String ACTION_WAIT_FOR_LOADING_PAGE = "waitForLoadingPage";
	public String ACTION_ANGULAR_WAIT_FOR_LOADING_PAGE = "angularWaitForLoadingPage";
	public String ACTION_WAIT_FOR_ELEMENT_IS_CLICKABLE = "elementIsClickable";
	public String ACTION_WAIT_FOR_ELEMENT_IS_VISIBLE = "elementIsVisible";
	public String ACTION_WAIT_FOR_ELEMENT_IS_NOT_VISIBLE = "elementIsNotVisible";
	public String ACTION_OPEN = "open";
	public String ACTION_GET_WIDTH = "getWidth";
	public String ACTION_DRAG_AND_DROP = "dragAndDrop";
	public String ACTION_RIGHT_CLICK = "rightClick";
	public String ACTION_SCROLL_INTO_VIEW = "scrollIntoView";
	public String ACTION_PRESS_ENTER_KEY = "pressEnterKey";
	public String ACTION_PRESS_TAB_KEY = "pressTabKey";
	public String ACTION_GET_HEIGHT = "getHeight";
	// public void logResultInReport(String testResult, String stepDescription);

	// public String
	// GET_NUMBER_OF_ELEMENT_IN_LIST="getNumberOfElementInList";
	public String ACTION_VERIFY_FONT_STYLE = "verifyStyle";
	public String ACTION_GET_CURRENT_URL = "getCurrentUrl";
	public String ACTION_VERIFY_POPUP_SENDKEYS = "verifypopupsendkeys";
	public String ACTION_CLICK_TERMINATE="clickterminate";

}
