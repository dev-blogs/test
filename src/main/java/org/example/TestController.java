package org.example;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/test")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            StringBuilder builder = new StringBuilder();
            while(networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    builder.append(inetAddress.getHostAddress() + "</br>");
                }
            }
            return "Test message. IP addresses:</br>" + builder;
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
    }
}