package com.ak.taxiapp.controller.invoice;

import com.ak.taxiapp.util.Controller;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class InvoiceRowController extends Controller {


    public Rectangle recStatus;
    public Label lblStatus;

    public Label lblId;
    public Label lblClient;
    public Label lblEmail;
    public Label lblTel;
    public Label lblDate;
    public Label lblRange;
    public Label lblTotal;
    public Label lblVAT;
    public Label lblNotes;



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


}
