package modenlibrary.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author L.star
 * @date 2021/1/8 20:20
 */
@Component
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String s) {
        try {
            if (StrUtil.isEmptyIfStr(s)){
                return null;
            }
            Integer num = Integer.parseInt(s.trim());
            return num;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
