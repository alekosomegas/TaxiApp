package com.ak.taxiapp.controller.invoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceCardController implements Initializable {
    @FXML public Rectangle recStatus;
    @FXML public Label lblStatus;
    @FXML public Label lblId;
    @FXML public Label lblClient;
    @FXML public Label lblFrom;
    @FXML public Label lblTo;
    @FXML public Label lblDate;
    @FXML public Label lblTotal;
    @FXML public Label lblVAT;
    @FXML public Label lblNotes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setRecStatus(String recStatusColor) {
        this.recStatus.setFill(Paint.valueOf(recStatusColor));
    }

    public void setLblStatus(String lblStatus) {
        this.lblStatus.setText(lblStatus);
    }

    public void setLblId(String lblId) {
        this.lblId.setText(lblId);
    }

    public void setLblClient(String lblClient) {
        this.lblClient.setText(lblClient);
    }

    public void setLblFrom(String lblFrom) {
        this.lblFrom.setText(lblFrom);
    }

    public void setLblTo(String lblTo) {
        this.lblTo.setText(lblTo);
    }

    public void setLblDate(String lblDate) {
        this.lblDate.setText(lblDate);
    }

    public void setLblTotal(String lblTotal) {
        this.lblTotal.setText(lblTotal);
    }

    public void setLblVAT(String lblVAT) {
        this.lblVAT.setText(lblVAT);
    }

    public void setLblNotes(String lblNotes) {
        this.lblNotes.setText(lblNotes);
    }
}
