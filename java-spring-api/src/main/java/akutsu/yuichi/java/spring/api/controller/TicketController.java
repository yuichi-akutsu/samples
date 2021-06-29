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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @GetMapping(produces = "image/png")
    ResponseEntity<byte[]> getPngTicket() throws WriterException, IOException {
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.QR_VERSION, 3);

        int size = 600;

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode("https://google.com", format, size, size, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedOutputStream os = new BufferedOutputStream(bos);

        image.flush();
        ImageIO.write(image, "png", os);
        byte[] bImage = bos.toByteArray();

        return new ResponseEntity<>(bImage, HttpStatus.OK);
    }

    @GetMapping(path = "pdf", produces = "application/pdf")
    ResponseEntity<byte[]> getPdfTicket() throws JRException {
        String jasperReportPath = this.getClass().getResource("/report/ticket.jasper").getPath();
        HashMap<String,  Object> params = new HashMap<>();
        params.put("title",  "test");

        byte[] reportFile = JasperRunManager.runReportToPdf(jasperReportPath, params);

        return new ResponseEntity<>(reportFile, HttpStatus.OK);
    }
}
