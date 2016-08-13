package projects.crawler.subproject.yiyaodaibiao.model;

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
@EqualsAndHashCode(of={"name"})
public class City {
    private static final Set<String> MUNICIPALITIES = ImmutableSet.of("北京", "上海", "天津", "重庆");

    public static boolean isMunicipality(String cityName) {
        return MUNICIPALITIES.contains(cityName);
    }

    private String provinceName;
    private final String name;
    private final String url;

    public String getAbbreviatedName() {
        /**
         * Samples:
         * http://np.58.com/yiyaodaibiao/
         * http://xa.58.com/yiyaodaibiao/
         * http://yc.58.com/yiyaodaibiao/
         * http://bz.58.com/yiyaodaibiao/
         * http://zhangbei.58.com/yiyaodaibiao/
         */
        int start = url.indexOf("http://") + "http://".length();
        int end = url.indexOf(".", start);
        return url.substring(start, end);
    }


}
