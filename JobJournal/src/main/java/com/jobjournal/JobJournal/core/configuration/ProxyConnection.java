package com.jobjournal.JobJournal.core.configuration;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConnection {
    @Bean
    public void connect() {
        try {
            URL proxyUrl = new URL(System.getenv("QUOTAGUARDSTATIC_URL"));
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

            URL url;
            url = new URL("http://ip.quotaguard.com");
            conn = url.openConnection();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
