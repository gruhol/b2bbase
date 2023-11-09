package pl.thinkdata.b2bbase.company.component;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SlugGeneratorTest {

    @ParameterizedTest
    @CsvSource({
            "Firma Bidna Lecz Solidna, firma-bidna-lecz-solidna",
            "Dąbrowski Karoluś-Cóśtam, dabrowski-karolus-costam",
            "Dąbrowski Karoluś-Cóśtam, dabrowski-karolus-costam",
            "Dąbrowski Karoluś----Cóśtam, dabrowski-karolus--costam",
    })
    void shouldReturnSlugFormString(String toSlug, String slug) {
        String result = SlugGenerator.toSlug(toSlug);
        assertEquals(slug, result);
    }

}