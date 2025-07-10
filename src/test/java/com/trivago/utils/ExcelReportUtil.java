package com.trivago.utils;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class ExcelReportUtil {
    public static void writeTop10HotelsToExcel(List<String> names, List<String> locations, List<String> prices, List<Double> ratings) {
        List<Integer> top10 = IntStream.range(0, ratings.size())
                .boxed()
                .sorted((i, j) -> Double.compare(ratings.get(j), ratings.get(i)))
                //.limit(ratings.size())
                .limit(10)
                .toList(); 

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Top 10 Hotels");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Hotel Name");
            header.createCell(1).setCellValue("Location");
            header.createCell(2).setCellValue("Price");
            header.createCell(3).setCellValue("Rating");

            for (int i = 0; i < top10.size(); i++) {
                int idx = top10.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(names.get(idx));
                row.createCell(1).setCellValue(locations.get(idx));
                row.createCell(2).setCellValue(prices.get(idx));
                row.createCell(3).setCellValue(ratings.get(idx));
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            String fileName = "src\\test\\resource\\Trivago_Hotel_Report_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xlsx";
            try (FileOutputStream out = new FileOutputStream(fileName)) {
                workbook.write(out);
            }
        } catch (Exception e) {
            System.err.println("Failed to write Excel: " + e.getMessage());
        }
    }

//	public static void writeTop10HotelsToExcel(List<String> hotelNames, List<String> hotelLocations,
//			List<String> hotelPrices, List<Double> hotelRatings) {
//		// TODO Auto-generated method stub
//		
//	}
}

