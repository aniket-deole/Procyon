package com.paleBlueDot.feedMe.processes;

import java.util.HashMap;

import com.paleBlueDot.feedMe.Helper.GateRequestGenerator2;
import com.paleBlueDot.feedMe.VO.ProfileVO;

public class ProfilerProcess {
	
	private ProfileVO profile;

	public void setProfile(ProfileVO profile) {
		this.profile = profile;
	}

	public ProfilerProcess() {
		// TODO Auto-generated constructor stub
	}

	public String getProfile() {
		String target = "https://sandbox.feedly.com/v3/profile?";
		GateRequestGenerator2 request = new GateRequestGenerator2(target);
		request.openConnection();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Authorization",
				"ArP4ncp7ImEiOiJGZWVkbHkgc2FuZGJveCBjbGllbnQiLCJlIjoxNDI3NDQ0NTA2MTE3LCJpIjoiMWEwMWIwY2QtNWM1OC00MGI3LWEwMzctZDIyNzY5ZDkzMjVlIiwicCI6NiwidCI6MSwidiI6InNhbmRib3giLCJ3IjoiMjAxNS4xMCIsIngiOiJzdGFuZGFyZCJ9:sandbox");
		request.addHeader(hm);
		String is = request.getResponse();
		request.closeConnection();
		return is;
	}
}
