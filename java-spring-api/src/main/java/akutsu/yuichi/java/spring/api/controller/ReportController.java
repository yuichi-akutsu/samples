/*
 * MIT License
 *
 * Copyright (c) 2021 yuichi.akutsu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package akutsu.yuichi.java.spring.api.controller;

import akutsu.yuichi.java.spring.api.exception.SystemException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * レポート(PDF)出力のサンプル
 */
@RestController
@RequestMapping("ticket")
public class ReportController {

    /**
     * QRサイズ
     */
    private static final int QR_SIZE = 600;

    /**
     * QRコード取得<br>
     * <p>
     * パラメータで指定された文字列をQRコードに埋め込みPNG画像で返却
     * </p>
     *
     * @return QRコード
     */
    @GetMapping(produces = "image/png")
    ResponseEntity<byte[]> getPngTicket(@RequestParam(value = "content", required = false) String content) {
        // QR画像をバッファに生成
        BufferedImage image = getImage(content == null ? "Content is not set." : content);

        // PNG画像のbyte配列に変換
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bos);
        } catch (IOException e) {
            throw new SystemException();
        }
        byte[] bImage = bos.toByteArray();

        // 画像を返却
        return new ResponseEntity<>(bImage, HttpStatus.OK);
    }

    /**
     * PDFレポート取得
     *
     * @return PDFレポート
     */
    @GetMapping(path = "pdf", produces = "application/pdf")
    ResponseEntity<byte[]> getPdfTicket(@RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "content", required = false) String content) {
        // Jasperのテンプレートを取得
        URL templateUrl = this.getClass().getResource("/report/ticket.jasper");
        if (templateUrl == null) {
            throw new SystemException();
        }
        String templatePath = templateUrl.getPath();

        // パラメータを設定
        HashMap<String, Object> params = new HashMap<>();
        params.put("title", title == null ? "Title is not set." : title);
        params.put("image", getImage(content == null ? "Content is not set." : content));

        // PDFを生成
        byte[] reportFile;
        try {
            reportFile = JasperRunManager.runReportToPdf(templatePath, params, new JREmptyDataSource());
        } catch (JRException e) {
            throw new SystemException();
        }
        return new ResponseEntity<>(reportFile, HttpStatus.OK);
    }

    /**
     * QRイメージ生成
     *
     * @param content QRコード内に埋め込むコンテンツ
     * @return QRイメージ
     */
    private BufferedImage getImage(String content) {
        // QRコード設定
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.QR_VERSION, 3);

        // QRコード生成
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = writer.encode(content, format, QR_SIZE, QR_SIZE, hints);
        } catch (WriterException e) {
            throw new SystemException();
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
