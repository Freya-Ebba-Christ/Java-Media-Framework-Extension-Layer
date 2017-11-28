/*
 * VideoPluginExample.java
 *
 * This example opens the webcam uses a plugin, which performs basic skin detection on video frames.
 * The plugIn performs conversion between RGB and YCBCR colour spaces and exploits the fact that skin colour is clustered in the chromatic colour space
 */

package examples.basic.advanced;
import classification.ALNClassifier;
import datasource.VideoDataSource;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.media.Format;
import javax.media.format.VideoFormat;
import plugins.video.VideoDataAccessor;
import simple_player.SimpleProcessor;
import simple_player.SimplePlayer;
import utilities.graphics.ByteImage;
import utilities.graphics.ColorSpaceConverter;

public class VideoColorClassificationTest {

    /** Creates a new instance of VideoColorClassificationTest */
    public VideoColorClassificationTest() {
    }

    public static void main(String[] args) {
        VideoDataSource aVideoDataSource = new VideoDataSource();
        VideoFormat vf = new VideoFormat(VideoFormat.RGB, new Dimension(640, 480), Format.NOT_SPECIFIED, null, 20.0f);

        aVideoDataSource.setVideoCaptureFormat(vf);
        try {
            aVideoDataSource.init();
        } catch (Exception e) {
            System.out.println(e);
        }

        Vector codecChain = new Vector();
        ColorVideoImage aVideoImage = new ColorVideoImage();
        codecChain.add(aVideoImage);
        SimpleProcessor aVideoProcessor = new SimpleProcessor(aVideoDataSource.getDataSource(), codecChain);
        SimplePlayer aSimpleVideoPlayer = new SimplePlayer(aVideoProcessor.getDataSource());

        aVideoProcessor.start();
        aSimpleVideoPlayer.start();
        aSimpleVideoPlayer.setVisible(true);
    }
}

class ColorVideoImage extends VideoDataAccessor {

    private double[] pixelColor = new double[3];
    private ByteImage tmp_Image;
    private ColorSpaceConverter colorSpaceConverter = new ColorSpaceConverter();

    public ColorVideoImage() {
    }

    public void processInData() {
        if (tmp_Image == null) {
            tmp_Image = new ByteImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        }
        System.arraycopy(getVideoImage().getPixels(), 0, tmp_Image.getPixels(), 0, getInLength());
    }

    public void processOutData() {

        for (int yPos = 0; yPos < getHeight(); yPos++) {
            for (int xPos = 0; xPos < getWidth(); xPos++) {

                int red = getVideoImage().getPixel(xPos, yPos).getRed();
                int green = getVideoImage().getPixel(xPos, yPos).getGreen();
                int blue = getVideoImage().getPixel(xPos, yPos).getBlue();

                colorSpaceConverter.rgb2ycbcr(red, green, blue);

                if (((red > colorSpaceConverter.getColor()[2]) && (green > red) && (green > colorSpaceConverter.getColor()[2])) | colorSpaceConverter.getColor()[2] > green) {
                    getVideoImage().setPixel(xPos, yPos, 0, 0, 0);
                } else {
                    getVideoImage().setPixel(xPos, yPos, red, colorSpaceConverter.getColor()[2], 0);
                }

                red = getVideoImage().getPixel(xPos, yPos).getRed();
                green = getVideoImage().getPixel(xPos, yPos).getGreen();
                blue = getVideoImage().getPixel(xPos, yPos).getBlue();
                if (green > 150 | red < 80) {
                    getVideoImage().setPixel(xPos, yPos, 0, 0, 0);
                }
                if (red >= 80) {
                    tmp_Image.setPixel(xPos, yPos, red, green, blue);
                }
            }
        }
        System.arraycopy(tmp_Image.getPixels(), 0, getVideoImage().getPixels(), 0, getInLength());
    }
}