package Jasperreports;

import com.jackrain.nea.exception.NDSException;
import com.jackrain.nea.sg.transfer.api.SgTransferPosPrintCmd;
import com.jackrain.nea.sys.domain.ValueHolderV14;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Descroption
 * @Author liqiang
 * @Date 2020/4/27 14:38
 */
@Slf4j
@Component
@Service(protocol = "dubbo", validation = "true", version = "1.0", group = "sg")
public class SgTransferPosPrintCmdImpl implements SgTransferPosPrintCmd {
    @Autowired
    private  SgTransferPosPrintService service;

    @Override
    public ValueHolderV14<String> transferPrint(Long[] ids) throws NDSException {
        return service.printPosTransfer(ids);
    }

    @Override
    public ValueHolderV14<String> transferByBoxPrint(Long[] ids) throws NDSException {
        return service.printPosByBoxTransfer(ids);
    }
}
