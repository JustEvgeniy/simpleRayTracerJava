import Geometry.Vec3;

public interface SceneObject {
    Intersection rayIntersect(Vec3 origin, Vec3 direction);
}
