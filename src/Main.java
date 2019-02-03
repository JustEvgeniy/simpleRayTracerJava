import Geometry.Vec2;
import Geometry.Vec3;

public class Main {
    public static void main(String[] args) {
        Vec2 vec2 = new Vec2(1., 2.);
        System.out.println("vec2 = " + vec2);
        System.out.println("vec2.length() = " + vec2.length());
        System.out.println("vec2.normalize() = " + vec2.normalize());

        Vec3 vec3 = new Vec3(1., 2., 3.);
        System.out.println("vec3 = " + vec3);
        System.out.println("vec3.length() = " + vec3.length());
        System.out.println("vec3.normalize() = " + vec3.normalize());
    }
}
