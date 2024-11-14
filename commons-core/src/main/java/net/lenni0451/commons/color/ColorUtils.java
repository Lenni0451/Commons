package net.lenni0451.commons.color;

import lombok.experimental.UtilityClass;
import net.lenni0451.commons.math.MathUtils;

import java.awt.Color;

@UtilityClass
public class ColorUtils {

    /**
     * Get a color calculated by the current time.<br>
     * Calling this method subsequently will generate an RGB rainbow color effect.<br>
     * The offset depends on the used divider. With a divider of {@code 7.5F} the color will be the same with the offset of {@code 0} and {@code 7500}.<br>
     * The higher the divider the slower and smoother the color will change.
     *
     * @param offset  The offset added to the current time
     * @param divider The divider for the current time
     * @return The calculated color
     */
    public static Color getRainbowColor(final int offset, final float divider) {
        long l = System.currentTimeMillis() + offset;
        l %= (int) (1000 * divider);
        return Color.getHSBColor(l / divider / 1000F, 1F, 1F);
    }

    /**
     * Set the red value of a color.<br>
     * This will generate a new color object unless the red value is already the same.
     *
     * @param color The color to modify
     * @param r     The new red value
     * @return The modified color
     */
    public static Color setRed(final Color color, final int r) {
        if (color.getRed() == r) return color;
        return new Color(MathUtils.clamp(r, 0, 255), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Set the green value of a color.<br>
     * This will generate a new color object unless the green value is already the same.
     *
     * @param color The color to modify
     * @param g     The new green value
     * @return The modified color
     */
    public static Color setGreen(final Color color, final int g) {
        if (color.getGreen() == g) return color;
        return new Color(color.getRed(), MathUtils.clamp(g, 0, 255), color.getBlue(), color.getAlpha());
    }

    /**
     * Set the blue value of a color.<br>
     * This will generate a new color object unless the blue value is already the same.
     *
     * @param color The color to modify
     * @param b     The new blue value
     * @return The modified color
     */
    public static Color setBlue(final Color color, final int b) {
        if (color.getBlue() == b) return color;
        return new Color(color.getRed(), color.getGreen(), MathUtils.clamp(b, 0, 255), color.getAlpha());
    }

    /**
     * Set the alpha value of a color.<br>
     * This will generate a new color object unless the alpha value is already the same.
     *
     * @param color The color to modify
     * @param a     The new alpha value
     * @return The modified color
     */
    public static Color setAlpha(final Color color, final int a) {
        if (color.getAlpha() == a) return color;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), MathUtils.clamp(a, 0, 255));
    }

    /**
     * Invert a color.
     *
     * @param color The color to invert
     * @return The inverted color
     */
    public static Color invert(final Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
    }

    /**
     * Multiply a color with a factor.<br>
     * The alpha value will not be changed.
     *
     * @param color  The color to multiply
     * @param factor The factor to multiply with
     * @return The multiplied color
     */
    public static Color multiply(final Color color, final float factor) {
        return multiply(color, factor, factor, factor, 1);
    }

    /**
     * Multiply the alpha value of a color with a factor.<br>
     * The RGB values will not be changed.
     *
     * @param color  The color to multiply
     * @param factor The factor to multiply with
     * @return The multiplied color
     */
    public static Color multiplyAlpha(final Color color, final float factor) {
        return multiply(color, 1, 1, 1, factor);
    }

    /**
     * Multiply a color with a factor.
     *
     * @param color  The color to multiply
     * @param factor The factor to multiply with
     * @return The multiplied color
     */
    public static Color multiplyAll(final Color color, final float factor) {
        return multiply(color, factor, factor, factor, factor);
    }

