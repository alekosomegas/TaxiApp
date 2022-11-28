package com.ak.taxiapp.util;
// ------------------------------------------------------------------ //
//region// ----------------------------- IMPORTS ---------------------------- //

import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.model.invoice.InvoiceTable;
import com.ak.taxiapp.model.invoice.InvoiceTableRow;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;

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

        Paragraph paragraph = new Paragraph();
        paragraph.add("Client: ");
        paragraph.add(invoice.getClientName());
        paragraph.add("\n");
        paragraph.add("Address: ");
        paragraph.add(invoice.getClientAddress());
        paragraph.add("\n");
        paragraph.add("Email: ");
        paragraph.add(invoice.getClientEmail());
        paragraph.add("\n");
        paragraph.add("Tel: ");
        paragraph.add(invoice.getClientTel());

        doc.add(paragraph);

        Table table = new Table(UnitValue.createPercentArray(invoiceTable.getNumOfColumns())).useAllAvailableWidth();

        for (String header : invoiceTable.getHEADERS()) {
            table.addCell(header);
        }
        for (InvoiceTableRow row : invoiceTable.getAllRows()) {
            for (int i = 0; i < 7; i++) {
                table.addCell(row.getAllData().get(i));
            }

        }


        doc.add(table);

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
}