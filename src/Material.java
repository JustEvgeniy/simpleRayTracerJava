import Geometry.Vec3;

public class Material {
    public double refractiveIndex;
    public double[] albedo;
    public Vec3 diffuseColor;
    public double specularExponent;

    public Material(double refractiveIndex, double[] albedo, Vec3 diffuseColor, double specularExponent) {
        this.refractiveIndex = refractiveIndex;
        this.albedo = albedo;
        this.diffuseColor = diffuseColor;
        this.specularExponent = specularExponent;
    }
}
