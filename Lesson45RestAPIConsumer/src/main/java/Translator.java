import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {
    public static void main(String[] args) {
        System.out.println("Enter sentence in English to Translate into Russian");
        Scanner scanner = new Scanner(System.in);
        String sentenceToTranslate = scanner.nextLine();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer" + "y0_AgAAAAAOl-WpAATuwQAAAADk6keTlAMBDVhJQPGn3yJritk59Urcrlc");

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("folderId", "b1gih4a1n39suclc7qaf");
        jsonData.put("targetLanguageCode", "ru");
        jsonData.put("texts", "[" + sentenceToTranslate + "]");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData, httpHeaders);

        YandexResponse response = restTemplate.postForObject(url, request, YandexResponse.class);
//        System.out.println(response); теперь нужно распарсить строчку с json с помощью JACKSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(response);
        assert response != null;
        System.out.println("Перевод: " + response.getTranslations().get(0).getText());
    }
}
