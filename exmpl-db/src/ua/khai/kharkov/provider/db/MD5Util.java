package ua.khai.kharkov.provider.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Util {
	public static String hash(String input,String algorithm){
    	MessageDigest digest;
    	byte [] hash=null;
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.update(input.getBytes());
	    	hash = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	String hex;
    	StringBuilder str = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			if (hash[i] < 0) {
				hex = Integer.toHexString(hash[i]);
				hex = hex.substring(hex.length() - 2, hex.length());
				str.append(hex);
				if (hex.length() == 1) {
					str.append('0');
				}
				continue;
			}
			hex= Integer.toHexString(hash[i]);
			if (hex.length() == 1) {
				str.append('0');
			}
			str.append(hex);
		}
    	return str.toString();
    }
}
