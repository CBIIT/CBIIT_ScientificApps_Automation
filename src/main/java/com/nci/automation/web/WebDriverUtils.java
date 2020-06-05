package com.nci.automation.web;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.nci.automation.common.Constants;
import com.nci.automation.common.ScenarioContext;
import com.nci.automation.utils.CucumberLogUtils;
import com.nci.automation.utils.LocalConfUtils;

public class WebDriverUtils {

	private final static Logger logger = Logger.getLogger(WebDriverUtils.class);
	public static File ffDownloadsFolder = null;

	public static File getFfDownloadsFolder() {
		return ffDownloadsFolder;
	}

	private static void setFfDownloadsFolder(File ffDownloadsFolder) {
		WebDriverUtils.ffDownloadsFolder = ffDownloadsFolder;
	}

	private static WebDriver webDriver;

	/**
	 * Get a web-driver to interact with the UI
	 */
	@SuppressWarnings("deprecation")
	public static WebDriver getWebDriver() {

		String browser = ConfUtils.getProperty("browser");
		String executionEnvironment = ConfUtils.getProperty("executionEnv");

		if (webDriver == null) {
			setDriverExecutables();

			if (executionEnvironment.equalsIgnoreCase("sauce")) {
				System.err.println("---Sauce Started---" + webDriver);
				if (webDriver == null) {
					DesiredCapabilities capabilities = new DesiredCapabilities();
					String version = ConfUtils.getProperty("version");
					String os = ConfUtils.getProperty("os");
					String username = ConfUtils.getProperty("sauceUsername");
					String accesskey = ConfUtils.getProperty("sauceKey");
					String parentTunnelId = ConfUtils.getProperty("parentSauceTunnel");
					String tunnelId = ConfUtils.getProperty("sauceTunnel");
					String proxyHost = ConfUtils.getProperty("proxyHost");
					String proxyPort = ConfUtils.getProperty("proxyPort");
					String buildId = ConfUtils.getProperty("buildId");

					if (browser != null)
						capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
					if (version != null)
						capabilities.setCapability(CapabilityType.VERSION, version);
					if (os != null)
						capabilities.setCapability(CapabilityType.PLATFORM, os);

					String scenarioName = ScenarioContext.scenario.get().getName();

					capabilities.setCapability("name", scenarioName);
					capabilities.setCapability("username", username);
					capabilities.setCapability("access-key", accesskey);
					capabilities.setCapability("tunnel-identifier", tunnelId);
					capabilities.setCapability("parent-tunnel", parentTunnelId);
					capabilities.setCapability("build", buildId);
					capabilities.setCapability("max-duration", 3600);
					capabilities.setCapability("idle-timeout", "360");
					System.setProperty("http.proxyHost", proxyHost);
					System.setProperty("http.proxyPort", proxyPort);

					webDriver = WebDriverUtils.getNewSauceDriver(capabilities);
					((RemoteWebDriver) webDriver).setFileDetector(new LocalFileDetector());
					ScenarioContext.webDriver.set(webDriver);
					long implicitWaitInSeconds = Long.valueOf(LocalConfUtils.getProperty("implicitWaitInSeconds"));
					webDriver.manage().timeouts().implicitlyWait(implicitWaitInSeconds, TimeUnit.SECONDS);
				}
				return webDriver;

			} else if (executionEnvironment.equalsIgnoreCase("local")) {

				if (Constants.BROWSER_CHROME.equals(browser)) {

//							DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//							HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					//
//							new File(CHROME_DOWNLOADS_FOLDER_PATH).mkdirs();
//							chromePrefs.put("download.default_directory", CHROME_DOWNLOADS_FOLDER_PATH);
					//
//							ChromeOptions options = new ChromeOptions();
//							options.addArguments("test-type");
//							options.addArguments("--disable-extensions");
//							options.addArguments("--start-maximized");
//							options.setExperimentalOption("prefs", chromePrefs);
					//
//							capabilities.setCapability(ChromeOptions.CAPABILITY, options);

					webDriver = new ChromeDriver();
					return webDriver;

				} else if (browser.equalsIgnoreCase(Constants.BROWSER_IE)) {
					DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
					desiredCapabilities.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, Boolean.TRUE);
					desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, Boolean.TRUE);
					desiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
					webDriver = new InternetExplorerDriver(desiredCapabilities);
					webDriver.manage().window().maximize();
					return webDriver;

				} else if (browser.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
					// FirefoxProfile ffProfile = new FirefoxProfile();
					FirefoxProfile ffProfile = new ProfilesIni().getProfile("default");
					DesiredCapabilities dc = DesiredCapabilities.firefox();

					// This is to handle the case where Firefox
					// installation doesn't have a default profile
					if (ffProfile == null)
						ffProfile = new FirefoxProfile();

					ffProfile.setPreference("signon.autologin.proxy", true);

					String ffDownloadsFolderPath = LocalConfUtils.getRootDir() + File.separator + "target"
							+ File.separator + "firefox_downloads";

					new File(ffDownloadsFolderPath).mkdirs();
					setFfDownloadsFolder(new File(ffDownloadsFolderPath));

					ffProfile.setPreference("browser.download.folderList", 2);
					ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
					ffProfile.setPreference("browser.download.manager.showAlertInterval", 1);
					ffProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
					ffProfile.setPreference("browser.download.manager.closeWhenDone", true);
					ffProfile.setPreference("browser.download.dir", ffDownloadsFolderPath);
					ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
					ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
							"application/xml,application/octet-stream,text/xml");
					// driver = new FirefoxDriver(ffProfile);
					dc.setCapability(FirefoxDriver.PROFILE, ffProfile);
					dc.setCapability("marionette", false);

					webDriver = new FirefoxDriver();
					return webDriver;

				} else if (browser.equalsIgnoreCase(Constants.BROWSER_PHANTOM)) {
					DesiredCapabilities capabilities = new DesiredCapabilities();
					capabilities.setJavascriptEnabled(true);
					capabilities.setCapability("takesScreenshot", true);
					capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
							new String[] { "--web-security=no", "--ignore-ssl-errors=yes" });

