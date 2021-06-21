package com.example.ooo.backend.service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.ooo.backend.model.Todo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TodoExcelExporterService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Todo> todoList;

    public TodoExcelExporterService(List<Todo> todoList) {
        this.todoList = todoList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Todo");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Todo date", style);
        createCell(row, 1, "Todo name", style);
        createCell(row, 2, "Todo description", style);
        createCell(row, 3, "Todo status", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Todo todo : todoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            createCell(row, columnCount++, todo.getDate().format(formatter), style);
            createCell(row, columnCount++, todo.getName(), style);
            createCell(row, columnCount++, todo.getDescription(), style);
            createCell(row, columnCount++, todo.getStatus().toString(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
