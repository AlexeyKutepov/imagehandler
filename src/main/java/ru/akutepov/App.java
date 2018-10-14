package ru.akutepov;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class App
{

    /**
     * Метод обработчик изображения.
     * Преобразует цветное изображение в чёрно-белое и задаёт ему новые размеры
     * @param colorImage цветное изображение
     * @param width новая ширина
     * @param height новая высота
     * @return обработанное изображение {@link BufferedImage}
     */
    public static BufferedImage handle(BufferedImage colorImage, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be greater than 0");
        }
        if (colorImage != null) {
            Image resizedImage = colorImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            resultImage.getGraphics().drawImage(resizedImage, 0, 0, null);
            return resultImage;
        } else {
            return null;
        }
    }

    /**
     * Основной метод приложения
     * @param args массив аргументов. Приложение ожидает на входе 3 аргумента:
     *             1. args[0] - URL изображения (формата http://host/path или file:/path)
     *             2. args[1] - ширина, которую нужно задать изображению
     *             3. args[3] - высота, которую нужно задать изображению
     */
    public static void main( String[] args ) {
        if (args.length == 3) {
            URL imageUrl;
            try {
                imageUrl = new URL(args[0]);
            } catch (MalformedURLException e) {
                System.out.println("Invalid url: " + e.getMessage());
                return;
            }

            int width;
            int height;
            try {
                width = Integer.parseInt(args[1]);
                height = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid width or height: " + e.getMessage());
                return;
            }
            if (width <= 0 || height <= 0) {
                System.out.println("width and height must be greater than 0");
                return;
            }

            // Чтение изображения
            final BufferedImage colorImage;
            try {
                colorImage = ImageIO.read(imageUrl);
            } catch (IOException e) {
                System.out.println("Image reading error: " + e.getMessage());
                return;
            }

            // Обработка изображения
            BufferedImage resultImage = handle(colorImage, width, height);

            if (resultImage != null) {
                // Сохранение обработанного изображения
                File out = new File(FilenameUtils.getName(imageUrl.getPath()));
                try {
                    ImageIO.write(resultImage, FilenameUtils.getExtension(imageUrl.getPath()), out);
                } catch (IOException e) {
                    System.out.println("Image saving error: " + e.getMessage());
                    return;
                }
                System.out.println("Image successfully processed. Location: " + out.getAbsolutePath());
            } else {
                System.out.println("Image handle error. Result is null");
            }
        } else {
            System.out.println("There is should be 3 arguments: image url, width and height");
        }
    }
}
