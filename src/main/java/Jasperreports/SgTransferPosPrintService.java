package Jasperreports;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.jackrain.nea.common.ReferenceUtil;
import com.jackrain.nea.constants.ResultCode;
import com.jackrain.nea.ps.api.SkuAndSizeInfoQueryCmd;
import com.jackrain.nea.ps.api.result.PsCShapeResult;
import com.jackrain.nea.sg.transfer.enums.SgTransferBillStatusEnum;
import com.jackrain.nea.sg.transfer.mapper.ScBTransferBoxItemMapper;
import com.jackrain.nea.sg.transfer.mapper.ScBTransferItemMapper;
import com.jackrain.nea.sg.transfer.mapper.ScBTransferMapper;
import com.jackrain.nea.sg.transfer.model.result.SgPosTransferPrintItemResult;
import com.jackrain.nea.sg.transfer.model.result.SgPosTransferPrintMainResult;
import com.jackrain.nea.sg.transfer.model.result.SgTransferPrintResult;
import com.jackrain.nea.sg.transfer.model.table.ScBTransfer;
import com.jackrain.nea.sg.transfer.model.table.ScBTransferBoxItem;
import com.jackrain.nea.sg.transfer.model.table.ScBTransferItem;
import com.jackrain.nea.sys.domain.ValueHolderV14;
import com.jackrain.nea.util.ApplicationContextHandle;
import com.jackrain.nea.utility.ExceptionUtil;
import com.jackrain.nea.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Descroption
 * @author liqiang
 * @date 2020/4/27 14:43
 */
@Slf4j
@Component
public class SgTransferPosPrintService {
    @Autowired
    private ScBTransferMapper scTransferMapper;
    @Autowired
    private ScBTransferItemMapper scTransferItemMapper;
    @Autowired
    private ScBTransferBoxItemMapper scBTransferBoxItemMapper;

    private String simplePattern = "yyyy/MM/dd";

