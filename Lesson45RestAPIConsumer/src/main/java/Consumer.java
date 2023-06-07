import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "TestName");
        jsonToSend.put("job", "TestJob");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

        String url = "https://reqres.in/api/users/";
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);
    }
}
