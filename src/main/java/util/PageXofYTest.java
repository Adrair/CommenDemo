package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;

/**
 * 页码事件
 */
public class PageXofYTest extends PdfPageEventHelper {

    public PdfTemplate toal;

    public BaseFont bfChinese;

    /**
     * 重写PdfPageEventHelper 中的onOpenDocument 方法
     */

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        super.onOpenDocument(writer, document);
    }
}
