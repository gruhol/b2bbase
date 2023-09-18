package pl.thinkdata.b2bbase.company.comonent;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugGenerator {
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String result = deletePolishChars(nowhitespace);
        result = Normalizer.normalize(result, Normalizer.Form.NFKD);
        result = EDGESDHASHES.matcher(result).replaceAll("");
        result = result.replace("--", "-");
        return result.toLowerCase(Locale.ENGLISH);
    }

    private static String deletePolishChars(String stringWithPolishChar) {
        return stringWithPolishChar.replace("ą" , "a")
                .replace("ć" , "c")
                .replace("ę" , "e")
                .replace("ł" , "l")
                .replace("ó" , "o")
                .replace("ń" , "n")
                .replace("ś" , "s")
                .replace("ż" , "z")
                .replace("ź" , "z")
                .replace("Ą" , "A")
                .replace("Ć" , "C")
                .replace("Ę" , "E")
                .replace("Ł" , "L")
                .replace("Ó" , "O")
                .replace("Ń" , "N")
                .replace("Ś" , "S")
                .replace("Ż" , "Z")
                .replace("Ź" , "Z");
    }
}
