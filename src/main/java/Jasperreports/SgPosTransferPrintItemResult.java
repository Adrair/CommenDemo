package Jasperreports;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Descroption
 * @Author liqiang
 * @Date 2020/4/27 16:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SgPosTransferPrintItemResult implements Serializable {
    private static final long serialVersionUID = -94608806L;

    /**
     * 制单人
     */
    @JSONField(name = "OWNERENAME")
    private String ownerename;

    /**
     * 创建时间
     */
    @JSONField(name = "CREATIONDATE")
    private String creationdate;

    /**
     * 审核人
     */
    @JSONField(name = "CHECK_ENAME")
    private String checkEname;

    /**
     * 审核时间
     */
    @JSONField(name = "CHECK_TIME")
    private String checkTime;

    /**
     * 备注
     */
    @JSONField(name = "REMARK")
    private String remark;


    /**
     * 号型组信息
     */
    @JSONField(name = "SC_B_TRANSFER_HEAD")
    private List<JSONObject> transferHead;

    /**
     * 商品信息
     */
    @JSONField(name = "SC_B_TRANSFER_ITEM")
    private List<JSONObject> transferItem;

    /**
     * 80mm热敏打印
     * 合计数量
     */
    @JSONField(name = "TOT_QTY")
    private BigDecimal totQty;

    /**
     * 80mm热敏打印
     * 总吊牌金额
     */
    @JSONField(name = "TOT_AMT_LIST")
    private BigDecimal totAmtList;

    /**
     * 商品信息(80mm热敏打印)
     */
    @JSONField(name = "SC_B_TRANSFER_ITEM_THERMAL")
    private List<JSONObject> transferItemsThermal;

}
