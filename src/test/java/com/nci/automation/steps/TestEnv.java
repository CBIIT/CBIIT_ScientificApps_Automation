package com.nci.automation.steps;


import com.nci.automation.web.EnvUtils;
import com.nci.automation.xceptions.TestingException;

public class TestEnv {

	public static void main(String[] args) throws TestingException {

		
		System.out.println(EnvUtils.getApplicationUrl());
		System.out.println(EnvUtils.getUserName("regular"));
		System.out.println(EnvUtils.getPassword("regular"));
		
		
		
	}

}
