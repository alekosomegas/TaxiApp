package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.TaxiApplication;
import com.ak.taxiapp.model.invoice.Invoice;
import com.ak.taxiapp.util.Controller;
import com.ak.taxiapp.util.GeneratePDF;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;

public class InvoiceRowController extends Controller {


    @FXML public Rectangle recStatus;
    @FXML public Label lblStatus;

    @FXML public Label lblId;
    @FXML public Label lblClient;
    @FXML public Label lblEmail;
    @FXML public Label lblTel;
    @FXML public Label lblDate;
    @FXML public Label lblRange;
    @FXML public Label lblTotal;
    @FXML public Label lblVAT;
    @FXML public Label lblNotes;
    @FXML public HBox hbRowContainer;

    public Invoice invoice;


    @FXML
    public void onEdit() throws SQLException {
        TaxiApplication.showInvoiceEditView(invoice);
    }

    @FXML
    public void onPrint() throws Exception {
        invoice.setStatus(Invoice.Status.CLOSED);
        invoice.updateStatus();
        new GeneratePDF(invoice);
    }

    public void traverse(Pane pane, Integer mode) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Pane) {
                Pane child = (Pane) node;
                traverse(child, mode);
            } else if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getId() != null) {
                    if(mode == 1) {
                        label.getStyleClass().add("text--table--white");
                    } else {
                        label.getStyleClass().remove("text--table--white");
                    }
                }
            }
        }

    }
    public void highlight() {
        traverse(hbRowContainer, 1);

    }
    public void unhighlight() {
        traverse(hbRowContainer, 2);
    }

    public void setLblId(String lblId) {
        this.lblId.setText(lblId);
    }

    public void setLblClient(String lblClient) {
        this.lblClient.setText(lblClient);
    }

    public void setLblDate(String lblDate) {
        this.lblDate.setText(lblDate);
    }

    public void setLblTotal(String lblTotal) {
        this.lblTotal.setText(lblTotal);
    }

    public void setLblNotes(String lblNotes) {
        this.lblNotes.setText(lblNotes);
    }

    public void setLblEmail(String lblEmail) {
        this.lblEmail.setText(lblEmail);
    }

    public void setLblTel(String lblTel) {
        this.lblTel.setText(lblTel);
    }

    public void setLblRange(String lblRange) {
        this.lblRange.setText(lblRange);
    }

    public void setRecStatus(Color color) {
        this.recStatus.setFill(color);
    }

    public void setLblStatus(String lblStatus) {
        this.lblStatus.setText(lblStatus);
    }

    public void setLblVAT(String lblVAT) {
        this.lblVAT.setText(lblVAT);
    }

    public void update() {

    }
}
