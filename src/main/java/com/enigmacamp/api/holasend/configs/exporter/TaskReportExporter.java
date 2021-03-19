package com.enigmacamp.api.holasend.configs.exporter;

import com.enigmacamp.api.holasend.models.excel.TaskReportModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;

public class TaskReportExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<TaskReportModel> listTaskReportModel;

    public TaskReportExporter(List<TaskReportModel> listTaskReportModel) {
        this.listTaskReportModel = listTaskReportModel;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("TaskReport");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(CENTER);
        createCell(row, 0, "Task Report", style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
        font.setFontHeightInPoints((short) (10));

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Create Date", style);
        createCell(row, 1, "Destination", style);
        createCell(row, 2, "Address", style);
        createCell(row, 3, "Notes", style);
        createCell(row, 4, "Request By", style);
        createCell(row, 5, "Courier", style);
        createCell(row, 6, "Pick Up", style);
        createCell(row, 7, "Delivered", style);
        createCell(row, 8, "Return", style);
        createCell(row, 9, "Status", style);
        createCell(row, 10, "Priority", style);
    }

    private void writeDataLines() {
        int rowCount = 2;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (TaskReportModel taskReport : listTaskReportModel) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, taskReport.getCreateDate(), style);
            createCell(row, columnCount++, taskReport.getDestination(), style);
            createCell(row, columnCount++, taskReport.getAddress(), style);
            createCell(row, columnCount++, taskReport.getNotes(), style);
            createCell(row, columnCount++, taskReport.getRequestBy(), style);
            createCell(row, columnCount++, taskReport.getCourier(), style);
            createCell(row, columnCount++, taskReport.getPickUpTime(), style);
            createCell(row, columnCount++, taskReport.getDeliveredTime(), style);
            createCell(row, columnCount++, taskReport.getReturnTime(), style);
            createCell(row, columnCount++, taskReport.getStatus(), style);
            createCell(row, columnCount++, taskReport.getPriority(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
