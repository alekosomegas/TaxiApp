package com.ak.taxiapp.util;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceTable;
import com.ak.taxiapp.model.invoice.InvoiceTableRow;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.image.ImageDataFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import com.itextpdf.layout.borders.Border;


import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;

import static com.itextpdf.kernel.pdf.PdfName.Color;
import static com.itextpdf.kernel.pdf.PdfName.ca;

// endregion
// ------------------------------------------------------------------ //

public class GeneratePDF {
    // ------------------------------------------------------------------ //
    //region// ---------------------------- VARIABLES --------------------------- //

    private final String ROOT_DEST = "Exports";
    private String yearDir;
    private String monthDir;

    private Invoice invoice;
    private InvoiceTable invoiceTable;
    private String fileName;
    private float pageHeight;
    private float pageWidth;
    private float rightMargin;
    private float leftMargin;

    private final HashMap<String, HashMap<String, String>> COMPANY = Propetries.COMPANY;


    //endregion
    // ------------------------------------------------------------------ //

    public GeneratePDF(Invoice invoice) throws Exception {
        this.invoice = invoice;
        this.invoiceTable = invoice.getInvoiceTable();
        builtFileName();
        buildDateDirectories();

        File file = new File(ROOT_DEST +"/"+ yearDir + "/"+ monthDir);
        file.mkdirs();
        file.getParentFile().mkdirs();
        manipulatePdf(ROOT_DEST +"/"+ yearDir +"/"+ monthDir +"/"+ fileName);
    }


    protected void manipulatePdf (String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        pdfDoc.setDefaultPageSize(PageSize.A4);
        pageWidth = pdfDoc.getDefaultPageSize().getWidth();
        pageHeight = pdfDoc.getDefaultPageSize().getHeight();
        rightMargin = doc.getRightMargin();
        leftMargin = doc.getLeftMargin();

        PdfPage page = pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(page);

        canvas.setStrokeColor(ColorConstants.DARK_GRAY)
                .setLineWidth(0.5f)
                .moveTo(leftMargin,100)
                .lineTo(pageWidth - rightMargin, 100);

        doc.add(invoiceInfo());
        doc.add(companyLogo());
        doc.add(companyInfo());
        doc.add(clientInfo());
        doc.add(table());
        doc.add(notes());
        doc.add(bankInfo());


        doc.close();
    }



    private void builtFileName() {
        String clientName = invoice.getClientName().replaceAll(" ", "_");
        this.fileName = invoice.getDateString() + "_" + clientName + "_invoice.pdf";
    }

    private void buildDateDirectories() {
        this.yearDir = invoice.getYearString();
        this.monthDir = invoice.getMonthString();
    }

    private Paragraph companyInfo() {
        Paragraph paragraph = new Paragraph();

        HashMap<String, String> info = COMPANY.get("INFO");
        Text name = new Text(info.get("NAME"));
        name.setBold();
        name.setFontSize(11);
        paragraph.add(name);

        paragraph.setFontSize(7);
        paragraph.setMultipliedLeading(1.1F);

        paragraph.add("\n");
        paragraph.add(info.get("ADDRESS"));

        paragraph.add("\nTel: ");
        paragraph.add(info.get("TEL"));
        paragraph.add("\nEmail: ");
        paragraph.add(info.get("EMAIL"));

        paragraph.add("\nVAT no : ");
        paragraph.add(info.get("VAT"));

        paragraph.setMarginBottom(20F);

        return paragraph;
    }

    private Paragraph companyLogo() throws MalformedURLException {
        Paragraph paragraph = new Paragraph();

        String LOGO = "C:\\Users\\Alexandros\\iCloudDrive\\Java\\TaxiApp\\src\\main\\resources\\com\\ak\\taxiapp\\icons\\company_logo.PNG";
        Image logo = new Image(ImageDataFactory.create(LOGO));
        logo.setHeight(40);
        logo.setWidth(50);
        paragraph.add(logo);

        return paragraph;
    }

