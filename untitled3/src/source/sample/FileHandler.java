package source.sample;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    //переменные для работы с файлами
    private static String name = "\\src\\source\\sample\\info.txt";
    private static File file;

    //переменные для метода write
    private static String buffer;


    //переменные для метода read
    static BufferedReader br;
    static ArrayList<String> out;

    //переменные для метода appendFileText
    static FileWriter fr = null;

    //запись цен вылют на определённую дату
    public static void write(String date, ArrayList<String> rates) throws IOException {

        catchEX();
        buffer = "";

        //дата
        buffer += date;

        //масив цен валют
        for (String rate:
             rates) {
            buffer += " " + rate;
        }
        buffer += "\n";

        appendFileText(buffer);
    }

    //чтение цен
    public static ArrayList<String> read() throws IOException, NullPointerException{

        //подготовка
        String root = System.getProperty("user.dir");
        file = new File(root + name);
        catchEX();
        br = null;
        String str;
        br = new BufferedReader(new FileReader(root + name));
        out = new ArrayList<String>();

        //чтение и запись
        while ((str = br.readLine()) != null){
            out.add(str);
        }
        return out;
    }

    //проверка на наличие файла
    public static void catchEX() throws IOException {
        if(!file.exists()){
            file.createNewFile();
        }
    }

    private static void appendFileText(String text) throws IOException {
        try {
            fr = new FileWriter(file,true);
            fr.write(text);

        } catch (IOException e) {
            throw new IOException("FileHandler[72]");
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