    private String fullPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * POS出库单打印
     *
     * @param ids id集合
     * @return ValueHolderV14
     */
    public ValueHolderV14<String> printPosTransfer(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return new ValueHolderV14<>(ResultCode.FAIL, "请选择数据！");
        }
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + ",POS出库单打印入参:" + JSON.toJSONString(ids));
        }

        ValueHolderV14<String> holderV14 = new ValueHolderV14<>(ResultCode.FAIL, "打印失败！");
        try {
            // 创建pos出库单打印数据
            List<SgPosTransferPrintMainResult> list = createPrintData(ids);
            // 打印返回数据
            SgTransferPrintResult result = new SgTransferPrintResult(list);

            String data = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

            holderV14 = new ValueHolderV14<>(data, ResultCode.SUCCESS, "success");

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(this.getClass().getName() + ",POS出库单打印异常:" + ExceptionUtil.getMessage(e));
            }
            AssertUtils.logAndThrowExtra("POS出库单打印异常！", e);
        }

        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + ",POS出库单打印出参:" + JSON.toJSONString(holderV14));
        }

        return holderV14;

    }

    public ValueHolderV14<String> printPosByBoxTransfer(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return new ValueHolderV14<>(ResultCode.FAIL, "请选择数据！");
        }

        ValueHolderV14<String> holderV14 = new ValueHolderV14<>(ResultCode.FAIL, "打印失败！");
        try {
            // 创建pos出库单打印数据
            List<SgPosTransferPrintMainResult> list = createByBoxPrintData(ids);
            // 打印返回数据
            SgTransferPrintResult result = new SgTransferPrintResult(list);

            String data = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

            holderV14 = new ValueHolderV14<>(data, ResultCode.SUCCESS, "success");

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(this.getClass().getName() + ",POS出库单按箱打印异常:" + ExceptionUtil.getMessage(e));
            }
            AssertUtils.logAndThrowExtra("POS出库单按箱打印异常！", e);
        }

        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + ",POS出库单按箱打印出参:" + JSON.toJSONString(holderV14));
        }

        return holderV14;

    }

    /**
     * @param ids POS出库单id
     * @return 打印数据集
     * 创建打印数据
     */
    private List<SgPosTransferPrintMainResult> createPrintData(Long[] ids) {
        List<SgPosTransferPrintMainResult> results = Lists.newArrayList();
        for (Long id : ids) {
            ScBTransfer scTransfer = scTransferMapper.selectById(id);
            // 创建查询条件构造器
            LambdaQueryWrapper<ScBTransferItem> itemLamda = new QueryWrapper<ScBTransferItem>().lambda();

            AssertUtils.cannot(scTransfer == null, "[" + id + "]该POS出库单已不存在！");

            AssertUtils.cannot(scTransfer.getStatus() == SgTransferBillStatusEnum.VOIDED.getVal(),
                    "[" + scTransfer.getBillNo() + "]单据已作废，不满足打印条件！");
            List<ScBTransferItem> scTransferItems =
                    scTransferItemMapper.selectList(itemLamda.eq(ScBTransferItem::getScBTransferId, id));

            AssertUtils.cannot(CollectionUtils.isEmpty(scTransferItems), "[" + scTransfer.getBillNo() +
                    "]无明细信息，不满足打印条件！");
            // 构建打印数据
            SgPosTransferPrintMainResult result = buildResult(scTransfer, scTransferItems);

            results.add(result);
        }
        return results;
    }

    /**
     * @param ids POS出库单id
     * @return 打印按箱数据集
     * 创建打印数据
     */
    private List<SgPosTransferPrintMainResult> createByBoxPrintData(Long[] ids) {
        List<SgPosTransferPrintMainResult> results = Lists.newArrayList();
        for (Long id : ids) {
            ScBTransfer scTransfer = scTransferMapper.selectById(id);
            // 创建查询条件构造器
            QueryWrapper itemLamda = new QueryWrapper();
            itemLamda.eq("sc_b_transfer_id", id);
            AssertUtils.cannot(scTransfer == null, "[" + id + "]该POS出库单已不存在！");

            AssertUtils.cannot(scTransfer.getStatus() == SgTransferBillStatusEnum.VOIDED.getVal(),
                    "[" + scTransfer.getBillNo() + "]单据已作废，不满足打印条件！");
            List<ScBTransferBoxItem> scTransferItems = scBTransferBoxItemMapper.selectList(itemLamda);
            List<ScBTransferItem> transferItems = scTransferItemMapper.selectList(itemLamda);

            AssertUtils.cannot(CollectionUtils.isEmpty(scTransferItems), "[" + scTransfer.getBillNo() +
                    "]无明细信息，不满足打印条件！");
            //根据箱号分组
            Map<Long, List<ScBTransferBoxItem>> groupByTeusItem = scTransferItems.stream().collect(Collectors.groupingBy(
                    ScBTransferBoxItem::getTransferBoxId));

            Iterator it = groupByTeusItem.keySet().iterator();
            while (it.hasNext()) {
                List<ScBTransferItem> scBTransferItemJson = new ArrayList<>();
                Long key = (Long) it.next();
                List<ScBTransferBoxItem> teusItemsTmp = groupByTeusItem.get(key);
                //根据箱明细信息获取得到对应的出库单明细数据
                for (ScBTransferBoxItem scBTransferBoxItem : teusItemsTmp) {
                    List<ScBTransferItem> scBTransferItemTmp = transferItems.stream().filter(bk -> scBTransferBoxItem.getPsCSkuEcode().equals(bk.getPsCSkuEcode())).collect(Collectors.toList());
                    scBTransferItemTmp.forEach(trans->{
                        trans.setQty(scBTransferBoxItem.getQty());
                    });
                    AssertUtils.cannot(CollectionUtils.isEmpty(scBTransferItemTmp), "[" + scTransfer.getBillNo() + "]查无明细信息，不满足打印条件！");
                    scBTransferItemJson.add(scBTransferItemTmp.get(0));
                }
                scTransfer.setTotQty(scBTransferItemJson.stream() .map(ScBTransferItem::getQty).reduce(BigDecimal.ZERO,BigDecimal::add));
                // 构建打印数据
                SgPosTransferPrintMainResult result = buildResult(scTransfer, scBTransferItemJson);
                //设置箱号
                result.setTransferBoxId(key.toString());
                //箱数
                List<Long> transferItemBoxIdList = scTransferItems.stream().map(ScBTransferBoxItem::getTransferBoxId).collect(Collectors.toList());
                JSONArray pickOutTeusIdAry = new JSONArray();
                Set set = new HashSet(transferItemBoxIdList);
                pickOutTeusIdAry.addAll(set);
                result.setTransferItemBoxNum(pickOutTeusIdAry.size());
                String remark = StringUtils.isEmpty(result.getRemark()) ? "本单箱数为:" + pickOutTeusIdAry.size() : result.getRemark() + ",本单箱数为:" + pickOutTeusIdAry.size();
                result.setRemark(remark);
                results.add(result);
            }

        }
        return results;
    }

    /**
     * 组装返回JSON
     *
     * @param main 主表
     * @param items 明细表
     * @return 组装的数据
     */
    private SgPosTransferPrintMainResult buildResult(ScBTransfer main, List<ScBTransferItem> items) {
        SgPosTransferPrintMainResult result = new SgPosTransferPrintMainResult();
        result.setBillNo(main.getBillNo());
        result.setCpCOrigEname(main.getCpCOrigEname());
        result.setCpCDestEname(main.getCpCDestEname());
        result.setOutDate((main.getOutTime() != null) ? format(main.getOutTime(), fullPattern) : "");
        result.setRemark((main.getRemark() != null) ? main.getRemark() : "");

        //出库数量和调拨数量
        Map<String, BigDecimal> qtyOutMap = new HashMap<>(16);
        Map<String, BigDecimal> qtyMap = new HashMap<>(16);

        for (ScBTransferItem item : items) {
            Long psSizeId = item.getPsCSizeId();
            String psSkuEcode = item.getPsCSkuEcode();
            if (psSizeId != null && StringUtils.isNotEmpty(psSkuEcode)) {
                qtyOutMap.put(psSizeId + "_" + psSkuEcode, item.getQtyOut());
                qtyMap.put(psSizeId + "_" + psSkuEcode, item.getQty());
            }
        }
        //出库打印信息
        Map<Integer, SgPosTransferPrintItemResult> outMap = buildTransferOrOutResult(0, main,
                qtyOutMap, items);
        ArrayList<Integer> colomnList = new ArrayList<>(outMap.keySet());
        result.setColumns(colomnList.get(0));
        result.setSubReport(outMap.get(colomnList.get(0)));
        //调拨打印信息
        Map<Integer, SgPosTransferPrintItemResult> tranferMap = buildTransferOrOutResult(1, main,
                qtyMap, items);
        ArrayList<Integer> tColomnList = new ArrayList<>(tranferMap.keySet());
        result.setTColumns(tColomnList.get(0));
        result.setTSubReport(tranferMap.get(tColomnList.get(0)));

        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + "POS出库单打印返回数据printPosTransfer:" + JSONObject.toJSONString(result));
        }

        return result;
    }

    private Map<Integer, SgPosTransferPrintItemResult> buildTransferOrOutResult(int flag, ScBTransfer main,
                                                                                Map<String, BigDecimal> qtyMap,
                                                                                List<ScBTransferItem> items) {
        Map<Integer, SgPosTransferPrintItemResult> transferOrOutMap = new HashMap<>();
        SgPosTransferPrintItemResult sgPosTransferPrintItemResult = new SgPosTransferPrintItemResult();
        // 组装号型组对象
        List<JSONObject> shapeJoList = Lists.newArrayList();
        // 组装数据对象
        List<JSONObject> stransferJoList = Lists.newArrayList();
        /*收集热敏打印信息*/
        List<JSONObject> thermalList = Lists.newArrayList();
        Map<Long, String> sizeCountMap = new HashMap<>(16);
        Map<String, String> sizeHashMap = new HashMap<>(16);

        Map<String, String> shapeGroupEnameMap = new HashMap<>(16);
        int colomn = 0;
        SkuAndSizeInfoQueryCmd skuAndSizeInfoQueryCmd =
                (SkuAndSizeInfoQueryCmd) ReferenceUtil.refer(ApplicationContextHandle.getApplicationContext(),
                        SkuAndSizeInfoQueryCmd.class.getName(), "ps", "1.0");
        //尺寸id集合查出号型信息
        List<Long> sizeIdList = items.stream().map(ScBTransferItem::getPsCSizeId).collect(Collectors.toList());
        List<String> proEcodeList = items.stream().map(ScBTransferItem::getPsCProEcode).collect(Collectors.toList());
        /*去重*/
        List proEcodes = new ArrayList<>(new TreeSet<>(proEcodeList));
        List sizeIds = new ArrayList<>(new TreeSet<>(sizeIdList));
        List<String> skuEcodeList = items.stream().map(ScBTransferItem::getPsCSkuEcode).collect(Collectors.toList());

        List<PsCShapeResult> psCShapeResults = skuAndSizeInfoQueryCmd.queryShapeListByIds(sizeIds, proEcodes,skuEcodeList);
        AssertUtils.cannot(CollectionUtils.isEmpty(psCShapeResults), "号型组明细为空！");

        psCShapeResults.forEach(info -> shapeGroupEnameMap.put(info.getProEcode(), info.getShapeGroupEname()));
        //号型组分组
        Map<Long, List<PsCShapeResult>> shapeMap =
                psCShapeResults.stream().collect(Collectors.groupingBy(PsCShapeResult::getPsCShapeGroupId));

        for (List<PsCShapeResult> shapeResults : shapeMap.values()) {
            JSONObject shapeRowJo = new JSONObject();
            shapeRowJo.put("GROUP_HEAD", shapeResults.get(0).getShapeGroupEname());
            if (CollectionUtils.isNotEmpty(shapeResults)) {
                int count = 0;
                for (PsCShapeResult psCShapeResult : shapeResults) {
                    BigDecimal sizeQty = qtyMap.get(psCShapeResult.getPsCSizeId() + "_" + psCShapeResult.getSkuEcode());

                    if ((sizeQty == null) || (sizeQty.compareTo(BigDecimal.ZERO) == 0)) {
                        continue;
                    }
                    if (sizeCountMap.containsKey(psCShapeResult.getPsCSizeId())) {

                        shapeRowJo.put("SHARP_HEAD" + count, psCShapeResult.getSizeEname());
                        sizeHashMap.put(psCShapeResult.getPsCSizeId() + "_" + psCShapeResult.getSkuEcode(),
                                "SHARP_HEAD" + count);

                    } else {
                        count++;
                        sizeCountMap.put(psCShapeResult.getPsCSizeId(), "SHARP_HEAD" + count);
                        shapeRowJo.put("SHARP_HEAD" + count, psCShapeResult.getSizeEname());
                        sizeHashMap.put(psCShapeResult.getPsCSizeId() + "_" + psCShapeResult.getSkuEcode(),
                                "SHARP_HEAD" + count);
                        if (count > colomn) {
                            colomn = count;
                        }
                    }
                }
                shapeJoList.add(shapeRowJo);
            }
        }
        sgPosTransferPrintItemResult.setTransferHead(shapeJoList);

        //根据商品编码和颜色编码分组 不同size的明细集合
        Map<String, List<ScBTransferItem>> stranferMap =
                items.stream().collect(Collectors.groupingBy(ScBTransferItem::getPsCProEcode));
        int no = 1;
        for (List<ScBTransferItem> itemList : stranferMap.values()) {
            Map<String, List<ScBTransferItem>> itemMap =
                    itemList.stream().collect(Collectors.groupingBy(e -> e.getPsCProEcode() + e.getPsCClrEname()));
            Set<String> strings = itemMap.keySet();
            for (String proCclrEcode : strings) {
                List<ScBTransferItem> scTransferItems = itemMap.get(proCclrEcode);
                /*收集热敏打印信息*/
                List<JSONObject> buildThermalList = buildThermalList(scTransferItems);
                thermalList.addAll(buildThermalList);
                JSONObject transferJo = new JSONObject();
                ScBTransferItem bTransferItem = scTransferItems.get(0);
                BigDecimal totQty = BigDecimal.ZERO;
                transferJo.put("NO", no);
                transferJo.put("PS_C_PRO_ECODE", bTransferItem.getPsCProEcode());
                transferJo.put("PS_C_PRO_ENAME", bTransferItem.getPsCProEname());
                transferJo.put("PS_C_CLR_ENAME", bTransferItem.getPsCClrEname());
                transferJo.put("PRICE_LIST", bTransferItem.getPriceList());
                transferJo.put("GROUP_NAME", shapeGroupEnameMap.get(bTransferItem.getPsCProEcode()));
                for (ScBTransferItem item : scTransferItems) {
                    String subNum = sizeHashMap.get(item.getPsCSizeId() + "_" + item.getPsCSkuEcode());
                    if (subNum == null) {
                        continue;
                    }
                    String[] split = subNum.split("D");
                    String num = split[1];
                    if (flag == 0) {
                        transferJo.put("SHARP_QTY" + num, item.getQtyOut());
                        totQty = totQty.add(item.getQty());
                    } else {
                        transferJo.put("SHARP_QTY" + num, item.getQty());
                        totQty = totQty.add(item.getQty());
                    }
                }
                /*6.19新增列，总吊牌金额*/
                BigDecimal totPriceList = totQty.multiply(bTransferItem.getPriceList());
                transferJo.put("TOT_PRICELIST", totPriceList);
                no++;
                stransferJoList.add(transferJo);
            }
        }
        sgPosTransferPrintItemResult.setOwnerename(main.getOwnerename()).setCreationdate(format(main.getCreationdate(),
                fullPattern)).setCheckEname(main.getStatusEname()).setCheckTime(format(main.getStatusTime(),
                fullPattern)).setTransferItem(stransferJoList).setTotQty(main.getTotQty()).setTotAmtList(main.getTotAmtList()).setTransferItemsThermal(thermalList);

        transferOrOutMap.put(colomn, sgPosTransferPrintItemResult);
        return transferOrOutMap;
    }

    /**
     * @param date    日期
     * @param pattern 样式
     * @return 格式化字符串
     *  格式化日期
     */
    public String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (date != null) {
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 收集热敏打印的商品信息
     *
     * @param scBTransferItems 明细集合
     * @return JSON
     */
    private List<JSONObject> buildThermalList(List<ScBTransferItem> scBTransferItems) {
        /* 组装数据对象*/
        List<JSONObject> thermalList = Lists.newArrayList();
        for (ScBTransferItem item : scBTransferItems) {
            JSONObject thermalJo = new JSONObject();
            /*POS出库单热敏打印 80mm*/
            thermalJo.put("PS_C_PRO_ECODE", item.getPsCProEcode());
            thermalJo.put("PS_C_PRO_ENAME", item.getPsCProEname());
            thermalJo.put("PS_C_CLR_ENAME", item.getPsCClrEname());

            thermalJo.put("PRICE_LIST", item.getPriceList());
            thermalJo.put("SIZE_ENAME", item.getPsCSizeEname());
            thermalJo.put("PRO_DESCRIPTION", item.getPsCProEcode() + item.getPsCProEname()
                    + "(" + item.getPsCClrEname() + ")");
            thermalJo.put("QTY", item.getQty());
            BigDecimal qty = item.getQty() != null ? item.getQty() : BigDecimal.ZERO;
            thermalJo.put("TOT_PRICELIST", qty.multiply(item.getPriceList()));

            thermalList.add(thermalJo);
        }
        return thermalList;
    }

}
