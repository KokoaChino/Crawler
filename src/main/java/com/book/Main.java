package com.book;


import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("Output\\" + 1);
        if (!file.exists()) file.mkdirs();
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getName());
        System.out.println(file.getPath());
        System.out.println(file.getParent());
    }
}