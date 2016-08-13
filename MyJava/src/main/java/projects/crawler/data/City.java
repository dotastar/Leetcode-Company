package projects.crawler.data;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Created by yazhoucao on 10/25/15.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = { "name" })
public class City {
    private static final Set<String> MUNICIPALITIES = ImmutableSet.of("北京", "上海", "天津", "重庆");

    public static boolean isMunicipality(String cityName) {
        return MUNICIPALITIES.contains(cityName);
    }

    private String provinceName;
    private final String name;
    private final String url;
}
