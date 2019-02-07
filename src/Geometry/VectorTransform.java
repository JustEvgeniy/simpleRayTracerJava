package Geometry;

public class VectorTransform {
    public static Vec3 reflect(Vec3 I, Vec3 N) {
        return N.mul(2).mul(I.dot(N)).sub(I);
    }

    public static Vec3 refract(Vec3 I, Vec3 N, double eta_t) {
        return refract(I, N, eta_t, 1);
    }

    public static Vec3 refract(Vec3 I, Vec3 N, double eta_t, double eta_i) {
        double cosi = -I.dot(N);

        if (cosi < 0) {
            return refract(I, N.inverse(), eta_i, eta_t);
        }

        double eta = eta_i / eta_t;
        double k = 1 - eta * eta * (1 - cosi * cosi);
        return k < 0 ? new Vec3(1, 0, 0) : I.mul(eta).add(N.mul(eta * cosi - Math.sqrt(k)));
    }
}
