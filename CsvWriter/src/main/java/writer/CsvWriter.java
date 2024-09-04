package writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvWriter {
    private final String filePath;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String[] headers;


    public CsvWriter(String filePath, String[] headers){
        this.filePath = filePath;
        this.headers = headers;
        Path path = Paths.get(filePath);
        if(path.toFile().exists()) return;
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers).build();

        try (FileWriter writer = new FileWriter(filePath, true)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToCsv(String data) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(data);
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers).setSkipHeaderRecord(true).build();

        try (FileWriter writer = new FileWriter(filePath, true)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

            String[] rows = new String[headers.length];
            for(int i = 0; i < headers.length; i++){
                rows[i] = jsonNode.path(headers[i]).asText();
                System.out.println(rows[i]);
            }

            csvPrinter.printRecord((Object[]) rows);

            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