    /**
     * Multiply a color with a factor.
     *
     * @param color   The color to multiply
     * @param rFactor The factor to multiply the red value with
     * @param gFactor The factor to multiply the green value with
     * @param bFactor The factor to multiply the blue value with
     * @param aFactor The factor to multiply the alpha value with
     * @return The multiplied color
     */
    public static Color multiply(final Color color, final float rFactor, final float gFactor, final float bFactor, final float aFactor) {
        return new Color(
                MathUtils.clamp((int) (color.getRed() * rFactor), 0, 255),
                MathUtils.clamp((int) (color.getGreen() * gFactor), 0, 255),
                MathUtils.clamp((int) (color.getBlue() * bFactor), 0, 255),
                MathUtils.clamp((int) (color.getAlpha() * aFactor), 0, 255)
        );
    }

    @Deprecated
    public static Color interpolate(final Color color1, final Color color2, final float progress) {
        return interpolate(progress, color1, color2);
    }

    /**
     * Interpolate between two colors with a progress.<br>
     * The progress is a value between 0 and 1.
     *
     * @param progress The progress between the two colors
     * @param color1   The first color
     * @param color2   The second color
     * @return The interpolated color
     */
    public static Color interpolate(final float progress, final Color color1, final Color color2) {
        return new Color(
                MathUtils.clamp((int) (color1.getRed() + (color2.getRed() - color1.getRed()) * progress), 0, 255),
                MathUtils.clamp((int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * progress), 0, 255),
                MathUtils.clamp((int) (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * progress), 0, 255),
                MathUtils.clamp((int) (color1.getAlpha() + (color2.getAlpha() - color1.getAlpha()) * progress), 0, 255)
        );
    }

    /**
     * Interpolate between multiple colors with a progress.<br>
     * The progress is a value between 0 and 1.<br>
     * The colors and steps must have the same length.
     *
     * @param progress The progress between the colors
     * @param colors   The colors to interpolate between
     * @param steps    The steps between the colors
     * @return The interpolated color
     */
    public static Color interpolate(final float progress, final Color[] colors, final float[] steps) {
        if (colors.length != steps.length) throw new IllegalArgumentException("Colors and steps must have the same length");
        if (colors.length == 0) throw new IllegalArgumentException("Colors and steps must have a length greater than 0");

        if (colors.length == 1) return colors[0];
        if (progress <= steps[0]) return colors[0];
        if (progress >= steps[steps.length - 1]) return colors[colors.length - 1];
        for (int i = 0; i < steps.length; i++) {
            if (progress < steps[i]) {
                float stepProgress = (progress - steps[i - 1]) / (steps[i] - steps[i - 1]);
                return interpolate(stepProgress, colors[i - 1], colors[i]);
            }
        }
        return colors[colors.length - 1];
    }

    /**
     * Interpolate between multiple colors with a progress.<br>
     * The progress is a value between 0 and 1.<br>
     * The colors must have a length greater than 0.
     *
     * @param progress The progress between the colors
     * @param colors   The colors to interpolate between
     * @return The interpolated color
     */
    public static Color interpolate(final float progress, final Color... colors) {
        if (colors.length == 0) throw new IllegalArgumentException("Colors must have a length greater than 0");
        if (colors.length == 1) return colors[0];
        if (colors.length == 2) return interpolate(progress, colors[0], colors[1]);

        float step = 1F / (colors.length - 1);
        float[] steps = new float[colors.length];
        for (int i = 0; i < colors.length; i++) steps[i] = step * i;
        return interpolate(progress, colors, steps);
    }

    /**
     * Get the distance between two colors.
     *
     * @param color1 The first color
     * @param color2 The second color
     * @return The distance between the two colors
     */
    public static int distance(final Color color1, final Color color2) {
        return Math.abs(color1.getRed() - color2.getRed())
                + Math.abs(color1.getGreen() - color2.getGreen())
                + Math.abs(color1.getBlue() - color2.getBlue());
    }

    /**
     * Mix two colors together.
     *
     * @param color1 The first color
     * @param color2 The second color
     * @return The mixed color
     */
    public static Color mix(final Color color1, final Color color2) {
        return new Color(
                color1.getRed() * color2.getRed() / 255,
                color1.getGreen() * color2.getGreen() / 255,
                color1.getBlue() * color2.getBlue() / 255,
                color1.getAlpha() * color2.getAlpha() / 255
        );
    }

}