					webDriver = new PhantomJSDriver(capabilities);
					return webDriver;

				} else {
					CucumberLogUtils.logFail("Unsupported browser in localConf.properties file! "
							+ "Browser has to be 'ie' or 'firefox' or 'phantomjs'", false);
					return null;
				}
			}
		}

		String osName = Constants.GET_OS_NAME;

		if (!browser.equals("phantomjs")) {
			if (osName.contains("Mac")) {
				webDriver.manage().window().fullscreen();
			} else if (osName.contains("Windows")) {
				webDriver.manage().window().maximize();
			}
		}
		
		return webDriver;
	}

	
	/**
	 * This method sets the path to executable drivers based on the operating system.
	 * No setting needs to be changed if switching to another operating system.
	 */
	private static void setDriverExecutables() {

		System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "true");
		String browser = ConfUtils.getProperty("browser");

		String osName = Constants.GET_OS_NAME;

		if (browser.equalsIgnoreCase(Constants.BROWSER_CHROME)) {

			if (osName.contains("Mac")) {
				System.setProperty(Constants.CHROME_KEY, Constants.CHROME_PATH);
			} else if (osName.contains("Window")) {
				System.setProperty(Constants.CHROME_KEY, Constants.CHROME_PATH+".exe");
			}

		} else if (browser.equalsIgnoreCase(Constants.BROWSER_IE)) {

			if (osName.contains("Mac")) {
				System.setProperty(Constants.IE_KEY, Constants.IE_PATH);
			} else if (osName.contains("Windows")) {
				System.setProperty(Constants.IE_KEY, Constants.IE_PATH + ".exe");
			}

		} else if (browser.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {

			if (osName.contains("Mac")) {
				System.setProperty(Constants.FIREFOX_KEY, Constants.FIREFOX_PATH);
			} else if (osName.contains("Windows")) {
				System.setProperty(Constants.FIREFOX_KEY, Constants.FIREFOX_PATH + ".exe");
			}

		} else if (browser.equalsIgnoreCase(Constants.BROWSER_PHANTOM)) {

			if (osName.contains("Mac")) {
				System.setProperty(Constants.PHANTOM_KEY, Constants.PHANTOM_PATH);
			} else if (osName.contains("Windows")) {
				System.setProperty(Constants.PHANTOM_KEY, Constants.PHANTOM_PATH + ".exe");
			}

		}
	}

	
	/**
	 * This method will close the current web-driver
	 */
	public static void closeWebDriver() {
		if (webDriver != null) {
			webDriver.quit();
			ScenarioContext.sauceSessionId.set(null);
			ScenarioContext.webDriver.set(null);
			webDriver = null;
		}
	}

	
	/**
	 * The method will provide a new driver (complete new browser).
	 * @return
	 */
	public static WebDriver getNewDriver() {

		String browser = ScenarioContext.getBrowserID();
		WebDriver driver;

		if (Constants.BROWSER_IE.equals(browser)) {

			DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();

			desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					Boolean.TRUE);
			desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, Boolean.TRUE);

			driver = new InternetExplorerDriver(desiredCapabilities);
			driver.manage().window().maximize();
			return driver;

		} else if (Constants.BROWSER_FIREFOX.contentEquals(browser)) {

			// FirefoxProfile ffProfile = new FirefoxProfile();
			FirefoxProfile ffProfile = new ProfilesIni().getProfile("default");
			DesiredCapabilities dc = DesiredCapabilities.firefox();

			// This is to handle the case where Firefox
			// installation doesn't have a default profile
			if (ffProfile == null)
				ffProfile = new FirefoxProfile();

			ffProfile.setPreference("signon.autologin.proxy", true);

			String ffDownloadsFolderPath = LocalConfUtils.getRootDir() + File.separator + "target" + File.separator
					+ "firefox_downloads";

			new File(ffDownloadsFolderPath).mkdirs();
			setFfDownloadsFolder(new File(ffDownloadsFolderPath));

			ffProfile.setPreference("browser.download.folderList", 2);
			ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
			ffProfile.setPreference("browser.download.manager.showAlertInterval", 1);
			ffProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
			ffProfile.setPreference("browser.download.manager.closeWhenDone", true);
			ffProfile.setPreference("browser.download.dir", ffDownloadsFolderPath);
			ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
			ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/xml,application/octet-stream,text/xml,text/csv,application/vnd.ms-excel,application/csv,text/plain,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// driver = new FirefoxDriver(ffProfile);
			dc.setCapability(FirefoxDriver.PROFILE, ffProfile);
			dc.setCapability("marionette", false);

			driver = new FirefoxDriver(dc);
			driver.manage().window().maximize();

			return driver;

		} else if (Constants.BROWSER_CHROME.equals(browser)) {

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("--disable-extensions");
			options.addArguments("--start-maximized");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			driver = new ChromeDriver(capabilities);
			driver.manage().window().maximize();
			return driver;

		} else if (Constants.BROWSER_PHANTOM.equals(browser)) {

			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability("takesScreenshot", true);
			capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
					new String[] { "--web-security=no", "--ignore-ssl-errors=yes" });

			driver = new PhantomJSDriver(capabilities);
			return driver;

		} else {
			CucumberLogUtils.logFail("Unsupported browser in localConf.properties file! "
					+ "Browser has to be 'ie' or 'firefox' or 'phantomjs'", false);
			return null;
		}
	}


	/**
	 * Use this method to get new sauce driver
	 * @param capabilities
	 * @return
	 */
	public static WebDriver getNewSauceDriver(DesiredCapabilities capabilities) {

		WebDriver driver = null;
		String url = "http://ondemand.saucelabs.com:80/wd/hub";

		// set time zone to ET if no time zone is set
		// to show right test execution time on sauce dashboard
		if (capabilities.getCapability("time-zone") == null)
			capabilities.setCapability("time-zone", "New York");

		try {
			driver = new RemoteWebDriver(new URL(url), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ScenarioContext.sauceSessionId.set((((RemoteWebDriver) driver).getSessionId()).toString());

		return driver;

	}


	/**
	 * Use this method in need of taking screenshot
	 * @return image in byte codes
	 */
	public static byte[] getScreenShot() {

		byte[] screenshot = null;
		WebDriver driver = ScenarioContext.webDriver.get();
		try {
			screenshot = ((TakesScreenshot) WebDriverUtils.getWebDriver()).getScreenshotAs(OutputType.BYTES);
		} catch (Exception e) {
			CucumberLogUtils.logError("Couldn't take screenshot");
		}
		return screenshot;
	}

	

	/**
	 * Use this method to navigate to an external url
	 * @param url
	 */
	public static void navToExternalPage(String url) {

		WebDriver driver = ScenarioContext.webDriver.get();
		driver.get(url);

		try {
			Thread.sleep(1000);
			suppressAlert();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error(String.format("Navigation to external url %s failed", url), e);
		}
	}

	
	public static void suppressAlert() {

		Robot robot = null;
		String browser = ScenarioContext.getBrowserID();
		if (Constants.BROWSER_IE.equals(browser)) {
			try {
				robot = new Robot();
			} catch (AWTException e) {
				logger.error(String.format("Error occured while supressing alert"));
			}
			// press enter to save the file
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
	}
	

	/**
	 * Fetches current URL from browser window
	 * @param driver
	 * @return
	 */
	public static String getCurrentURL(WebDriver driver) {
		String url = "";

		if (driver != null) {
			url = driver.getCurrentUrl();
		}
		return url;
	}

	
	public static void navigateForward(WebDriver driver) {
		driver.navigate().forward();
	}

	public static void navigateBack(WebDriver driver) {
		driver.navigate().back();
	}

	public static void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

}
