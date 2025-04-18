package org.example;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    private static int counter = 1;

    @GetMapping("/test")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        try {
            logger.info("Call number: {}", incAndGetCounter());
            logger.info("test log message");
            logger.info("Current time is: {}", new Date());
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            StringBuilder builder = new StringBuilder();
            while(networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    logger.info("IP address: {}", inetAddress.getHostAddress());
                    builder.append(inetAddress.getHostAddress() + "</br>");
                }
            }
            return "Test message. IP addresses:</br>" + builder + "</br>This text was added after configure oc. Version 5";
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
    }

    private synchronized int incAndGetCounter() {
        return counter++;
    }
}