package Jasperreports;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liqiang
 * @date 2020-04-27 16:14
 * @description POS出库单打印数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SgTransferPrintResult {

    /**
     * Pos出库单
     */
    @JSONField(name = "SC_B_TRANSFER")
    List<SgPosTransferPrintMainResult> transferPrint;

}
