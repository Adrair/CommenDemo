package Jasperreports;

import com.jackrain.nea.exception.NDSException;
import com.jackrain.nea.sys.domain.ValueHolderV14;

/**
 * @Descroption
 * @Author liqiang
 * @Date 2020/4/27 14:32
 */
public interface SgTransferPosPrintCmd {
    /**
     * Pos出库单打印
     * @param ids
     * @return
     * @throws NDSException
     */
    ValueHolderV14<String> transferPrint(Long[] ids) throws NDSException;
    /**
     * Pos出库单按箱打印
     * @param ids
     * @return
     * @throws NDSException
     */
    ValueHolderV14<String> transferByBoxPrint(Long[] ids) throws NDSException;
}
