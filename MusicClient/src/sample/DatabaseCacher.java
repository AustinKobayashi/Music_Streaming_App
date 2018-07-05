package sample;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DatabaseCacher {

    private static final String base_url = "http://localhost:3000";



    public static void main(String args[]){

        CacheDatabase();
    }




    private static void CacheDatabase(){

        try {
            CacheResults("artists");
            CacheResults("albums");
            CacheResults("songs");
            CacheResults("genres");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    private static void CacheResults(String url) throws FileNotFoundException {

        String result = Request("/" + url);

        File url_file = new File("cache\\" + url + ".txt");
        url_file.delete();

        PrintWriter pw = new PrintWriter("cache\\" + url + ".txt");
        pw.println(result);
        pw.close();
    }




    private static String Request(String url_string){

        HttpURLConnection connection = null;

        try{
            URL url = new URL(base_url + url_string);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Language", "en-US");


            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            return response.toString();


        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
            e.printStackTrace();

        } catch (ProtocolException e) {
            System.out.println("ProtocolException");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("ProtocolException");
            e.printStackTrace();
        }

        return null;
    }
}
