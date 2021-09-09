package util;

import com.jackrain.nea.config.PropertiesConf;
import com.jackrain.nea.ext.oss.OssHelper;
import com.jackrain.nea.util.ApplicationContextHandle;
import org.springframework.beans.factory.annotation.Autowired;

public class SgReportUtils {

    @Autowired
    private static OssHelper ossHelper;

    public static OssHelper getOssHelper() {

        if (ossHelper == null) {

            PropertiesConf pconf = ApplicationContextHandle.getBean(PropertiesConf.class);
            String accessKeyId = pconf.getProperty("r3.oss.accessKey");
            String accessKeySecret = pconf.getProperty("r3.oss.secretKey");
            String bucketName = pconf.getProperty("r3.oss.bucketName");
            String endpoint = pconf.getProperty("r3.oss.endpoint");
            ossHelper = new OssHelper(accessKeyId, accessKeySecret, endpoint, bucketName);
        }

        return ossHelper;
    }
}
