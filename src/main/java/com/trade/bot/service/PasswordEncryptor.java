package com.trade.bot.service;

import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.trade.bot.exception.BotException;
@Service
public class PasswordEncryptor {

	private static final String ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

	private static final String key = "KUy3eM79Z1aY5P+QSimT0A=="; // 128 bit key
	private static final String iv = "udqWfIDD0ek+RZoNiOsrBA==";

	public  String encrypt(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new BotException("Enryption canot be done on null or empty values");
		}

		IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);

		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);

		} catch (GeneralSecurityException e) {
			throw new BotException("Error while encrypting : ", e);
		}

	}

	public String decrypt(String encrypted) {
		if (encrypted == null || encrypted.trim().isEmpty()) {
			throw new BotException("Decryption canot be done on null or empty values");
		}
		IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
		SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), ALGORITHM);
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (GeneralSecurityException e) {
			throw new BotException("Error while decrypting : ", e);
		}
	}

	
	/**
	 * Method is used to generate the key and store it in properties.
	 * 
	 * @throws NoSuchAlgorithmException
	 * 
	 */
	/*
	 * private void generateKey() throws NoSuchAlgorithmException { // Used to
	 * generate key KeyGenerator gen = KeyGenerator.getInstance(ALGORITHM);
	 * gen.init(128); 128-bit AES SecretKey secret = gen.generateKey(); byte[]
	 * binary = secret.getEncoded(); Base64.getEncoder().encodeToString(binary);
	 * 
	 * // Used to genreate iv byte[] iv = new byte[16]; SecureRandom r = new
	 * SecureRandom(); r.nextBytes(iv); Base64.getEncoder().encodeToString(iv);
	 * 
	 * }
	 */

}
