package org.vadim.server;

import java.io.*;

public class DataBase implements Repository {

    private final String FILE_NAME = "chat.log";

    @Override
    public void saveTextToFile(String txt) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
            bw.write(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String readTextFromFile()  {
        StringBuilder str = new StringBuilder();
//        System.out.println(System.getProperty("user.dir"));
        File f = new File("chat.log");
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                while (br.ready()) {
                    str.append(br.readLine());
                    str.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }

}
