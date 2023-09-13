package com.macias34.codemastery.security.constants;

import io.github.cdimascio.dotenv.Dotenv;

public class SecurityConstants {
	public static final long JWT_EXPIRATION = 70000;
	public static final String JWT_SECRET = Dotenv.load().get("JWT_SECRET");

}
