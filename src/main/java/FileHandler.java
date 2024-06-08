import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class FileHandler {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Shape.class, new ShapeAdapter())
            .create();

    public static void saveShapes(List<Shape> shapes, String filename) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Color.class, new ColorAdapter());

        Gson gson = gsonBuilder.create();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(shapes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Shape> loadShapes(String filename) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Color.class, new ColorAdapter());
        gsonBuilder.registerTypeAdapter(Shape.class, new ShapeAdapter());

        Gson gson = gsonBuilder.create();

        try (FileReader reader = new FileReader(filename)) {
            Type shapeListType = new TypeToken<List<Shape>>(){}.getType();
            return gson.fromJson(reader, shapeListType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ShapeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {
        @Override
        public JsonElement serialize(Shape src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = context.serialize(src, src.getClass()).getAsJsonObject();
            jsonObject.addProperty("type", src.getClass().getSimpleName());
            return jsonObject;
        }

        @Override
        public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("figureType").getAsString();
            Class<? extends Shape> clazz;
            switch (type) {
                case "Rectangle":
                    clazz = Rectangle.class;
                    break;
                case "Square":
                    clazz = Square.class;
                    break;
                case "Ellipse":
                    clazz = Ellipse.class;
                    break;
                case "Circle":
                    clazz = Circle.class;
                    break;
                default:
                    throw new JsonParseException("Invalid figure type: " + type);
            }

            return context.deserialize(jsonObject, clazz);
        }
    }
}