package org.apache.nutch.parse.html;

import java.net.URL;
import java.net.URLClassLoader;
import org.apache.nutch.parse.ParseFilter;

import org.junit.Test;

public class TestClassPath {

    @Test
    public void t() {
        
        System.out.println(ParseFilter.class.getName());
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }
    }
}
