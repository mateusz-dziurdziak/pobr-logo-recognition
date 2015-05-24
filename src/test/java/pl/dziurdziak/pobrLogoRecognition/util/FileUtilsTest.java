package pl.dziurdziak.pobrLogoRecognition.util;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.configuration.Configuration;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author Mateusz Dziurdziak
 */
public class FileUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testReadImageFromNonExistingFile() {
        FileUtils.readImageFromFile("src/test/resources/lll.jpg");
    }

    @Test
    public void testReadImageFromFile() {
        Image image = FileUtils.readImageFromFile("src/test/resources/logo_resized.jpg");
        assertNotNull(image);
    }

    @Test
    public void testReadAndWrite() {
        Image image = FileUtils.readImageFromFile("src/test/resources/logo_resized.jpg");
        FileUtils.writeImageToFile(image, "build/tmp/outTest.jpg");
        assertThat(new File("build/tmp/outTest.jpg")).exists();
    }

    @Test
    public void testReadConfiguration() {
        Configuration configuration = FileUtils.readConfiguration("src/test/resources/config_1.json");
        assertThat(configuration.getOutputDir()).isEqualTo("outDir");
        assertThat(configuration.getOutputFileName()).isEqualTo("outFile");
        assertThat(configuration.isExportFilesAfterEachStep()).isTrue();
        assertThat(configuration.getFilters()).hasSize(5);
    }

}