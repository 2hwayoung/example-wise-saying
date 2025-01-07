package wiseSaying;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public boolean isNew() {
        return this.id == 0;
    }

    @Override
    public String toString() {
        return "%d / %s / %s".formatted(id, author, content);
    }

    public String toJson() {
        return "{\n\t\"id\": " + id + ",\n\t\"content\": \"" + content + "\",\n\t\"author\": \"" + author + "\"\n}";
    }

    public static WiseSaying fromJson(String json) {
        String[] items = json
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .replace("\n", "")
                .replace("\t", "")
                .split(",");
        int id = Integer.parseInt(items[0].split(":")[1].trim());
        String content = items[1].split(":")[1].trim();
        String author = items[2].split(":")[1].trim();
        return new WiseSaying(id, content, author);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("content", content);
        map.put("author", author);

        return map;
    }

    public static WiseSaying fromMap(Map<String, Object> map) {
        int id = (int)map.get("id");
        String content = (String) map.get("content");
        String author = (String) map.get("author");

        return new WiseSaying(id, content, author);
    }
}
