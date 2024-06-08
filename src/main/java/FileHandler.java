import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Shape.class, new ShapeAdapter())
            .create();

    public static void saveShapes(List<Shape> shapes, String filename) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(shapes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Shape> loadShapes(String filename) {
        try (Reader reader = new FileReader(filename)) {
            Type listType = new TypeToken<ArrayList<Shape>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static class ShapeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {
        @Override
        public JsonElement serialize(Shape src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
            result.add("properties", context.serialize(src, src.getClass()));
            return result;
        }

        @Override
        public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");

            try {
                return context.deserialize(element, Class.forName("your.package.name." + type));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown shape type: " + type, e);
            }
        }
    }
}