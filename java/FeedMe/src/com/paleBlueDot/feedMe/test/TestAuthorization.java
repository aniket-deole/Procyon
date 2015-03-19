package com.paleBlueDot.feedMe.test;

import com.paleBlueDot.feedMe.processes.AuthorizationProcess;

public class TestAuthorization {

	public static void main(String args[]) {
		AuthorizationProcess as = new AuthorizationProcess();
		// String data = as.getCode();
		// System.out.println(data);
		//String code = "AhCe7eF7ImkiOiIxYTAxYjBjZC01YzU4LTQwYjctYTAzNy1kMjI3NjlkOTMyNWUiLCJ1IjoiMTE3NDMxMTE5OTIxNjE0MDI1NTcyIiwiYSI6IkZlZWRseSBzYW5kYm94IGNsaWVudCIsInAiOjYsInQiOjE0MjYzMTY4OTI2NTN9";
		//as.getTokens(code);
		String refToken = "Aswpcl57ImkiOiIxYTAxYjBjZC01YzU4LTQwYjctYTAzNy1kMjI3NjlkOTMyNWUiLCJ1IjoiMTE3NDMxMTE5OTIxNjE0MDI1NTcyIiwiYSI6IkZlZWRseSBzYW5kYm94IGNsaWVudCIsInAiOjYsImMiOjE0MjYzMTY5NTcxNDIsInYiOiJzYW5kYm94IiwibiI6ImVWYjlnOTJmRDVhSEh1cDYifQ:sandbox";
		//as.refreshToken(refToken);
		as.revokeToken(refToken);
	}

}
