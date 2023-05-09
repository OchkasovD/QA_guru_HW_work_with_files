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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestZipFiles {
    private final ClassLoader cl = TestZipFiles.class.getClassLoader();
    private static final String[][] FILE_CSV = new String[][]{
            {"Name", "Gender", "Age"},
            {"Alex", "Man", "20"},
            {"Julia", "Female", "21"}
    };
    private static final String FILE_PDF = "СОГЛАШЕНИЕ";


    @Test
    void csvParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("TestArchive.zip")) {
            assert is != null;
            try (ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("Name.csv")) {
                        InputStreamReader isr = new InputStreamReader(zs);
                        CSVReader csvReader = new CSVReader(isr);
                        List<String[]> content = csvReader.readAll();
                        Assertions.assertArrayEquals(FILE_CSV[0], content.get(0));

                    }
                }
            }
        }
    }


    @DisplayName("Чтение pdf файла")
    @Test
    void pdfParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("TestArchive.zip")) {
            assert is != null;
            try (ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("Soglashenie.pdf")) {
                        PDF pdf = new PDF(zs);
                        assertTrue(pdf.text.contains(FILE_PDF));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка xls файла")
    void xlsParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("TestArchive.zip")) {
            assert is != null;
            try (ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().equals("imeniny.xls")) {
                        XLS xls = new XLS(zs);
                        assertTrue(xls.excel.getSheetAt(0).getRow(6).
                                getCell(0).getStringCellValue().startsWith("7 мая"));
                    }
                }
            }
        }
    }
}



