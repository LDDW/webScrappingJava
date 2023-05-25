import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ScrapperTest {

    public File file = new File("Resultat/result.txt");

    @Test
    public void TestFileIsExist(){
        assertTrue(file.exists(), "Le fichier existe");
    }

    @Test
    public void TestFileIsNotEmpty(){
        assertNotEquals(0, file.length(), "Le fichier contient de la data");
    }

}
