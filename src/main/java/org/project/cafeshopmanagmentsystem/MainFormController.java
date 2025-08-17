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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
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
    private TreeTableColumn<ProductData, Date> inventory_col_date;

    @FXML
    private TreeTableColumn<ProductData, String> inventory_col_id;

    @FXML
    private TreeTableColumn<ProductData, Double> inventory_col_price;

    @FXML
    private TreeTableColumn<ProductData, String> inventory_col_product_name;

    @FXML
    private TreeTableColumn<ProductData, String> inventory_col_status;

    @FXML
    private TreeTableColumn<ProductData, String> inventory_col_stock;

    @FXML
    private TreeTableColumn<ProductData, String> inventory_col_tyoe;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private TreeTableView<ProductData> inventory_tableview;

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


    private Image image;
    public void inventoryAddBtn()
    {
        if(inventory_product_id.getText().isEmpty()
                ||  inventory_product_name.getText().isEmpty()
                ||  inventory_stock.getText().isEmpty()
                ||  inventory_price.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.pasth == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }
        else{
            String checkProdID = "select prod_id from product where prod_id= '"+inventory_product_id.getText()+"' ";
            connect = Conn.connectDB();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);
                if(result.next())
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_product_id.getText() + " already exists");
                    alert.showAndWait();
                }
                else{
                    String insertData = "insert into product " + "(prod_id,prod_name,stock,price,status,type,image,date)"+"values(?,?,?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_product_id.getText());
                    prepare.setString(2, inventory_product_name.getText());
                    prepare.setString(3, inventory_stock.getText());
                    prepare.setDouble(4, Double.parseDouble(inventory_price.getText()));
                    prepare.setString(5, inventory_status.getSelectionModel().getSelectedItem().toString());
                    prepare.setString(6, inventory_type.getSelectionModel().getSelectedItem().toString());
                    String path = data.pasth;
                    path = path.replace("\\","\\\\");
                    prepare.setString(7, path);
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(8,String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added product");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void inventoryUpdateBtn()
    {
        if(inventory_product_id.getText().isEmpty()
                ||  inventory_product_name.getText().isEmpty()
                ||  inventory_stock.getText().isEmpty()
                ||  inventory_price.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.pasth == null || data.id == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }
        else{
            String path = data.pasth;
            path = path.replace("\\","\\\\");
            String updateData = "Update product set "
                    + "prod_id='"+inventory_product_id.getText()
                    +"',prod_name = '"+inventory_product_name.getText()
                    +"', stock = '"+inventory_stock.getText()
                    +"',price = '"+inventory_price.getText()
                    +"',status = '"+inventory_status.getSelectionModel().getSelectedItem().toString()
                    +"',type = '"+inventory_type.getSelectionModel().getSelectedItem().toString()
                    +"',image = '"+path+"',date = '"+data.date+"' where id = " + data.id;

            connect = Conn.connectDB();
            try{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update the product ID " + inventory_product_id.getText() + "?");
                Optional<ButtonType> option =  alert.showAndWait();
                if(option.get() == ButtonType.OK){
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated the product ID " + inventory_product_id.getText() + "!");
                    alert.showAndWait();
                    inventoryShowData();
                    inventoryClearBtn();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }




            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void inventoryClearBtn()
    {
        inventory_product_id.setText("");
        inventory_product_name.setText("");
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        inventory_type.getSelectionModel().clearSelection();
        data.pasth = "";
        data.id = 0;
        inventory_imageView.setImage(null);
    }

    public void inventoryImportBtn()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open image","*png","*jpg","*jpeg"));
        File file = fileChooser.showOpenDialog(main_form.getScene().getWindow());
        if(file != null)
        {
            data.pasth = file.getAbsolutePath();
            image = new Image(file.toURI().toString());
            inventory_imageView.setImage(image);
        }
    }
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
                        result.getDate("date"));

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
        TreeTableColumn<ProductData, Date> inventory_col_date = new TreeTableColumn<>("date");


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
                param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getStock()))
        );

        inventory_col_price.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getValue().getPrice())
        );
        inventory_col_status.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getValue().getStatus())
        );
        inventory_col_date.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getValue().getDate())
        );


        TreeItem<ProductData> root = new TreeItem<>(new ProductData());
        root.setExpanded(true);

        for (ProductData data : inventoryListData) {
            root.getChildren().add(new TreeItem<>(data));
        }
        inventory_tableview.setRoot(root);
        inventory_tableview.setShowRoot(false);

        inventory_tableview.getColumns().setAll(
                inventory_col_id,
                inventory_col_product_name,
                inventory_col_tyoe,
                inventory_col_stock,
                inventory_col_price,
                inventory_col_status,
                inventory_col_date
        );



    }

    public void inventorySelectData()
    {
        ProductData productData = inventory_tableview.getSelectionModel().getSelectedItem().getValue();
        int num = inventory_tableview.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) return;
        inventory_product_id.setText(String.valueOf(productData.getProductId()));
        inventory_product_name.setText(productData.getProductName());

        inventory_stock.setText(String.valueOf(productData.getStock()));
        data.date = String.valueOf(productData.getDate());
        data.id = productData.getId();
        data.pasth = "File:" + productData.getImage();
        inventory_imageView.setImage(new Image(data.pasth));
        inventory_price.setText(String.valueOf(productData.getPrice()));

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
        inventoryShowData();
    }
}
