package Renderer;

import Geometry.Vec3;

import java.util.Arrays;
import java.util.Objects;

class Material {
    final double refractiveIndex;
    final double[] albedo;
    final Vec3 diffuseColor;
    final double specularExponent;

    Material(Vec3 diffuseColor) {
        refractiveIndex = 1;
        albedo = new double[]{1, 0, 0, 0};
        this.diffuseColor = diffuseColor;
        specularExponent = 0;
    }

    Material(double refractiveIndex, double[] albedo, Vec3 diffuseColor, double specularExponent) {
        this.refractiveIndex = refractiveIndex;
        this.albedo = albedo;
        this.diffuseColor = diffuseColor;
        this.specularExponent = specularExponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Double.compare(material.refractiveIndex, refractiveIndex) == 0 &&
                Double.compare(material.specularExponent, specularExponent) == 0 &&
                Arrays.equals(albedo, material.albedo) &&
                Objects.equals(diffuseColor, material.diffuseColor);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(refractiveIndex, diffuseColor, specularExponent);
        result = 31 * result + Arrays.hashCode(albedo);
        return result;
    }

    @Override
    public String toString() {
        return "Material{" +
                "refractiveIndex=" + refractiveIndex +
                ", albedo=" + Arrays.toString(albedo) +
                ", diffuseColor=" + diffuseColor +
                ", specularExponent=" + specularExponent +
                '}';
    }
}
