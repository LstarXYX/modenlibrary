package modenlibrary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author L.star
 * @date 2020/12/24 15:25
 */
@Component
@Getter
@Setter
public class ImgProperties {

    @Value("${img.fileSize}")
    private long fileSize;

    @Value("${img.uploadPath}")
    private String uplodaPath;

    @Value("${img.scaleRatio}")
    private Double scaleRatio;

    @Value("${img.Type}")
    private String imgType;

    @Value("${img.baseURL}")
    private String baseURL;

    @Value("${img.dataBasePath}")
    private String dataBasePath;

    @Value("${img.realPath}")
    private String realPath;
}
