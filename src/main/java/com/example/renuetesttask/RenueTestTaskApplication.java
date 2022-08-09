package com.example.renuetesttask;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


@SpringBootApplication
public class RenueTestTaskApplication implements CommandLineRunner {
    public static Properties properties = new Properties();
    @Value("${columnDefault}")
    int columnDefault;
    @Value("${lineSize}")
    int lineSize;
    @Value("${filePath}")
    String filePath;

    public static void main(String[] args) {
        SpringApplication.run(RenueTestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        var column = columnDefault;
        if (args.length > 0) {
            try {
                column = parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Ваш аргумент колонки введен некорректно ");
                return;
            }
            if (column < 0 || column >= lineSize) {
                System.out.println("Введенная Вами номер колонки не существует");
                return;
            }
        }
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Файл не найден.");
            return;
        }
        CSVReader reader = new CSVReader(fileReader, ',', '"', 1);
        Tree tree = new Tree();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            tree.add(nextLine, column);
        }
        reader.close();
        Scanner in = new Scanner(System.in);
        System.out.println("Введите строку: ");
        String inputText = in.nextLine();
        long start = System.currentTimeMillis();
        var resultLines = tree.getAllLeaf(inputText);
        long end = System.currentTimeMillis();
        for (var line : resultLines) {
            System.out.println(Arrays.toString(line));
        }
        if (resultLines.size() != 0) {
            System.out.println("Количество найденных строк:" + resultLines.size());
            System.out.println("Время, затраченное на поиск:" + (end - start) + " мс");
        } else
            System.out.println("По данному запросу в файле ничего не найдено");
    }
}
