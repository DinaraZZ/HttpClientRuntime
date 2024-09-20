import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StarWarsClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String URL = "https://swapi.dev/api/people/?format=json";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray characters = body.get("results").getAsJsonArray();

            int maxHeight = -1;
            String maxHeightName = new String();

            System.out.println("Персонажи: ");
            for (JsonElement characterElement : characters) {
                JsonObject character = characterElement.getAsJsonObject();
                String name = character.get("name").getAsString();
                System.out.println("\t" + name);

                int height = character.get("height").getAsInt();
                if (height > maxHeight) {
                    maxHeight = height;
                    maxHeightName = name;
                }
            }
            if (maxHeight > 0) {
                System.out.println();
                System.out.println("Самый высокий персонаж: " + maxHeightName);
                System.out.println("Рост: " + maxHeight);
            }
        }
    }
}
