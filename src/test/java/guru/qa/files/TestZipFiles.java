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
    private static final String FILE_XLSX = "Александр";
    private static final String[][] FILE_CSV = new String[][]{
                     {"Name","Gender","Age"},
                     {"Alex","Man","20"},
                     {"Julia","Female","21" }

    };

    private static final ClassLoader classLoader = TestZipFiles.class.getClassLoader();

    @Test
    @DisplayName("Проверка файлов PDF, XLSX, CSV в архиве")
    void csvParseZipTest() throws Exception {

      ClassLoader cl = this.getClass().getClassLoader();

        try (InputStream is = cl.getResourceAsStream("TestArchive.zip");
            ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("csv") && !entry.getName().contains("name.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> csv = csvReader.readAll();
                    Assertions.assertArrayEquals(FILE_CSV[1], csv.get(1));
               }
            }
          }
       }

    @Test
    @DisplayName("Проверка xlsx файла")
    void xlsxParseZipTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("TestArchive.zip")) {
            ZipInputStream zs = new ZipInputStream(is);
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().contains("xlsx") && !entry.getName().contains("imeniny.xlsx")) {
                        XLS xls = new XLS(zs);
                        Assertions.assertEquals(FILE_XLSX, xls.excel.getSheetAt(0)
                                .getRow(2)
                                .getCell(1)
                                .getRichStringCellValue());

                    }
                }
            }
        }
}

