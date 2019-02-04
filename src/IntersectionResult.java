public class IntersectionResult {
    public boolean intersection;
    public double distance;

    public IntersectionResult(boolean intersection) {
        this.intersection = intersection;
    }

    public IntersectionResult(boolean intersection, double distance) {
        this.intersection = intersection;
        this.distance = distance;
    }
}
