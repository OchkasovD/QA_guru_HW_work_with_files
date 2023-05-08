package guru.qa.files;
import com.google.gson.Gson;
import guru.qa.files.pojos.Human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FamilyJsonTests {

   private final ClassLoader cl = FamilyJsonTests.class.getClassLoader();
    @DisplayName(value = "Парсинг JSON")
    @Test
    void jsonTest() throws Exception {
        Gson objectMapper = new Gson();
        try (InputStream is = cl.getResourceAsStream("family.json");
        ) {
            try (InputStreamReader reader = new InputStreamReader(is);
                ){
                Human humanData = objectMapper.fromJson(reader, Human.class);
                Assertions.assertEquals(377, humanData.getUser().getUserID());
                Assertions.assertEquals("Ivan", humanData.getUser().getName());
                Assertions.assertEquals(3, humanData.getUser().getChildren().get(0).getAge());
                Assertions.assertEquals("Masha", humanData.getUser().getChildren().get(0).getName());
                //Assertions.assertTrue(humanData.getUser().getChildren().get(0).isSchoolKid());
                }
        }
     }
    }




