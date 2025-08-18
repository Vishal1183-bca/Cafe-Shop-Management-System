package org.project.cafeshopmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CardProductController implements Initializable
{

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_AddBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<?> prod_spinner;

    private Image image;
    private ProductData productData;
    public void setData(ProductData productData)
    {
        this.productData = productData;
        prod_name.setText(productData.getProductName());
        prod_price.setText(String.valueOf(productData.getPrice()));
        String path = productData.getImage();
        image =  new Image(path);
        prod_imageView.setImage(image);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
