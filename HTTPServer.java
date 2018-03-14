import java.io.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.*;

import java.nio.file.Files;
import java.util.*;
import com.sun.net.httpserver.Headers;

public class HTTPServer {

	public static void main(String[] args) throws Exception {
		InetAddress localHost = InetAddress.getByName("localhost");
		HttpServer server = HttpServer.create(new InetSocketAddress(localHost, 8000), 0);
		server.createContext("/MusicServer", new ParseHttpRequest());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	static class ParseHttpRequest implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("==========================");
			
			System.out.println(t.getRequestMethod());
			//System.out.println(t.getRequestHeaders());
			Headers headers = t.getRequestHeaders();
			for (HashMap.Entry<String,List<String>> entry : headers.entrySet()) {
				String key = entry.getKey();
				List<String> values = entry.getValue();
				System.out.println("Key: " + key);
				for(String value : values)
					System.out.println("value: " + value);
				
				if(key.equals("Index")){
					IndexRequest(t, values.get(0));
					return;	
				}
				
				if(key.equals("Song")){
					SongRequest(t, values.get(0));
					return;
				}
			}
			
            String response = "This is the response";
           	t.sendResponseHeaders(200, response.length());
           	OutputStream os = t.getResponseBody();
          	os.write(response.getBytes());
           	os.close();
		}
		
		
		static void IndexRequest(HttpExchange t, String song) throws IOException {
			
			String path = "/Users/Austin/Development/projects/Music_Stream/Music/prog_index.m3u8";
			File index = new File(path);
			byte[] data = Files.readAllBytes(index.toPath());
			//String test = index.toURI().toString();
			
			t.sendResponseHeaders(200, data.length);
			OutputStream os = t.getResponseBody();
			for(int i = 0; i < data.length; i++)
				os.write(data[i]);
			os.close();
		}
		
		
		static void SongRequest(HttpExchange t, String songPartPath) throws IOException{
				
			//String path = "/Users/Austin/Development/projects/Music_Stream/Music/Macklemore_Thrift_shop0.mp3";
			File song = new File(songPartPath);
			byte[] data = Files.readAllBytes(song.toPath());
			//String songURI = song.toURI().toString();
			
			t.sendResponseHeaders(200, data.length);
			OutputStream os = t.getResponseBody();
			for(int i = 0; i < data.length; i++)
				os.write(data[i]);
			os.close();
		}
	}	
}