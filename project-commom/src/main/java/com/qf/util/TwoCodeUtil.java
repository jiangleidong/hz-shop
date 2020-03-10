package com.qf.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * 利用 ZXing 的jar 包，产生二维码的response 字节流
 */
public class TwoCodeUtil {
    private static final int CODE_WIDTH = 200;
    private static final int CODE_HEIGHT = 200;

    public static void createTwoCode(String uri , HttpServletResponse response) throws URISyntaxException, IOException {
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(uri, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
            System.out.println("创建二维码完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
