package general.webcrawler.yiyaodaibiao.model;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Created by yazhoucao on 10/25/15.
 */
@Data
@AllArgsConstructor
public class City {
    private static final Set<String> MUNICIPALITIES = ImmutableSet.of("北京", "上海", "天津", "重庆");

    public static boolean isMunicipality(String cityName) {
        return MUNICIPALITIES.contains(cityName);
    }

    private String provinceName;

    final private String name;

    final private String url;
}