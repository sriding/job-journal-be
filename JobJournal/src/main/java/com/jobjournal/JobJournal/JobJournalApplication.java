package com.jobjournal.JobJournal;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobJournalApplication {
	@Value("${QUOTAGUARDSHIELD_URL}")
	private static String quotaguardUrl;

	public static void main(String[] args) {
		try {
			URL proxyUrl = new URL(quotaguardUrl);
			String userInfo = proxyUrl.getUserInfo();
			String user = userInfo.substring(0, userInfo.indexOf(':'));
			String password = userInfo.substring(userInfo.indexOf(':') + 1);

			URLConnection conn = null;
			System.setProperty("http.proxyHost", proxyUrl.getHost());
			System.setProperty("http.proxyPort", Integer.toString(proxyUrl.getPort()));

			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password.toCharArray());
				}
			});

			URL url = new URL("http://ip.quotaguard.com");
			conn = url.openConnection();

			SpringApplication.run(JobJournalApplication.class, args);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
}
