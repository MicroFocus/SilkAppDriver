package com.microfocus.silk.appdriver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.borland.silktest.jtf.common.InvalidObjectHandleException;
import com.borland.silktest.jtf.common.types.Rect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.microfocus.silk.appdriver.controller.model.ElementGetRectResponse;
import com.microfocus.silk.appdriver.controller.model.ElementSendKeysRequest;
import com.microfocus.silk.appdriver.controller.model.ExecuteScriptRequest;
import com.microfocus.silk.appdriver.controller.model.FindElementRequest;
import com.microfocus.silk.appdriver.controller.model.NewSessionRequest;
import com.microfocus.silk.appdriver.controller.model.NewSessionResponse;
import com.microfocus.silk.appdriver.controller.model.NewSessionResult;
import com.microfocus.silk.appdriver.controller.model.PerformActionsRequest;
import com.microfocus.silk.appdriver.controller.model.ProtocolError;
import com.microfocus.silk.appdriver.controller.model.Response;
import com.microfocus.silk.appdriver.controller.model.SetWindowRectRequest;
import com.microfocus.silk.appdriver.controller.model.SwitchToWindowRequest;
import com.microfocus.silk.appdriver.impl.IReplaySession;
import com.microfocus.silk.appdriver.impl.SessionManager;

@Controller
public class W3CProtocolController {
	private final static Logger LOGGER = Logger.getLogger(W3CProtocolController.class.getName());

	@Autowired
	private SessionManager sessionManager;

