package com.nineya.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nineya.springboot.common.R;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nineya.springboot.entity.Configuration;
import com.nineya.springboot.entity.History;
import com.nineya.springboot.entity.Result;
import com.nineya.springboot.service.ConfigurationService;
import com.nineya.springboot.service.HistoryService;
import com.nineya.springboot.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */
@RestController
@CrossOrigin
@ResponseBody
public class ReportController {
    @Autowired
    ConfigurationService configurationService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ResultService resultService;
    @RequestMapping(value = "/report/getbyid",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getReport(HttpServletRequest req, HttpSession session) throws IOException {

        String Id = req.getParameter("id");
        Configuration configuration = (Configuration) configurationService.getById(Id).getData();
        History history = historyService.getById(Id);
        Result resultEntity = resultService.getById(Id);
        JSONObject result = new JSONObject();
        JSONArray array=new JSONArray(13);
        JSONObject data = new JSONObject();

        JSONObject item1 = new JSONObject();
        JSONObject item2= new JSONObject();
        JSONObject item3 = new JSONObject();
        JSONObject item4 = new JSONObject();
        JSONObject item5 = new JSONObject();
        JSONObject item6 = new JSONObject();
        JSONObject item7 = new JSONObject();
        JSONObject item8 = new JSONObject();
        JSONObject item9 = new JSONObject();
        JSONObject item10 = new JSONObject();
        JSONObject item11 = new JSONObject();
        JSONObject item12 = new JSONObject();
        JSONObject item13 = new JSONObject();

        item1.put("segment","任务开始时间");
        item1.put("value", Id.substring(6,Id.length()));

        item2.put("segment","任务执行时间");
        item2.put("value",history.getTime());

        item3.put("segment","融合算法");
        item3.put("value",configuration.getAlgorithm());

        item4.put("segment","交叉算子");
        item4.put("value",configuration.getCrossover());

        item5.put("segment","变异算子");
        item5.put("value",configuration.getMutation());

        item6.put("segment","地图");
        item6.put("value",configuration.getMapselect());

        item7.put("segment","Nsga-II子代数");
        item7.put("value",configuration.getGenerator());

        item8.put("segment","每代用例数量");
        item8.put("value",configuration.getNumber());

        item9.put("segment","执行用例数量");
        item9.put("value",resultEntity.getCounter());

        item10.put("segment","碰撞数量");
        item10.put("value",resultEntity.getCrashnum());

        item11.put("segment","融合错误数量");
        item11.put("value",resultEntity.getFusionerror());

        item12.put("segment","测试图片路径");
        item12.put("value","~/Openpilot/Loggerd/Senesor/camerad/picturelist"+Id.substring(6,Id.length()));

        item13.put("segment","传感器数据路径");
        item13.put("value","~/Openpilot/Loggerd/Senesor/sitedatalist/task"+Id.substring(6,Id.length()));

        array.add(0,item1);
        array.add(1,item2);
        array.add(2,item3);
        array.add(3,item4);
        array.add(4,item5);
        array.add(5,item6);
        array.add(6,item7);
        array.add(7,item8);
        array.add(8,item9);
        array.add(9,item10);
        array.add(10,item11);
        array.add(11,item12);
        array.add(12,item13);

        data.put("rows1",array);
        result.put("status",0);
        result.put("msg", "");
        result.put("data", data);
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/report/download",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String download(HttpServletRequest req, HttpSession session , HttpServletResponse response) throws IOException{

        String Id = req.getParameter("id");
        System.out.println(Id);

        String reportdir = "/home/xixi/amis-admin-master/pages/resource/report/"+Id;
        File reportfiles = new File(reportdir);

        if(!reportfiles.exists()) {
            boolean hasSucceeded = reportfiles.mkdir();
        }else {
            System.out.println("报告存在");
            JSONObject testresult = new JSONObject();
            testresult.put("status", 0);
            testresult.put("msg", "");
            JSONObject reporter = new JSONObject();
            reporter.put("source", "http://127.0.0.1:3000/pages/resource/report/" + Id + ".zip");
            testresult.put("data", reporter);
            return testresult.toJSONString();
        }

        Configuration configuration = (Configuration) configurationService.getById(Id).getData();
        History history = historyService.getById(Id);
        Result resultEntity = resultService.getById(Id);

        String[] headers = new String[]{ "报告字段", "数值"};
        String[][] rows = new String[13][2];
        rows[0][0] = "任务开始时间";
        rows[0][1] = Id.substring(6,Id.length());
        rows[1][0] = "任务执行时间";
        rows[1][1] = history.getTime();
        rows[2][0] = "融合算法";
        rows[2][1] = configuration.getAlgorithm();
        rows[3][0] = "交叉算子";
        rows[3][1] = configuration.getCrossover();
        rows[4][0] = "变异算子";
        rows[4][1] = configuration.getMutation();
        rows[5][0] = "地图";
        rows[5][1] = configuration.getMapselect();
        rows[6][0] = "Nsga-II子代数";
        rows[6][1] = configuration.getGenerator();
        rows[7][0] = "每代用例数量";
        rows[7][1] = configuration.getNumber();
        rows[8][0] = "执行用例数量";
        rows[8][1] = resultEntity.getCounter();
        rows[9][0] = "碰撞数量";
        rows[9][1] = resultEntity.getCrashnum();
        rows[10][0] = "融合错误数量";
        rows[10][1] = resultEntity.getFusionerror();
        rows[11][0] = "测试图片路径";
        rows[11][1] = "~/Openpilot/Loggerd/Senesor/camerad/picturelist"+Id.substring(6,Id.length());
        rows[12][0] = "传感器数据路径";
        rows[12][1] = "~/Openpilot/Loggerd/Senesor/sitedatalist/task"+Id.substring(6,Id.length());

        Document document = new Document(PageSize.LETTER.rotate());
        try {
            // 获取PdfWriter的实例并创建Table.pdf文件
            // 作为输出。
            PdfWriter.getInstance(document,
                    new FileOutputStream(new File("/home/xixi/amis-admin-master/pages/resource/report/"+Id+"/测试报告.pdf")));
            document.open();
            //创建一个PdfPTable实例。之后我们转型
            //将标头和行数组转换为PdfPCell对象。什么时候
            // 每个表格行都是完整的，我们必须调用
            // table.completeRow()方法。
            //
            // 为了更好地呈现，我们还设置了单元字体名称，
            //大小和重量。我们还定义了背景填充
            // 为细胞。

            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fontRow = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
            PdfPTable table = new PdfPTable(headers.length);
            for (String header : headers) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(0.9f);
                cell.setPhrase(new Phrase(header.toUpperCase(), setChineseFont()));
                table.addCell(cell);
            }
            table.completeRow();
            for (String[] row : rows) {
                for (String data : row) {
                    Phrase phrase = new Phrase(data, setChineseFont());
                    table.addCell(new PdfPCell(phrase));
                }
                table.completeRow();
            }

            document.addTitle("Fusion Report PDF");
            Paragraph par3=new Paragraph("预测结果展示",setChineseFont());
            //设置局中对齐
            par3.setAlignment(Element.ALIGN_CENTER);
            document.add(par3);
            document.add(new Paragraph("         ", setChineseFont()));
            document.add(table);


        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }


        JSONObject testresult = new JSONObject();
        testresult.put("status",0);
        testresult.put("msg","");
        JSONObject chartre = new JSONObject();
        List<File> fileList = new ArrayList<>();
        fileList.add(reportfiles);

        String zipDir ="/home/xixi/amis-admin-master/pages/resource/report/"+Id+".zip";
        zipFile(fileList,zipDir);
        chartre.put("source","http://127.0.0.1:3000/pages/resource/report/"+Id+".zip");
        testresult.put("data",chartre);
        System.out.println(testresult.toJSONString());
        return testresult.toJSONString();

    }

    private static Font setChineseFont() {
        BaseFont bf = null;
        Font fontChinese = null;
        try {
            // STSong-Light : Adobe的字体
            // UniGB-UCS2-H : pdf 字体
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChinese = new Font(bf, 12, Font.NORMAL);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }


    private static void zipFile(List<File> fileList , String dir) throws IOException {
        for (int i =0;i<fileList.size();i++){
            System.out.println(fileList.get(i).getName());
        }
        // 文件的压缩包路径
        String zipPath = dir;
        // 获取文件压缩包输出流
        try (OutputStream outputStream = new FileOutputStream(zipPath);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream,new Adler32());
             ZipOutputStream zipOut = new ZipOutputStream(checkedOutputStream)){
            for (File file : fileList) {
                // 获取文件输入流
                InputStream fileIn = new FileInputStream(file);
                // 使用 common.io中的IOUtils获取文件字节数组
                byte[] bytes = getFileByteArray(file);
                // 写入数据并刷新
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                zipOut.write(bytes,0,bytes.length);
                zipOut.flush();
                fileIn.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getFileByteArray(File file) {
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        byte[] buffer = null;
        try (FileInputStream fi = new FileInputStream(file)) {
            buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}


