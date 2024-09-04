import com.fasterxml.jackson.core.JsonProcessingException;
import writer.CsvWriter;

public class CsvTest {
    public static void main(String[] args) {
        String jsonData = """
{
  "by" : "norvig",
  "id" : 2921983,
  "parent" : 2921506,
  "text" : "Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, I'll make a deal: I'll keep writing if you keep reading. K?",
  "time" : 1314211127
}
""";

        CsvWriter csvWriter = new CsvWriter("/home/cuma/Fakultet/veliki-podaci-ispit/KafkaServiceSuite/CommentConsumers/src/main/resources/comments.csv", new String[]{"by", "id", "parent", "text", "time"});
        try {
            csvWriter.writeToCsv(jsonData);
            csvWriter.writeToCsv(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
