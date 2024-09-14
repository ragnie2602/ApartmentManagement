package Resources.Constant;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Tool {
    public static ImageIcon BytesToImage(byte[] bytes) {
        return new ImageIcon(bytes);
    }

    public static int checkDay(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) return 29;
                else return 28;
        }
    }

    public static long DateToMillis(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy");
        long millis = 0L;
        try {
            millis = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }

    public static byte[] imageToBytes(ImageIcon image) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();

        image.paintIcon(null, g, 0, 0);
        g.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        return imageInByte;
    }

    public static String MillisToDate(long millis) {
        DateFormat simple = new SimpleDateFormat("dd-MM-yyy");
        Date result = new Date(millis);
        return simple.format(result);
    }

    public static ImageIcon resize(ImageIcon img, int height, int width) {
        BufferedImage resizeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = resizeImage.getGraphics();
        Image image = img.getImage();

        g.drawImage(image, 0, 0, width, height, null);

        return new ImageIcon(resizeImage);
    }    
}
