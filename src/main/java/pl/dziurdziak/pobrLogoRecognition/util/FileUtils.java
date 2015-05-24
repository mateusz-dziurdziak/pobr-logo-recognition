package pl.dziurdziak.pobrLogoRecognition.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.configuration.Configuration;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Mateusz Dziurdziak
 */
public class FileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Reads image from file and returns it as {@link Image}
     *
     * @param filePath file path
     * @return readed image
     * @throws NullPointerException     if filePath is null
     * @throws IllegalArgumentException if file doesn't exist or file is a directory
     * @throws RuntimeException         if error occurs while reading file
     */
    @NotNull
    public static Image readImageFromFile(String filePath) {
        checkNotNull(filePath, "filePath cannot be null");
        File file = new File(filePath);
        checkArgument(file.exists(), "File %s doesn't exist", filePath);
        checkArgument(!file.isDirectory(), "File %s is a directory", filePath);

        LOG.info("Reading file {}", filePath);

        try (FileInputStream fis = new FileInputStream(file)) {
            BufferedImage bufferedImage = ImageIO.read(fis);
            LOG.info("File read successful.");
            return ImageUtils.convertBufferedImage(bufferedImage);
        } catch (IOException e) {
            LOG.error("Error while reading file {}", e, filePath);
            throw new RuntimeException("IOException", e);
        }
    }

    public static void writeImageToFile(Image image, String filePath) {
        checkNotNull(image, "image cannot be null");
        checkArgument(isNotBlank(filePath), "filePath is blank");

        LOG.info("Writing to file {}", filePath);

        File file = new File(filePath);
        file.getParentFile().mkdirs();

        BufferedImage bufferedImage = ImageUtils.convertImage(image);
        try {
            ImageIO.write(bufferedImage, "jpg", file);
            LOG.info("Writing to file successful");
        } catch (IOException e) {
            LOG.error("Error while writing image to file. {}", e, filePath);
            throw new RuntimeException(e);
        }
    }

    public static Configuration readConfiguration(String filePath) {
        checkNotNull(filePath, "filePath cannot be null");
        File file = new File(filePath);
        checkArgument(file.exists(), "File %s doesn't exist", filePath);
        checkArgument(!file.isDirectory(), "File %s is a directory", filePath);

        LOG.info("Reading configuration file {}", filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, Configuration.class);
        } catch (IOException e) {
            LOG.error("Error while reading configuration from file {}", e, filePath);
            throw new RuntimeException(e);
        }
    }
}
