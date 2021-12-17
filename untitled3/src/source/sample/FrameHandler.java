package source.sample;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;

class FrameHandler{

    //основные переменные для установки информации
    ArrayList<String> currenciesRatesForAllTime;
    GridBagConstraints constraints;
    String[] currenciesRates;
    String[] currencies;

    //индексаторы
    int indexOfCurrent;
    int indexOfDate;

    //окно
    static JFrame jFrame = getFrame();
    static JPanel jPanel = new JPanel();

    public FrameHandler() throws IOException {
        //определяем компоненты
        jFrame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        //чтоб было быстрее определим один раз
        getReady();
    }

    //выносим в отдельный метод то что можно определить один раз
    private void getReady() throws IOException {
        //определяем входные и переменные для установки информации
        currencies = InfoHandler.currenties;

        //причёска
        constraints = new GridBagConstraints();
    }

    //получаем и выводим информацию
    public void setInfo() throws IOException {

        //подготавливаем JPanel
        jPanel.removeAll();

        //берём информацию из файла
        currenciesRatesForAllTime = FileHandler.read();

        //перечисляем по датам
        for(int i = 0; i < currencies.length; i++){
            //причёсываем
            constraints.weightx = 0;
            constraints.weighty = 0;
            constraints.gridx = 0;
            constraints.gridy = (i + 6) * 4;// нулевая строка не нужна
            constraints.gridheight = 4;
            constraints.gridwidth = 4;
            jPanel.add(new JTextField(currencies[i] + ":",8),constraints);

        }
        constraints.gridy = 0;
        jPanel.add(new JTextField("day of week:",8),constraints);

        constraints.gridy = 4;
        jPanel.add(new JTextField("month:",8),constraints);

        constraints.gridy = 8;
        jPanel.add(new JTextField("date:",8),constraints);

        constraints.gridy = 12;
        jPanel.add(new JTextField("time:",8),constraints);

        constraints.gridy = 16;
        jPanel.add(new JTextField("IDK¯\\_(ツ)_/¯",8),constraints);

        constraints.gridy = 20;
        jPanel.add(new JTextField("year:",8),constraints);

        for (indexOfDate = 0; indexOfDate < currenciesRatesForAllTime.size(); indexOfDate++){//цикл по изменениям валют
            currenciesRates = currenciesRatesForAllTime.get(indexOfDate).split(" ");//находим и подготавливаем необховимое нам изменение
            for (indexOfCurrent = 0; indexOfCurrent < currenciesRates.length; indexOfCurrent++){//цикол повалютам в необходимом нам изменении
                //причёсываем
                constraints.weightx = 0;
                constraints.weighty = 0;
                constraints.gridx = (indexOfDate + 1) * 4;//нулевой столбец выводит наименование валюты
                constraints.gridy = indexOfCurrent * 4;
                constraints.gridheight = 4;
                constraints.gridwidth = 4;
                //добавляем
                jPanel.add(new JTextField(currenciesRates[indexOfCurrent],8),constraints);
            }
        }
        //заканчиваем причёсывать
        jFrame.pack();
    }

    //определяем окно
    static JFrame getFrame(){
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return jFrame;
    }
}
