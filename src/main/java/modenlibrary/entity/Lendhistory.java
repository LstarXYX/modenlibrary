package modenlibrary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (Lendhistory)实体类
 *
 * @author makejava
 * @since 2020-12-25 20:52:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lendhistory implements Serializable {
    private static final long serialVersionUID = 700317095169737715L;
    /**
    * 记录日期
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date cdate;
    /**
    * 借阅人数
    */
    private Integer people;

}