    private Paragraph clientInfo() {
        Paragraph paragraph = new Paragraph();

        paragraph.setFontSize(7);
        paragraph.setMultipliedLeading(1.1F);

        paragraph.add("Bill To: ");

        Text name = new Text(invoice.getClientName().toUpperCase());
        name.setBold();
        name.setFontSize(11);
        paragraph.add("\n");
        paragraph.add(name);

        paragraph.add("\n");
        paragraph.add(invoice.getClientAddress());
        paragraph.add("\n");
        paragraph.add("Email: ");
        paragraph.add(invoice.getClientEmail());
        paragraph.add("\n");
        paragraph.add("Tel: ");
        paragraph.add(invoice.getClientTel());

        paragraph.setMarginBottom(20F);

        return paragraph;
    }

    private Paragraph invoiceInfo() {
        Paragraph paragraph = new Paragraph();

        paragraph.setTextAlignment(TextAlignment.RIGHT);
        paragraph.setFontSize(9);
        paragraph.setMultipliedLeading(1.2F);

        Text invoice = new Text("Invoice");
        invoice.setBold();
        invoice.setFontSize(19);

        paragraph.add(invoice);
        paragraph.add("\n");

        Text invoiceNumber = new Text(this.invoice.getId());
        invoiceNumber.setBold();

        paragraph.add("Invoice no. ");
        paragraph.add(invoiceNumber);

        paragraph.add("\n\n\n\n\n\n");
        paragraph.add("Date: ");
        paragraph.add(this.invoice.getDatePrint());

        paragraph.setFixedPosition(pageWidth - rightMargin - 100, 650, 100);

        return paragraph;
    }

    private Paragraph notes() {
        Paragraph paragraph = new Paragraph();
        paragraph.setFontSize(9);
        paragraph.add("\n\n");
        paragraph.add("Notes");
        paragraph.add("\n");
        if(invoice.getNotes() != null) {
            paragraph.add(invoice.getNotes());
        }

        return paragraph;
    }

