package pl.thinkdata.b2bbase.datafile.util;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TinyPngConverter {
    public static final int width = 400;
    public static final int height = 200;
    public static final String method = "fit";
    private String tinyPngApiKey;

    public TinyPngConverter(@Value("${tiny.api.key}") String tinyPngApiKey) {
        this.tinyPngApiKey = tinyPngApiKey;
    }

    public void convertImage(String filename) throws IOException {
        Tinify.setKey(tinyPngApiKey);
        Source source = Tinify.fromFile("./data/logos/" + filename);
        Options options = new Options()
                .with("method", method)
                .with("width", width)
                .with("height", height);
        Source resized = source.resize(options);
        resized.toFile("./data/logos/t_" + filename);
    }
}
