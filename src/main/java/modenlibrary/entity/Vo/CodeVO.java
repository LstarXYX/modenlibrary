package modenlibrary.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码传输类
 * @author L.star
 * @date 2021/3/6 11:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeVO {
    private String base64Str;
    private String uuid;
}
