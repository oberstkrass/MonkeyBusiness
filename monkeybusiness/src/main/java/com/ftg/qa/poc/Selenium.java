//package com.ftg.qa.poc;
//
//import java.awt.AWTException;
//import java.awt.Robot;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//import javax.swing.*;
//
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.firefox.internal.ProfilesIni;
//
//public class Selenium {
//
//	static Scanner scanner = new Scanner(System.in);
//	static WebDriver driver;
//
//	public static void main(String[] args) {
//		startFire();
//	}
//
//	public static void startFire() {
//		JFrame jf = new JFrame();
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		JPanel contentPane = new JPanel();
//		contentPane.setLayout(null);
//		jf.setBounds(100, 100, 450, 600);
//		jf.setContentPane(contentPane);
//		
//		JButton request = new JButton("Request");
//		request.setBounds(12, 100, 425, 35);
//		
//		JTextField txt_units = new JTextField();
//		txt_units.setColumns(10);
//		txt_units.setBounds(10, 50, 87, 26);
//		JTextField txt_limit = new JTextField();
//		txt_limit.setColumns(10);
//		txt_limit.setBounds(235, 50, 87, 26);
//		JButton close = new JButton("Close");
//		close.setBounds(12, 150, 425, 35);
//
//		contentPane.add(request);
//		contentPane.add(txt_units);
//		contentPane.add(txt_limit);
//		contentPane.add(close);
//
//		
//		
//		
//		
//		request.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				driver = getFirefoxDriver();
//				String url = "https://ilox-uat2.xervices.de/loxwebfelx";
//				driver.get(url);
//				WebElement userName = driver.findElement(By.id("name"));
//				userName.sendKeys("ILOXTEST@ETRADE");
//				WebElement password = driver.findElement(By.id("pass"));
//				password.sendKeys("w%kq4W@s2");
//				WebElement btnLogin = driver.findElement(By.id("loginbutton"));
//				btnLogin.click();
//
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//				WebElement WKN = driver.findElement(By.id("security"));
//				WKN.sendKeys("DE000DB1D1V6" + Keys.RETURN);
//
//				WebElement units = driver.findElement(By.id("units"));
//				units.sendKeys(txt_units.getText());
//				WebElement limit = driver.findElement(By.id("limit"));
//				limit.sendKeys(txt_limit.getText());
//				WebElement requestbutton = driver.findElement(By.id("requestbutton"));
//				requestbutton.click();
//				
//				
//				List<WebElement> row = driver.findElements(By.xpath("//tr[@class='bgdataeven']/td"));
//				JTextField txt_price = new JTextField(/*driver.findElement(By.xpath("//tr[@class='bgdataeven']/td[@class='pr']")).getText()*/row.get(5).getText());
//				txt_price.setColumns(10);
//				txt_price.setBounds(10, 190, 87, 26);
//				contentPane.add(txt_price);
//				JTextField txt_status = new JTextField(/*driver.findElement(By.xpath("//tr[@class='bgdataeven']/td[3]"))*/row.get(2).getText());
//				txt_status.setColumns(10);
//				txt_status.setBounds(200, 190, 87, 26);
//				contentPane.add(txt_status);
//				
//				try {
//					FileWriter writer = new FileWriter("log.txt", true);
//					Scanner reader = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream("log.txt"))));
//					if(!reader.hasNextLine()) writer.write("Date\tUnits\tLimit\tStatus\tPrice");
//					writer.append(new Date() + "\t" + txt_units.getText() + "\t" + txt_limit.getText() + "\t" + txt_status.getText() + "\t" + txt_price.getText()); 
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				JButton change = new JButton("Change Order");
//				change.setBounds(12, 250, 425, 35);
//				
//				change.addActionListener(
//						new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent arg0) {
//						driver.findElement(By.xpath("//td[@class='ac']/div[1]/img[2]")).click();
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						
//						String newline = txt_limit.getText();
//						System.out.println(newline);
//						driver.findElement(By.id("limit")).clear();
//						driver.findElement(By.id("limit")).sendKeys(newline);
//						newline = txt_units.getText();
//						System.out.println(newline);
//						driver.findElement(By.id("units")).clear();
//						driver.findElement(By.id("units")).sendKeys(newline);
//						driver.findElement(By.xpath("//input[@class='stdbutton' and @value='Change']")).click();
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						txt_status.setText(row.get(2).getText());
//						txt_price.setText(row.get(5).getText());
//						try {
//							FileWriter writer = new FileWriter("log.txt", true);
//							Scanner reader = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream("log.txt"))));
//							if(!reader.hasNextLine()) writer.append("Date\tUnits\tLimit\tStatus\tPrice");
//							writer.append(new Date() + "\t" + txt_units.getText() + "\t" + txt_limit.getText() + "\t" + txt_status.getText() + "\t" + txt_price.getText()); 
//							writer.close();
//							reader.close();
//							
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						//jf.pack();
//					}
//								
//				});
//				
//				
//				
//				contentPane.add(change);
//				//jf.pack();
//			}
//		});
//		
//		close.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(driver != null) driver.quit();
//				System.exit(0);
//			}
//		});
//		
//		//jf.pack();
//		jf.setVisible(true);
//
//	}
//
//	private static WebDriver getFirefoxDriver() {
//
//		String currentWebdriver = "webdriver.gecko.driver";
//		System.setProperty(currentWebdriver,
//				"C:\\Users\\Nicolas\\Downloads\\geckodriver-v0.18.0-win64\\geckodriver.exe");
//
//		// profile
//		ProfilesIni profile = new ProfilesIni();
//		FirefoxProfile ffProfile = profile.getProfile("default");
//		ffProfile.setAcceptUntrustedCertificates(true);
//		ffProfile.setAssumeUntrustedCertificateIssuer(false);
//		return new FirefoxDriver(ffProfile);
//	}
//
//}
