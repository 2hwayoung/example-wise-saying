package standard;

import common.utils.FileUtil;
import common.utils.JsonUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiseSaying.WiseSaying;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {

    @BeforeAll
    static void beforeAll() {
        FileUtil.createDir("db/test");
    }

    @AfterAll
    static void afterAll() {
        FileUtil.deleteForce("db/test");
    }

    @Test
    @DisplayName("Map을 Json으로 변환1 - 속성이 1개")
    void t1() {

        Map<String, Object> map = Map.of("name", "홍길동");

        String jsonStr = JsonUtil.mapToJson(map);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "name" : "홍길동"
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("Map을 Json으로 변환2 - 속성이 2개")
    void t2() {

        // Map은 순서를 보장하지 않는다.
        // 순서 보장 -> LinkedHashMap
//        Map<String, Object> map = Map.of("name", "홍길동", "home", "서울");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "홍길동");
        map.put("home", "서울");

        String jsonStr = JsonUtil.mapToJson(map);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "name" : "홍길동",
                            "home" : "서울"
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("Map을 Json으로 변환3 - 속성이 3개, 문자와 숫자 혼합")
    void t3() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "홍길동");
        map.put("home", "서울");
        map.put("age", 20);

        String jsonStr = JsonUtil.mapToJson(map);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "name" : "홍길동",
                            "home" : "서울",
                            "age" : 20
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("WiseSaying을 Map으로 변환 -> Json으로 변환")
    void t4() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");
        Map<String, Object> wiseSayingMap = wiseSaying.toMap();

        String jsonStr = JsonUtil.mapToJson(wiseSayingMap);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "id" : 1,
                            "content" : "aaa",
                            "author" : "bbb"
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("Map을 넘기면 Json 파일로 저장하기")
    void t5() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");
        Map<String, Object> wiseSayingMap = wiseSaying.toMap();

        String filePath = "db/test/%d.json".formatted(wiseSaying.getId());
        JsonUtil.writeAsMap(filePath, wiseSayingMap);

        boolean rst = Files.exists(Path.of(filePath));
        assertThat(rst).isTrue();

        String content = FileUtil.readAsString(filePath);
        assertThat(content)
                .isEqualTo("""
                        {
                            "id" : 1,
                            "content" : "aaa",
                            "author" : "bbb"
                        }
                        """.stripIndent().trim()
                );
    }

    @Test
    @DisplayName("Json 문자열을 Map으로 변환하기")
    void t6() {

        String jsonStr = """
                {
                    "id" : 1,
                    "content" : "aaa",
                    "author" : "bbb"
                }
                """;

        Map<String, Object> map = JsonUtil.jsonToMap(jsonStr);

        assertThat(map)
                .hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("content", "aaa")
                .containsEntry("author", "bbb");

    }

    @Test
    @DisplayName("파일명을 넘기면 Map으로 읽어오기")
    void t7() {

        String filePath = "db/test/%d.json".formatted(1);

        Map<String, Object> map = JsonUtil.readAsMap(filePath);

        assertThat(map)
                .hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("content", "aaa")
                .containsEntry("author", "bbb");
    }

    @Test
    @DisplayName("Map을 WiseSaying으로 변환")
    void t8() {

        String filePath = "db/test/%d.json".formatted(1);
        Map<String, Object> map = JsonUtil.readAsMap(filePath);

        WiseSaying wiseSaying = WiseSaying.fromMap(map);

        assertThat(wiseSaying.getId()).isEqualTo(1);
        assertThat(wiseSaying.getContent()).isEqualTo("aaa");
        assertThat(wiseSaying.getAuthor()).isEqualTo("bbb");

    }

    @Test
    @DisplayName("wiseSaying list를 json 문자열로 변환")
    void t9() {

        WiseSaying wiseSaying1 = new WiseSaying(1, "aaa", "bbb");
        WiseSaying wiseSaying2 = new WiseSaying(2, "ccc", "ddd");

        List<WiseSaying> wiseSayings = List.of(wiseSaying1, wiseSaying2);

        List<Map<String, Object>> mapList = wiseSayings.stream()
                .map(WiseSaying::toMap)
                .toList();


        String jsonStr = JsonUtil.listToJson(mapList);

        assertThat(jsonStr)
                .isEqualTo("""
                        [
                            {
                                "id" : 1,
                                "content" : "aaa",
                                "author" : "bbb"
                            },
                            {
                                "id" : 2,
                                "content" : "ccc",
                                "author" : "ddd"
                            }
                        ]
                        """.stripIndent().trim());

    }
}
