import java.util.List;

public class AreaCalculator {
    public static double calculateNativeForestPercentage(List<Shape> shapes, double totalArea) {
        double nativeForestArea = shapes.stream()
                .mapToDouble(Shape::getArea)
                .sum();
        return (nativeForestArea / totalArea) * 100;
    }
}