import Geometry.Vec3;

public class Material {
    public double refractiveIndex;
    public double[] albedo;
    public Vec3 diffuseColor;
    public double specularExponent;

    public Material(Vec3 diffuseColor) {
        refractiveIndex = 1;
        albedo = new double[]{1, 0, 0, 0};
        this.diffuseColor = diffuseColor;
        specularExponent = 0;
    }

    public Material(double refractiveIndex, double[] albedo, Vec3 diffuseColor, double specularExponent) {
        this.refractiveIndex = refractiveIndex;
        this.albedo = albedo;
        this.diffuseColor = diffuseColor;
        this.specularExponent = specularExponent;
    }
}
