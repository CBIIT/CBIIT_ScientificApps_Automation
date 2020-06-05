package com.nci.automation.dao;

import com.nci.automation.utils.EncryptionUtils;

public class PasswordEncrypter {

	public static void main(String[] args) {
		String pwd = "";
		System.out.println(EncryptionUtils.encrypt(pwd));

	}

}
