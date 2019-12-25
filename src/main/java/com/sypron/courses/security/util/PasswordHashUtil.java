package com.sypron.courses.security.util;

import com.sypron.courses.security.model.HashedPassword;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHashUtil {
	private static int workload = 15;

	public static void main(String[] args) {
		System.out.println(generatePasswordHash("admin").getHash());
	}

	public static HashedPassword generatePasswordHash(String password)
	{
		String salt = BCrypt.gensalt(workload);
		String hashedPassword = BCrypt.hashpw(password, salt);
		return new HashedPassword(hashedPassword, salt);
	}


	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean passwordVerified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new IllegalArgumentException("Invalid hash provided for comparison");

		passwordVerified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(passwordVerified);
	}
}
