package org.project.cafeshopmanagmentsystem;

import com.gluonhq.charm.glisten.control.Icon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.project.cafeshopmanagmentsystem.data.username;

public class MainFormController implements Initializable
{

    @FXML
    private Button add_btn;

    @FXML
    private Label card_1_label_1;

    @FXML
    private Label card_1_label_2;

    @FXML
    private Label card_2_label_1;

    @FXML
    private Label card_2_label_2;

    @FXML
    private Label card_3_label_1;

    @FXML
    private Label card_3_label_2;

    @FXML
    private Label card_4_label_1;

    @FXML
    private Label card_4_label_2;

    @FXML
    private Icon cart_icon;

    @FXML
    private Button clear_btn;

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Label doller;

    @FXML
    private Button import_btn;

    @FXML
    private Button inventory_btn;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_date;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_id;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_price;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_product_name;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_status;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_stock;

    @FXML
    private TreeTableColumn<?, ?> inventory_col_tyoe;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private TreeTableView<?> inventory_tableview;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label manu_label_1;

    @FXML
    private Button menu_btn;

    @FXML
    private Label menu_label_2;

    @FXML
    private Icon money_icon;

    @FXML
    private Icon people_icon;

    @FXML
    private Button signout_btn;

    @FXML
    private Button update_btn;
    @FXML
    private  Label username;


    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_product_id;

    @FXML
    private TextField inventory_product_name;

    @FXML
    private TextField inventory_stock;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private ComboBox<?> inventory_type;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    public ObservableList<ProductData> inventoryDataList()
    {
        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        String sql = "Select * from product";
        connect = Conn.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ProductData productData;
            while (result.next())
            {
                productData = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("type"),
                        result.getString("image"),
                        result.getDate("datel"));

                listData.add(productData);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listData;
    }


    private ObservableList<ProductData> inventoryListData;

    public void inventoryShowData()
    {
            inventoryListData = inventoryDataList();
        TreeTableColumn<ProductData, String> inventory_col_id = new TreeTableColumn<>("productId");
        TreeTableColumn<ProductData, String> inventory_col_product_name = new TreeTableColumn<>("productName");
        TreeTableColumn<ProductData, String> inventory_col_tyoe = new TreeTableColumn<>("type");
        TreeTableColumn<ProductData, String> inventory_col_stock = new TreeTableColumn<>("stock");
        TreeTableColumn<ProductData, Double> inventory_col_price = new TreeTableColumn<>("Price");
        TreeTableColumn<ProductData, String> inventory_col_status = new TreeTableColumn<>("status");
        TreeTableColumn<ProductData, Date> inventory_col_date = new TreeTableColumn<>("datel");


        inventory_col_id.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getProductId())
        );
        inventory_col_product_name.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getProductName())
        );
        inventory_col_tyoe.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getType())
        );
        inventory_col_stock.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getStatus())
        );
        inventory_col_price.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getValue().getPrice())
        );
        inventory_col_status.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getStatus())
        );
        inventory_col_date.setCellValueFactory(
                param -> new SimpleObjectProperty(param.getValue().getValue().getDatel())
        );





    }


    private String[] typeList = {"Meals","Drinks"};
    public void inventoryTypeList()
    {
        List<String> typeL = new ArrayList<>();
        for(String list : typeList){
            typeL.add(list);
        }
        ObservableList listdata =  FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listdata);
    }

    private String[] statusList = {"Availabel","Unavailable"};
    public void statusList()
    {
        List<String> statusL = new ArrayList<>();
        for(String list : statusList){
            statusL.add(list);
        }
        ObservableList listdata =  FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listdata);
    }

    public void logout()
    {
        try{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Are you Sure you want to Log-out");
            Optional<ButtonType> optional = alert.showAndWait();

            if(optional.get().equals(ButtonType.OK)){
                signout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Cafe Shop Management System");
                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void displayUsername()
    {
        String user = data.username;
        user = user.substring(0,1).toUpperCase()+user.substring(1);
        username.setText(user);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        inventoryTypeList();
        statusList();
    }
}
