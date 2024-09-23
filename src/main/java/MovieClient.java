import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class MovieClient {
    static Map<String, Integer> genres = new HashMap<>();
    static List<String> queries = new ArrayList<>();

    static {
        genres.put("драма", 18);
        genres.put("комедия", 35);
        genres.put("фантастика", 878);
        genres.put("ужасы", 27);
        genres.put("боевик", 28);

        queries.add("Бот, посоветуй фильм драма");
        queries.add("Бот, посоветуй фильм комедия");
        queries.add("Бот, посоветуй фильм фантастика");
        queries.add("Бот, посоветуй фильм ужасы");
        queries.add("Бот, посоветуй фильм боевик");
        queries.add("Бот, посоветуй фильм");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Базовый url
        String url = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=418969814078357800fcf2d3df514a26&with_original_language=en&language=ru";

        // Чтобы найти по жанру, необходимо к ссылке добавить with_genres={GENRE_ID}.
        // Пример:
        // url += &with_genres=18

        HttpClient client = HttpClient.newHttpClient();
        Random random = new Random();

        for (String query : queries) {
            String genreUrl = url;
            String genre = query.substring(query.lastIndexOf(" ") + 1);

            if (!genre.equals("фильм")) {
                genreUrl += "&with_genres=" + genres.get(genre);
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(genreUrl))
                    .build();
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {
                JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray movies = body.get("results").getAsJsonArray();
                int randomIndex = random.nextInt(movies.size());

                String resultTitle = movies.get(randomIndex).getAsJsonObject().get("title").getAsString();

                System.out.println(query + ": " + "\"" + resultTitle + "\"");
            }
        }
    }
}