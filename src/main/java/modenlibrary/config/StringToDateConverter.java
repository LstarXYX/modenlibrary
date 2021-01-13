package modenlibrary.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转化器
 *      实现Converter接口
 * @Component 注解该类会作为组件类
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {
    /**
     * 将String字符串转换成Date日期格式
     * @param source 字符串日期格式
     * @return Date日期格式
     */
    @Override
    public Date convert(String source) {
        /*创建Date日期对象*/
        Date date = null;

        try {
            /*判断传入的字符串日期格式是否为空*/
            if (StringUtils.isEmpty(source)) {
                /*返回为空*/
                return null;
            }

            /*转换日期格式*/
            date = new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*返回日期对象*/
        return date;
    }
}
