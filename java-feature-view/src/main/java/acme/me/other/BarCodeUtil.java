package acme.me.other;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
public class BarCodeUtil {
    private static final Map<DecodeHintType, Object> decodeHints;
    private static final Map<EncodeHintType, Object> encodeHints;

    static{
        encodeHints = new HashMap<EncodeHintType, Object>();
        encodeHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        decodeHints = new HashMap<DecodeHintType, Object>();
        decodeHints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
    }

    public static void main(String[] args)throws Exception {
        String filePath = ".//zxing01.png";
        JSONObject json = new JSONObject();
        json.put("author", "shihy");
        json.put("zxing", "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");
        String content = json.toJSONString();

        BarCodeUtil codeUtil=new BarCodeUtil();
        //codeUtil.enCodeContent(content, filePath);

        String resutlContent = codeUtil.deCodeContent(filePath);
        if(content.equals(resutlContent)){
            System.out.println("Tool Pass!");
        }

        filePath = ".//zxing03.jpg";
        resutlContent = codeUtil.deCodeContent(filePath);
        if(content.equals(resutlContent)){
            System.out.println("Tool Pass!");
        }
    }

    /**
     * 生成图像
     *
     * @throws WriterException
     * @throws IOException
     */
    public void enCodeContent(String content, String filePath) throws WriterException, IOException {
        int width = 200, height = width;
        String format = "png";

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, encodeHints);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
    }

    /**
     * 解析图像
     */
    public String deCodeContent(String filePath) throws Exception {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
            byte[] data = ((DataBufferByte) image.getData().getDataBuffer()).getData();

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Reader multiFormatReader = new QRCodeReader();
            Result result = multiFormatReader.decode(binaryBitmap, decodeHints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
