package pl.thinkdata.b2bbase.datafile.util;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TinyPngConverter {
    public static final int width = 250;
    public static final int height = 125;
    public static final String method = "fit";
    private String tinyPngApiKey;

    public TinyPngConverter(@Value("${tiny.api.key}") String tinyPngApiKey) {
        this.tinyPngApiKey = tinyPngApiKey;
    }

    public void convertImage(String filename, String fileNameToSave, String dir) throws IOException {
        Tinify.setKey(tinyPngApiKey);
        Source source = Tinify.fromFile("./data/" + dir + "/temp/" + filename);
        Options options = new Options()
                .with("method", method)
                .with("width", width)
                .with("height", height);
        Source resized = source.resize(options);
        resized.toFile("./data/logos/" + fileNameToSave);
    }
}
