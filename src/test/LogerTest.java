package test;

import org.apache.log4j.Logger;

public class LogerTest {
	public static void main(String[] args) {
		LogerTest logerTest = new LogerTest();
		logerTest.test1();
	}
	public void test1(){
		Logger logger = Logger.getLogger(this.getClass());
		logger.error("里好123"+System.currentTimeMillis());
	}
}
