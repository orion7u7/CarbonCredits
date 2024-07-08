import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.google.gson.Gson;
import java.util.List;
import com.google.gson.reflect.TypeToken;

public class ApiClient {
    private static final String API_BASE_URL = "http://localhost:8080/api";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final Gson gson = new Gson();

    public static String sendEvaluation(Long areaId, List<Shape> shapes) throws Exception {
        String url = API_BASE_URL + "/evaluaciones";

        EvaluationData evaluationData = new EvaluationData(areaId, shapes);
        String jsonBody = gson.toJson(evaluationData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Error sending evaluation: " + response.statusCode());
        }
    }

    public static List<AreaEvaluada> getAreasEvaluadas() throws Exception {
        String url = API_BASE_URL + "/areas";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Type listType = new TypeToken<List<AreaEvaluada>>(){}.getType();
            return gson.fromJson(response.body(), listType);
        } else {
            throw new Exception("Error getting areas: " + response.statusCode());
        }
    }

    public static AreaEvaluada getAreaById(Long id) throws Exception {
        String url = API_BASE_URL + "/areas/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), AreaEvaluada.class);
        } else {
            throw new Exception("Error getting area: " + response.statusCode());
        }
    }
    private static class EvaluationData {
        Long areaId;
        List<Shape> shapes;

        EvaluationData(Long areaId, List<Shape> shapes) {
            this.areaId = areaId;
            this.shapes = shapes;
        }
    }
}