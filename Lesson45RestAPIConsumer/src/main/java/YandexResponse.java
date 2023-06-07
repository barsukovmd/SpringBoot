import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YandexResponse {
    private List<Translation> translations;
}
