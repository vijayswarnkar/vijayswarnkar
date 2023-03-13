package com.example.algos;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class ReadIni {
    public static void main(String[] args) throws IOException {
        String path = "/Users/vsswarnk/java_projects/resources/testIni.ini";
        Wini ini = new Wini(new File(path));
        System.out.println(ini);
        String y = ini.get("ftp", "key");
        System.out.println(y + y.length());
        y = y.replace("\"", "");
        System.out.println(y + y.length());
    }
}