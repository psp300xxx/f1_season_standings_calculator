package controller;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JSONCallManagerPlainJava implements JSONCallManager {


    public StringBuilder get(String urlString) throws JSONDownloadException {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new JSONDownloadException(e);
        }

// Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JSONDownloadException(e);
        }

// Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

// This line makes the request
        try {
            InputStream responseStream = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            sb.append(new String(responseStream.readAllBytes()));
            return sb;
        } catch (IOException e) {
            throw new JSONDownloadException(e);
        }
    }
}
