package pl.thinkdata.b2bbase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("/")
    public String getTest() {
        return "B2Bbase running ...";
    }
}
