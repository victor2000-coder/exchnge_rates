package source.sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //поток проекта 1
        try {
            ThreadHandler.resetInfo();
            ThreadHandler.CACInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
