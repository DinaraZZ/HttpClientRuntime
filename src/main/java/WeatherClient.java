import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=Астана&units=metric&lang=ru&appid=79d1ca96933b0328e1c7e3e7a26cb347"))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonElement bodyElement = JsonParser.parseString(response.body());
        JsonObject body = bodyElement.getAsJsonObject();

        JsonObject wind = body.get("wind").getAsJsonObject();
        double windSpeed = wind.get("speed").getAsDouble();

        System.out.println("Скорость ветра: " + windSpeed + " м/с");


        JsonParser.parseString(response.body());
    }
}
