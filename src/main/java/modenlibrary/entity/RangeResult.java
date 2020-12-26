package modenlibrary.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author L.star
 * @date 2020/12/26 12:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RangeResult {
    String cdate;
    Integer num;
}
