import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class WeatherService {
    private static final String API_KEY = "3e1316b7be0fdb4601e6202d3c6a5268"; 
    public WeatherInfo fetchWeather(String city, boolean isCelsius) throws Exception {
        String unit = isCelsius ? "metric" : "imperial";
        String urlString = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s",
            URLEncoder.encode(city, "UTF-8"), API_KEY, unit
        );

        JSONObject obj = readJsonFromUrl(urlString);
        String description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
        double temp = obj.getJSONObject("main").getDouble("temp");
        double feelsLike = obj.getJSONObject("main").getDouble("feels_like");
        String iconCode = obj.getJSONArray("weather").getJSONObject(0).getString("icon");
        String iconPath = "icons/" + iconCode + ".png";

        File iconFile = new File(iconPath);
        if (!iconFile.exists()) {
            try (InputStream in = new URL("https://openweathermap.org/img/wn/" + iconCode + "@2x.png").openStream()) {
                Files.copy(in, iconFile.toPath());
            } catch (IOException e) {
                System.out.println("Failed to download icon: " + e.getMessage());
            }
        }

        return new WeatherInfo(description, temp, feelsLike, iconPath);
    }

    private JSONObject readJsonFromUrl(String urlStr) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            return new JSONObject(sb.toString());
        }
    }
}
