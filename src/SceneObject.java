import Geometry.Vec3;

public interface SceneObject {
    IntersectionResult rayIntersect(Vec3 origin, Vec3 direction);
}
