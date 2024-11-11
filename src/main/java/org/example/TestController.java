package org.example;

import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {

    @GetMapping("/test")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Enumeration e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        }
        StringBuilder builder = new StringBuilder();
        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                builder.append(i.getHostAddress() + " ");
            }
        }
        return "Test message " + builder;
    }
}