	private ResponseEntityFactory responseFactory = new ResponseEntityFactory();

	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Implements the "New Session" command See
	 * https://www.w3.org/TR/webdriver/#dfn-new-session
	 */
	@RequestMapping(value = "/session", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> newSession(@RequestBody(required = false) NewSessionRequest body)
			throws Throwable {

		LOGGER.info("newSession -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(body));

		IReplaySession session = sessionManager.createSession(body.getDesiredCapabilities());

		NewSessionResult result = new NewSessionResult();
		result.setSessionId(session.getId());

		LOGGER.info("  <- " + mapper.writeValueAsString(result));
		LOGGER.info("newSession <--");

		return ResponseEntity.<Response>ok(new NewSessionResponse(result));
	}

	/**
	 * Implements the "Delete Session" command See
	 * https://www.w3.org/TR/webdriver/#dfn-delete-session
	 */
	@RequestMapping(value = "/session/{sessionId}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> deleteSession(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("deleteSession -->");
		LOGGER.info("  -> " + sessionId);

		sessionManager.deleteSession(sessionId);

		LOGGER.info("deleteSession <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Status" command See
	 * https://www.w3.org/TR/webdriver/#dfn-status
	 */
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> status(@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("status -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ready");
		LOGGER.info("status <--");

		return responseFactory.success(null, "ready");
	}

	/**
	 * Implements the "Get Timeouts" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-timeouts
	 */
	@RequestMapping(value = "/session/{sessionId}/timeouts", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getTimeouts(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getTimeouts -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("getTimeouts <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Set Timeouts" command See
	 * https://www.w3.org/TR/webdriver/#dfn-set-timeouts
	 */
	@RequestMapping(value = "/session/{sessionId}/timeouts", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> setTimeouts(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("setTimeouts -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("setTimeouts <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Go" command See https://www.w3.org/TR/webdriver/#dfn-go
	 */
	@RequestMapping(value = "/session/{sessionId}/url", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> go(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("go -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("go <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Get Current URL" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-current-url
	 */
	@RequestMapping(value = "/session/{sessionId}/url", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getCurrentURL(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getCurrentURL -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("getCurrentURL <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Back" command See
	 * https://www.w3.org/TR/webdriver/#dfn-back
	 */
	@RequestMapping(value = "/session/{sessionId}/back", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> back(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("back -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("back <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Forward" command See
	 * https://www.w3.org/TR/webdriver/#dfn-forward
	 */
	@RequestMapping(value = "/session/{sessionId}/forward", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> forward(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("forward -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("forward <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Refresh" command See
	 * https://www.w3.org/TR/webdriver/#dfn-refresh
	 */
	@RequestMapping(value = "/session/{sessionId}/refresh", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> refresh(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("refresh -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("refresh <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Get Title" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-title
	 */
	@RequestMapping(value = "/session/{sessionId}/title", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getTitle(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getTitle -->");
		String title = sessionManager.getSession(sessionId).getCurrentWindowTitle();
		LOGGER.info("  <- " + title);
		LOGGER.info("getTitle <--");

		return responseFactory.success(sessionId, title);
	}

	/**
	 * Implements the "Get Window Handle" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-window-handle
	 */
	@RequestMapping(value = "/session/{sessionId}/window", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getWindowHandle(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getWindowHandle -->");

		int mainWindowHandle = sessionManager.getSession(sessionId).getCurrentWindowHandle();

		LOGGER.info("  <- " + mainWindowHandle);
		LOGGER.info("getWindowHandle <--");

		return responseFactory.success(sessionId, mainWindowHandle);
	}

	/**
	 * Implements the "Close Window" command See
	 * https://www.w3.org/TR/webdriver/#dfn-close-window
	 */
	@RequestMapping(value = "/session/{sessionId}/window", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> closeWindow(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("closeWindow -->");

		sessionManager.getSession(sessionId).closeCurrentWindow(); // TODO:
																	// Close
																	// current
																	// window?

		LOGGER.info("closeWindow <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Switch To Window" command See
	 * https://www.w3.org/TR/webdriver/#dfn-switch-to-window
	 */
	@RequestMapping(value = "/session/{sessionId}/window", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> switchToWindow(@PathVariable String sessionId,
			@RequestBody(required = false) SwitchToWindowRequest request) throws Throwable {
		LOGGER.info("switchToWindow -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(request));
		
		sessionManager.getSession(sessionId).switchToWindow(request.getHandle());

		LOGGER.info("switchToWindow <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Get Window Handles" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-window-handles
	 */
	@RequestMapping(value = "/session/{sessionId}/window/handles", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getWindowHandles(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getWindowHandles -->");
		LOGGER.info("  -> " + body);

		List<Integer> handles = sessionManager.getSession(sessionId).getWindowHandles();
		ResponseEntity<Response> result = responseFactory.success(sessionId, handles);

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("getWindowHandles <--");

		return result;
	}

	/**
	 * Implements the "Switch To Frame" command See
	 * https://www.w3.org/TR/webdriver/#dfn-switch-to-frame
	 */
	@RequestMapping(value = "/session/{sessionId}/frame", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> switchToFrame(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("switchToFrame -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("switchToFrame <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Switch To Parent Frame" command See
	 * https://www.w3.org/TR/webdriver/#dfn-switch-to-parent-frame
	 */
	@RequestMapping(value = "/session/{sessionId}/frame/parent", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> switchToParentFrame(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("switchToParentFrame -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("switchToParentFrame <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Get Window Rect" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-window-rect
	 */
	@RequestMapping(value = "/session/{sessionId}/window/rect", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getWindowRect(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getWindowRect -->");

		Rect result = sessionManager.getSession(sessionId).getCurrentWindowRect();

		LOGGER.info("  <- " + result);
		LOGGER.info("getWindowRect <--");

		return responseFactory.success(sessionId,
				new ElementGetRectResponse(result.getX(), result.getY(), result.getWidth(), result.getHeight()));
	}

	/**
	 * Implements the "Set Window Rect" command See
	 * https://www.w3.org/TR/webdriver/#dfn-set-window-rect
	 */
	@RequestMapping(value = "/session/{sessionId}/window/rect", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> setWindowRect(@PathVariable String sessionId,
			@RequestBody(required = false) SetWindowRectRequest request) throws Throwable {
		LOGGER.info("setWindowRect -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		sessionManager.getSession(sessionId).setCurrentWindowRect(request.getX(), request.getY(), request.getWidth(),
				request.getHeight());

		LOGGER.info("setWindowRect <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Maximize Window" command See
	 * https://www.w3.org/TR/webdriver/#dfn-maximize-window
	 */
	@RequestMapping(value = "/session/{sessionId}/window/maximize", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> maximizeWindow(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("maximizeWindow -->");

		sessionManager.getSession(sessionId).maximizeCurrentWindow();

		LOGGER.info("maximizeWindow <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Minimize Window" command See
	 * https://www.w3.org/TR/webdriver/#dfn-minimize-window
	 */
	@RequestMapping(value = "/session/{sessionId}/window/minimize", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> minimizeWindow(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("minimizeWindow -->");

		sessionManager.getSession(sessionId).minimizeCurrentWindow();

		LOGGER.info("minimizeWindow <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Fullscreen Window" command See
	 * https://www.w3.org/TR/webdriver/#dfn-fullscreen-window
	 */
	@RequestMapping(value = "/session/{sessionId}/window/fullscreen", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> fullscreenWindow(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("fullscreenWindow -->");

		sessionManager.getSession(sessionId).maximizeCurrentWindow();

		LOGGER.info("fullscreenWindow <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Get Active Element" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-active-element
	 */
	@RequestMapping(value = "/session/{sessionId}/element/active", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getActiveElement(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getActiveElement -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("getActiveElement <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Find Element" command See
	 * https://www.w3.org/TR/webdriver/#dfn-find-element
	 */
	@RequestMapping(value = "/session/{sessionId}/element", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> findElement(@PathVariable String sessionId,
			@RequestBody(required = false) FindElementRequest request) throws Throwable {
		LOGGER.info("findElement -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		ResponseEntity<Response> result = findElement(sessionId, request.getUsing(), request.getValue(), null);

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("findElement <--");

		return result;
	}

	/**
	 * Implements the "Find Elements" command See
	 * https://www.w3.org/TR/webdriver/#dfn-find-elements
	 */
	@RequestMapping(value = "/session/{sessionId}/elements", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> findElements(@PathVariable String sessionId,
			@RequestBody(required = false) FindElementRequest request) throws Throwable {
		LOGGER.info("findElements -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		ResponseEntity<Response> result = findElements(sessionId, request.getUsing(), request.getValue(), null);

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("findElements <--");

		return result;
	}

	/**
	 * Implements the "Find Element From Element" command See
	 * https://www.w3.org/TR/webdriver/#dfn-find-element-from-element
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/element", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> findElementFromElement(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) FindElementRequest request)
			throws Throwable {
		LOGGER.info("findElementFromElement -->");
		LOGGER.info("  -> element: " + elementId);
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		ResponseEntity<Response> result = findElement(sessionId, request.getUsing(), request.getValue(), elementId);

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("findElementFromElement <--");

		return result;
	}

	/**
	 * Implements the "Find Elements From Element" command See
	 * https://www.w3.org/TR/webdriver/#dfn-find-elements-from-element
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/elements", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> findElementsFromElement(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) FindElementRequest request)
			throws Throwable {
		LOGGER.info("findElementsFromElement -->");
		LOGGER.info("  -> element: " + elementId);
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		ResponseEntity<Response> result = findElements(sessionId, request.getUsing(), request.getValue(), elementId);

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("findElementsFromElement <--");

		return result;
	}

	/**
	 * Implements the "Is Element Selected" command See
	 * https://www.w3.org/TR/webdriver/#dfn-is-element-selected
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/selected", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> isElementSelected(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("isElementSelected -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("isElementSelected <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Get Element Attribute" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-element-attribute
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/attribute/{name}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getElementAttribute(@PathVariable String sessionId,
			@PathVariable String elementId, @PathVariable String name, @RequestBody(required = false) String body)
			throws Throwable {
		LOGGER.info("getElementAttribute -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("getElementAttribute <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Get Element Property" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-element-property
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/property/{name}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getElementProperty(@PathVariable String sessionId,
			@PathVariable String elementId, @PathVariable String name, @RequestBody(required = false) String body)
			throws Throwable {
		LOGGER.info("getElementProperty -->");
		LOGGER.info("  -> " + elementId);
		LOGGER.info("  -> " + name);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			Object value = sessionManager.getSession(sessionId).getElementProperty(elementId, name);
			result = responseFactory.success(sessionId, value);
		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("getElementProperty <--");

		return result;
	}

	/**
	 * Implements the "Get Element CSS Value" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-element-css-value
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/css/{propertyname}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getElementCSSValue(@PathVariable String sessionId,
			@PathVariable String elementId, @PathVariable String propertyname,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getElementCSSValue -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("getElementCSSValue <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Get Element Text" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-element-text
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/text", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getElementText(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getElementText -->");
		LOGGER.info("  -> elementId: " + elementId);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			String text = sessionManager.getSession(sessionId).getElementText(elementId);
			result = responseFactory.success(sessionId, text);

		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("getElementText <--");

		return result;
	}

	/**
	 * Implements the "Get Element Tag Name" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-element-tag-name
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/name", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getElementTagName(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getElementTagName -->");
		LOGGER.info("  -> " + elementId);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			String className = sessionManager.getSession(sessionId).getElementClassName(elementId);
			result = responseFactory.success(sessionId, className);
		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("getElementTagName <--");

		return result;
	}

	/**
	 * Implements the "Get Element Rect" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-element-rect
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/rect", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getElementRect(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getElementRect -->");
		LOGGER.info("  -> " + elementId);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			Rect rect = sessionManager.getSession(sessionId).getElementRect(elementId);
			ElementGetRectResponse response = new ElementGetRectResponse(rect.getX(), rect.getY(), rect.getWidth(),
					rect.getHeight());
			result = responseFactory.success(sessionId, response);
		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("getElementRect <--");

		return result;
	}

	/**
	 * Implements the "Is Element Enabled" command See
	 * https://www.w3.org/TR/webdriver/#dfn-is-element-enabled
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/enabled", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> isElementEnabled(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("isElementEnabled -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("isElementEnabled <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Element Click" command See
	 * https://www.w3.org/TR/webdriver/#dfn-element-click
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/click", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> elementClick(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("elementClick -->");
		LOGGER.info("  -> elementid: " + elementId);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			sessionManager.getSession(sessionId).clickElement(elementId);
		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("elementClick <--");

		return result;
	}

	/**
	 * Implements the "Element Clear" command See
	 * https://www.w3.org/TR/webdriver/#dfn-element-clear
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/clear", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> elementClear(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("elementClear -->");
		LOGGER.info("  -> elementid: " + elementId);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			sessionManager.getSession(sessionId).clearElement(elementId);
		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("elementClear <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Element Send Keys" command See
	 * https://www.w3.org/TR/webdriver/#dfn-element-send-keys
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/value", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> elementSendKeys(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) ElementSendKeysRequest request)
			throws Throwable {
		LOGGER.info("elementSendKeys -->");
		LOGGER.info("  -> elementId: " + elementId);

		StringBuilder keysToSend = new StringBuilder();

		for (String str : request.getValue()) {
			keysToSend.append(str); // TOOD: This should be the place to handle
									// special keys
		}

		String keys = keysToSend.toString();

		LOGGER.info("  -> keys: " + keys);

		ResponseEntity<Response> result = responseFactory.success(sessionId);

		try {
			sessionManager.getSession(sessionId).sendKeysToElement(elementId, keys);
		} catch (InvalidObjectHandleException ex) {
			result = responseFactory.error(sessionId, ProtocolError.STALE_ELEMENT_REFERENCE);
		}

		LOGGER.info("  <- " + mapper.writeValueAsString(result.getBody()));
		LOGGER.info("elementSendKeys <--");

		return result;
	}

	/**
	 * Implements the "Get Page Source" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-page-source
	 */
	@RequestMapping(value = "/session/{sessionId}/source", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getPageSource(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getPageSource -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("getPageSource <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Execute Script" command See
	 * https://www.w3.org/TR/webdriver/#dfn-execute-script
	 */
	@RequestMapping(value = "/session/{sessionId}/execute/sync", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> executeScript(@PathVariable String sessionId,
			@RequestBody(required = false) ExecuteScriptRequest request) throws Throwable {

		LOGGER.info("executeScript -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		Object result = sessionManager.getSession(sessionId).executeScript(request.getScript(), request.getArgs());

		ResponseEntity<Response> response = responseFactory.success(sessionId, result);

		LOGGER.info("  <- " + mapper.writeValueAsString(result));

		LOGGER.info("executeScript <--");

		return response;
	}

	/**
	 * Implements the "Execute Async Script" command See
	 * https://www.w3.org/TR/webdriver/#dfn-execute-async-script
	 */
	@RequestMapping(value = "/session/{sessionId}/execute/async", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> executeAsyncScript(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("executeAsyncScript -->");
		LOGGER.info("  -> " + body);
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("executeAsyncScript <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Get All Cookies" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-all-cookies
	 */
	@RequestMapping(value = "/session/{sessionId}/cookie", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getAllCookies(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getAllCookies -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("getAllCookies <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Get Named Cookie" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-named-cookie
	 */
	@RequestMapping(value = "/session/{sessionId}/cookie/{name}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getNamedCookie(@PathVariable String sessionId,
			@PathVariable String name, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getNamedCookie -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("getNamedCookie <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Add Cookie" command See
	 * https://www.w3.org/TR/webdriver/#dfn-add-cookie
	 */
	@RequestMapping(value = "/session/{sessionId}/cookie", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> addCookie(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("addCookie -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("addCookie <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Delete Cookie" command See
	 * https://www.w3.org/TR/webdriver/#dfn-delete-cookie
	 */
	@RequestMapping(value = "/session/{sessionId}/cookie/{name}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> deleteCookie(@PathVariable String sessionId,
			@PathVariable String name, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("deleteCookie -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("deleteCookie <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Delete All Cookies" command See
	 * https://www.w3.org/TR/webdriver/#dfn-delete-all-cookies
	 */
	@RequestMapping(value = "/session/{sessionId}/cookie", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> deleteAllCookies(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("deleteAllCookies -->");
		LOGGER.info("  <- ERROR: unknown command");
		LOGGER.info("deleteAllCookies <--");

		return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
	}

	/**
	 * Implements the "Perform Actions" command See
	 * https://www.w3.org/TR/webdriver/#dfn-perform-implementation-specific-action-dispatch-steps
	 */
	@RequestMapping(value = "/session/{sessionId}/actions", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> performActions(@PathVariable String sessionId,
			@RequestBody(required = false) PerformActionsRequest request) throws Throwable {
		LOGGER.info("performActions -->");
		LOGGER.info("  -> " + mapper.writeValueAsString(request));

		// TODO: Implement me!

		LOGGER.info("performActions <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Release Actions" command See
	 * https://www.w3.org/TR/webdriver/#dfn-release-actions
	 */
	@RequestMapping(value = "/session/{sessionId}/actions", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> releaseActions(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("releaseActions -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("releaseActions <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Dismiss Alert" command See
	 * https://www.w3.org/TR/webdriver/#dfn-dismiss-alert
	 */
	@RequestMapping(value = "/session/{sessionId}/alert/dismiss", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> dismissAlert(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("dismissAlert -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("dismissAlert <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Accept Alert" command See
	 * https://www.w3.org/TR/webdriver/#dfn-accept-alert
	 */
	@RequestMapping(value = "/session/{sessionId}/alert/accept", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> acceptAlert(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("acceptAlert -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("acceptAlert <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Get Alert Text" command See
	 * https://www.w3.org/TR/webdriver/#dfn-get-alert-text
	 */
	@RequestMapping(value = "/session/{sessionId}/alert/text", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> getAlertText(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("getAlertText -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("getAlertText <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Send Alert Text" command See
	 * https://www.w3.org/TR/webdriver/#dfn-send-alert-text
	 */
	@RequestMapping(value = "/session/{sessionId}/alert/text", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> sendAlertText(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("sendAlertText -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("sendAlertText <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Take Screenshot" command See
	 * https://www.w3.org/TR/webdriver/#dfn-take-screenshot
	 */
	@RequestMapping(value = "/session/{sessionId}/screenshot", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> takeScreenshot(@PathVariable String sessionId,
			@RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("takeScreenshot -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("takeScreenshot <--");

		return responseFactory.success(sessionId);
	}

	/**
	 * Implements the "Take Element Screenshot" command See
	 * https://www.w3.org/TR/webdriver/#dfn-take-element-screenshot
	 */
	@RequestMapping(value = "/session/{sessionId}/element/{elementId}/screenshot", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<Response> takeElementScreenshot(@PathVariable String sessionId,
			@PathVariable String elementId, @RequestBody(required = false) String body) throws Throwable {
		LOGGER.info("takeElementScreenshot -->");
		LOGGER.info("  -> " + body);

		// TODO: Implement me!

		LOGGER.info("takeElementScreenshot <--");

		return responseFactory.success(sessionId);
	}

	private ResponseEntity<Response> findElement(String sessionId, String using, String value, String parentElement) {
		IReplaySession session = sessionManager.getSession(sessionId);

		if ("xpath".equals(using)) {
			String elementId = session.findElementByXpath(value, parentElement);

			if (elementId != null) {
				return responseFactory.success(sessionId, ImmutableMap.of("ELEMENT", elementId));
			} else {
				return responseFactory.error(sessionId, ProtocolError.NO_SUCH_ELEMENT);
			}
		} else {
			// TODO: Is this the right error?
			return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
		}
	}

	private ResponseEntity<Response> findElements(String sessionId, String using, String value, String parentElement) {
		IReplaySession session = sessionManager.getSession(sessionId);

		if ("xpath".equals(using)) {
			List<String> elementIDs = session.findAllElementsByXpath(value, parentElement);

			List<Map<String, String>> result = new ArrayList<>();
			for (String elementId : elementIDs) {
				result.add(ImmutableMap.of("ELEMENT", elementId));
			}

			return responseFactory.success(sessionId, result);
		} else {
			// TODO: Is this the right error?
			return responseFactory.error(sessionId, ProtocolError.UNKNOWN_COMMAND);
		}
	}

}
