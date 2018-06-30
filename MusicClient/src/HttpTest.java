import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpTest {

    public static void main(String[] args) {

        HttpURLConnection connection = null;

        try{
            URL url = new URL("http://localhost:3000/");
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

            System.out.println(response.toString());


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
    }
}
