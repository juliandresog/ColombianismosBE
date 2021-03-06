/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author agomez
 */
public class ImageUtil {

    public static BufferedImage loadImage(String ref) {
        BufferedImage bimg = null;
        try {

            bimg = ImageIO.read(new File(ref));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
}
