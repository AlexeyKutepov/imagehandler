package ru.akutepov;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;


public class AppTest 
{

    private BufferedImage bufferedImage;

    /**
     * Инициализация заглушки для тестирования
     */
    @Before
    public void initTest() {
        bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Тестирование правильности преобразования изображения
     */
    @Test
    public void testHandle() {
        BufferedImage result = App.handle(bufferedImage, 50, 50);
        assertEquals("Image type must be equal BufferedImage.TYPE_BYTE_GRAY", BufferedImage.TYPE_BYTE_GRAY, result.getType());
        assertEquals("Image width must be equal 50", 50, result.getWidth());
        assertEquals("Image height must be equal 50", 50, result.getHeight());
    }

    /**
     * Тестирование проверки на null
     */
    @Test
    public void testHandleCheckNull() {
        BufferedImage result = App.handle(null, 50, 50);
        assertEquals("Result must be null", null, result);
    }

    /**
     * Тестирование исключения при некорректных значениях ширины
     */
    @Test(expected = IllegalArgumentException.class)
    public void testHandleInvalidWidth() {
        App.handle(bufferedImage, 0, 100);
    }

    /**
     * Тестирование исключения при некорректных значениях высоты
     */
    @Test(expected = IllegalArgumentException.class)
    public void testHandleInvalidHeight() {
        App.handle(bufferedImage, 100, 0);
    }
}
