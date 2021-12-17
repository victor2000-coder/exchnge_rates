package source.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

class ThreadHandler {
    //определение потока вывода информации проекта 1
    private static Thread resetInfoThread;
    private static Runnable resetInfoRunnable;
    public static void resetInfo() throws IOException {
        resetInfoRunnable = new Runnable() {
            FrameHandler fh = new FrameHandler();
            long temp;
            @Override
            public void run() {
                try{
                    long lastTime;
                    while(true) {
                        lastTime = new Date().getTime();
                        fh.setInfo();
                        temp = new Date().getTime() - lastTime;
                        if(temp > 30){
                            System.err.println(temp + " Thread: resetInf  Date: " + new Date().toString().split(" ")[2] + " Time: " + new Date().toString().split(" ")[3]);
                        }
                        Thread.sleep(1000);
                    }
                }catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        };
        resetInfoThread = new Thread(resetInfoRunnable);
        resetInfoThread.start();
    }

    //определение потока проверки и изменения информации проекта 1
    private static Thread checkInfoThread;
    private static Runnable checkInfoRunnable;
    public static void CACInfo()
    {
        checkInfoRunnable = new Runnable() {
            @Override
            public void run() {
                try{
                    //определяем входные и переменные
                    InfoHandler infoHandler = new InfoHandler();
                    String[] currencies = InfoHandler.currenties;
                    ArrayList<String> rates = new ArrayList<String>();
                    int index;
                    //бул проверяющий на изменения
                    boolean isChangeExist;
                    boolean isBugExist;

                    String[] lastString;
                    ArrayList<String> data;
                    long lastTime;
                    long temp;
                    while(true){
                        lastTime = new Date().getTime();
                        isChangeExist = false;
                        try{
                            //проверка на фантомный баг
                            isBugExist = false;
                            data = FileHandler.read();
                            if (data.size() < infoHandler.contOfStrings){
                                isBugExist = true;
                            }
                            if (!isBugExist){

                                //читаем из файла, и сравниваем с тем, что находится в другом источнике
                                lastString = data.get(data.size() - 1).split(" ");
                                index = 0;
                                rates.clear();
                                for (String current:
                                        currencies) {
                                    index++;
                                    rates.add(infoHandler.getInfo(current));
                                    if(!lastString[index+5].equals(infoHandler.getInfo(current))){
                                        isChangeExist = true;
                                        System.out.println(lastString[index+5] + "!=" + infoHandler.getInfo(current));
                                    }
                                }
                                //если в источнике произошли изменения, записываем в файл
                                if (isChangeExist){
                                    FileHandler.write(new Date().toString(),rates);
                                    infoHandler.contOfStrings++;
                                }
                                temp = new Date().getTime() - lastTime;
                                if(temp > 10){
                                    System.err.println(temp + " Thread: CACInf  Date: " + new Date().toString().split(" ")[2] + " Time: " + new Date().toString().split(" ")[3]);
                                }
                                //спим
                                Thread.sleep(1000);
                            }else{
                                System.out.println("WTF???");
                            }
                            //если файл пуст, выполняем другое условие
                        }catch (IndexOutOfBoundsException ex){
                            index = 0;
                            rates.clear();
                            for (String current:
                                    currencies) {
                                index++;
                                rates.add(infoHandler.getInfo(current));
                                ex.printStackTrace();
                            }
                            FileHandler.write(new Date().toString(),rates);
                            System.err.println("chto-to strannoe proishodit");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        checkInfoThread = new Thread(checkInfoRunnable);
        checkInfoThread.start();
    }
}