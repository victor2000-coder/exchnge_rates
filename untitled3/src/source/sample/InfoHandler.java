package source.sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class InfoHandler {

    //для getInfo
    String outInfo;
    int startIndex;

    //счётчики
    int i;
    int contOfStrings = FileHandler.read().size();

    //информация
    private Document doc;
    private String info;

    //список нужных нaм валют(центробанк европы)
    public static final String[] currenties = new String[]{"USD", "JPY","BGN","CZK","DKK","GBP","HUF","PLN","RON","SEK","CHF","ISK","NOK","HRK","RUB","TRY","AUD","BRL","CAD","CNY","HKD","IDR","ILS","INR","KRW","MXN","MYR","NZD","PHP","SGD","THB","ZAR"};

    //всё очевидно
    public InfoHandler() throws IOException {

        //основа
        init();
    }

    //основа
    private void init() throws IOException {
        setWeb();
        setInfo();
    }

    //определяем документ
    private void setWeb() throws IOException {
        doc = (Document) Jsoup.connect("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml").get();
    }

    //определяем информацию
    private void setInfo(){
        info = getDoc().outerHtml();
    }

    //получаем иформацию
    public String getInfo(String infoAbout){
        outInfo = "";
        startIndex = info.indexOf(infoAbout);
        for(i = startIndex + 11; info.charAt(i) != '\"';i++){
            outInfo += info.charAt(i);
        }
        return outInfo;
    }

    //получаем документ
    public Document getDoc(){
        return doc;
    }

}
