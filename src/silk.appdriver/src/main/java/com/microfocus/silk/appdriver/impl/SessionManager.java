package com.microfocus.silk.appdriver.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.microfocus.silk.appdriver.controller.model.AppDriverOptions;
import com.microfocus.silk.appdriver.controller.model.BaseStateInfo;
import com.microfocus.silk.appdriver.controller.model.BaseStateInfo.BaseStateType;

@Component
public class SessionManager {
	private Map<String, IReplaySession> map = new HashMap<>();

	private ObjectMapper mapper = new ObjectMapper();

	public IReplaySession createSession(Map<String, Object> capabilities) {

		BaseStateInfo baseStateInfo = mapper.convertValue(capabilities.get("appdriver-basestate"), BaseStateInfo.class);

		IReplaySession session = null;

		if (baseStateInfo == null) {
			Object app = capabilities.get("app");
			Preconditions.checkNotNull(app);

			Object locator = capabilities.get("locator");

			baseStateInfo = new BaseStateInfo();
			baseStateInfo.setType(BaseStateType.NATIVE);
			baseStateInfo.setExecutable(app.toString());
			baseStateInfo.setLocator(locator != null ? locator.toString() : null);
		}

		AppDriverOptions options = mapper.convertValue(capabilities.get("appdriver-options"), AppDriverOptions.class);

		session = new JtfReplaySession(baseStateInfo, options);
		
		map.put(session.getId(), session);
		return session;
	}

	public void deleteSession(String sessionId) {
		IReplaySession session = map.remove(sessionId);
		session.delete();
	}

	public IReplaySession getSession(String sessionId) {
		return map.get(sessionId);
	}
}