    private Table table() {
        Table table = new Table(UnitValue.createPercentArray(invoiceTable.getNumOfColumns())).useAllAvailableWidth();

        int TOTAL_ROWS = 25;
        float ROW_HEIGHT = 12;
        int emptyRows = TOTAL_ROWS - invoiceTable.getAllRows().size();

        for (String header : invoiceTable.getHEADERS()) {
            Cell cell = new Cell();
            Paragraph paragraph2 = new Paragraph(header);
            paragraph2.setFontSize(9);
            paragraph2.setFontColor(ColorConstants.WHITE);

            cell.add(paragraph2);
            cell.setBorder(new SolidBorder(ColorConstants.WHITE, 1));
            cell.setBackgroundColor(ColorConstants.DARK_GRAY);
            cell.setHeight(ROW_HEIGHT);
            table.addCell(cell);
        }
        for (InvoiceTableRow row : invoiceTable.getAllRows()) {
            for (int i = 0; i < 7; i++) {
                Cell cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.setHeight(ROW_HEIGHT);
                Paragraph paragraph1 = new Paragraph(row.getAllData().get(i));
                paragraph1.setFontSize(9);
                cell.add(paragraph1);
                if (invoiceTable.getAllRows().indexOf(row) % 2 != 0) {
                    cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                }
                table.addCell(cell);
            }
        }

//        for (int i = 0; i < 1; i++) {
//            Cell cell = new Cell(0,7);
//            cell.setBorder(Border.NO_BORDER);
//            cell.setHeight(ROW_HEIGHT);
//            table.addCell(cell);
//        }
//
//        for (int i = 0; i < emptyRows; i++) {
//            Cell cell = new Cell(0,7);
//            cell.setBorder(Border.NO_BORDER);
//            cell.setHeight(ROW_HEIGHT);
//            table.addCell(cell);
//        }

        Cell emptyCells = new Cell(0,5);
        emptyCells.setBorder(Border.NO_BORDER);
        emptyCells.setHeight(ROW_HEIGHT);


        Cell lastEmptyCell = new Cell(0,7);
        lastEmptyCell.setBorder(Border.NO_BORDER);
        lastEmptyCell.setBorderBottom(new SolidBorder(ColorConstants.DARK_GRAY, 0.5f));
        lastEmptyCell.setHeight(ROW_HEIGHT);

        table.addCell(lastEmptyCell);
        table.addCell(emptyCells);

        Cell subtotalLbl = new Cell();
        subtotalLbl.setBorder(Border.NO_BORDER);
        subtotalLbl.setHeight(ROW_HEIGHT);
        Paragraph txSubtotalLbl = new Paragraph("Subtotal");
        txSubtotalLbl.setFontSize(9);
        subtotalLbl.add(txSubtotalLbl);
        table.addCell(subtotalLbl);

        Cell subtotal = new Cell();
        subtotal.setBorder(Border.NO_BORDER);
        subtotal.setHeight(ROW_HEIGHT);
        Paragraph txSubtotal = new Paragraph("€ " + invoiceTable.getTotal());
        txSubtotal.setFontSize(9);
        subtotal.add(txSubtotal);
        table.addCell(subtotal);

        table.addCell(emptyCells);

        Cell lblVat = new Cell();
        lblVat.setBorder(Border.NO_BORDER);
        lblVat.setHeight(ROW_HEIGHT);
        Paragraph txlblVat = new Paragraph("VAT 9%");
        txlblVat.setFontSize(9);
        lblVat.add(txlblVat);
        table.addCell(lblVat);


        DecimalFormat f = new DecimalFormat("##.00");

        Cell vat = new Cell();
        vat.setBorder(Border.NO_BORDER);
        vat.setHeight(ROW_HEIGHT);
        Paragraph txVat = new Paragraph("€ " + f.format((double)invoiceTable.getTotal() * (double)9/100) );
        txVat.setFontSize(9);
        vat.add(txVat);
        table.addCell(vat);

        table.addCell(emptyCells);

        Cell lblTotal = new Cell();
        lblTotal.setBorder(Border.NO_BORDER);
        lblTotal.setHeight(ROW_HEIGHT);
        Paragraph txlblTotal = new Paragraph("Total");
        txlblTotal.setFontSize(9);
        lblTotal.add(txlblTotal);
        table.addCell(lblTotal);

        Cell total = new Cell();
        total.setBorder(Border.NO_BORDER);
        total.setHeight(ROW_HEIGHT);
        Paragraph txTotal = new Paragraph("€ " + f.format((double)invoiceTable.getTotal() * (double)109/100));
        txTotal.setFontSize(9);
        txTotal.setBold();
        total.add(txTotal);
        table.addCell(total);

//        table.addFooterCell("Total");
//        table.addFooterCell(String.valueOf(invoiceTable.getTotal()));
        return table;
    }

    private Paragraph bankInfo() {
        Paragraph paragraph = new Paragraph();
        paragraph.setFontSize(9);
        paragraph.setMultipliedLeading(1.2F);

        HashMap<String, String> info = COMPANY.get("BANK");

        paragraph.add("Account name: ");
        paragraph.add(info.get("ACCOUNT NAME"));
        paragraph.add("\n");
        paragraph.add("Account number: ");
        paragraph.add(info.get("ACCOUNT NUMBER"));
        paragraph.add("\n");
        paragraph.add("IBAN: ");
        paragraph.add(info.get("IBAN"));
        paragraph.add("\n");
        paragraph.add("BIC: ");
        paragraph.add(info.get("BIC"));
        paragraph.add("\n");

        paragraph.setFixedPosition(leftMargin, 40, 500);

        return paragraph;

    }
}