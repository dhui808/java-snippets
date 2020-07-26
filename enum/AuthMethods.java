package javasnippets.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AuthMethods {

	PASSWORD(0),
	PVQ(1),
	THUMBPRINT(2),
	BIOMETRICS(3),
	OTP(4),
	OTHER(5);

	private int code;
	private static Map<Integer, AuthMethods> map = new HashMap<Integer, AuthMethods>(7);
	
	static {
		Arrays.stream(AuthMethods.values()).forEach(item -> {map.put(item.code, item);});
	}
	
	AuthMethods(int code) {
        	this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static AuthMethods getEnum(int code) {
		
		AuthMethods item = map.get(code);
		
		if (null != item) {
      			return item;
		}
		
		throw new IllegalArgumentException("Illegal Auth Method code=" + code);
	}
}
