package guru.qa.files;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class TestZipFiles {
  //  private static final String FILE_XLSX = "Александр";
    private static final String[][] FILE_CSV = new String[][]{
            {"Name", "Gender", "Age"},
            {"Alex", "Man", "20"},
            {"Julia", "Female", "21"}
    };
    private static final String FILE_PDF = "СОГЛАШЕН";


    private static final ClassLoader classLoader = TestZipFiles.class.getClassLoader();

    @Test
    @DisplayName("Проверка файла CSV в архиве")
    void csvParseZipTest() throws Exception {

        ClassLoader cl = this.getClass().getClassLoader();

        try (InputStream is = cl.getResourceAsStream("TestArchive.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains("csv") && !entry.getName().contains("name.csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> csv = csvReader.readAll();
                        Assertions.assertArrayEquals(FILE_CSV[1], csv.get(1));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка xlsx файла")
    void xlsxParseZipTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("TestArchive.zip")) {
            assert is != null;
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("xlsx") && !entry.getName().contains("imeniny.xlsx")) {
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals("Александр", xls.excel.getSheetAt(0)
                            .getRow(1)
                            .getCell(3)
                            .getRichStringCellValue());

                }
            }
        }
    }


    @DisplayName("Чтение pdf файла")
    @Test
    void zipPdfTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("TestArchive.zip")) {
            assert is != null;
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("pdf") && !entry.getName().contains("Soglashenie.pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.text.contains(FILE_PDF));
                }
            }
        }
    }
}
