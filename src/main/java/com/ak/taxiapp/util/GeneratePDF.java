package com.ak.taxiapp.util;

import com.ak.taxiapp.model.Ride;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.util.ArrayList;


public class GeneratePDF {

    private String DEST = "Exports/";

    private Invoice invoice;
    private InvoiceTable invoiceTable;
    private String fileName;


    public GeneratePDF(Invoice invoice) throws Exception {
        this.invoice = invoice;
        this.invoiceTable = invoice.getInvoiceTable();
        this.fileName = invoice.getClientName().replace(" ", "_");
        DEST = DEST + fileName + ".pdf";
        System.out.println();

        File file = new File(DEST);
        file.getParentFile().mkdirs();

        manipulatePdf(DEST);
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

        ;
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
}