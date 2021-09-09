package Jasperreports;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Descroption
 * @Author liqiang
 * @Date 2020/4/27 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SgPosTransferPrintMainResult implements Serializable {
    private static final long serialVersionUID = -128L;

    /**
     * POS入库单名称
     */
    @JSONField(name = "BILLNAME")
    private String billName = "店铺调拨明细单";
    /**
     * 单号
     */
    @JSONField(name = "BILL_NO")
    private String billNo;

    /**
     * 发货店仓名称
     */
    @JSONField(name = "CP_C_ORIG_ENAME")
    private String cpCOrigEname;

    /**
     * 收货店仓名称
     */
    @JSONField(name = "CP_C_DEST_ENAME")
    private String cpCDestEname;

    /**
     * 出库日期
     */
    @JSONField(name = "OUT_DATE")
    private String outDate;

    /**
     * 矩阵列数(出库)
     */
    @JSONField(name = "COLUMNS")
    private Integer columns;

    /**
     * 矩阵列数(调拨)
     */
    @JSONField(name = "T_COLUMNS")
    private Integer tColumns;

    /**
     * 出库数量明细
     */
    @JSONField(name = "SC_B_TRANSFER_SUB_REPORT")
    private SgPosTransferPrintItemResult subReport;

    /**
     * 调拨数量明细
     */
    @JSONField(name = "T_SC_B_TRANSFER_SUB_REPORT")
    private SgPosTransferPrintItemResult tSubReport;

    /**
     * 备注
     */
    @JSONField(name = "REMARK")
    private String remark;

    /**
     * 箱数
     */
    @JSONField(name = "TRANSFER_ITEM_BOX_NUM")
    private Integer transferItemBoxNum;


    /**
     * 箱号
     */
    @JSONField(name = "TRANSFER_BOX_ID")
    private String transferBoxId;
}
