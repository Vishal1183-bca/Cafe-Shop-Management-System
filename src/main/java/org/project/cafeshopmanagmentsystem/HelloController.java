package org.project.cafeshopmanagmentsystem;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label cafe1;

    @FXML
    private Button createButton1;

    @FXML
    private Label createLabel1;

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_backl_btn;

    @FXML
    private Button fp_proceed_btn;

    @FXML
    private ComboBox<?> fp_quetion;

    @FXML
    private Button ihaveAlrady;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Label loginLabel1;

    @FXML
    private Label loginLabel11;

    @FXML
    private AnchorPane login_format1;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField no_confirmPass;

    @FXML
    private Button np_back_btn;

    @FXML
    private Button np_changePass_btn;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassWord;

    @FXML
    private AnchorPane register_Fromat;

    @FXML
    private TextField register_answer;

    @FXML
    private ComboBox<?> register_combo;

    @FXML
    private Label register_label;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_username;

    @FXML
    private Hyperlink si_forgat;

    @FXML
    private AnchorPane side_format1;

    @FXML
    private AnchorPane si_login_format1;

    @FXML
    private AnchorPane si_login_format11;

    @FXML
    private AnchorPane si_login_format111;

    @FXML
    private Button singUpBtn;
    @FXML
    private AnchorPane fp_format;

    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet resultSet;

    private Alert alert;






    public void loginBTN()
    {
        if(login_username.getText().isEmpty() || login_password.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username or Password");
            alert.showAndWait();
        }
        else {
            String selectData = "SELECT username, password FROM signUpp WHERE username = ? and password = ?";
            connection = Conn.connectDB();
            try{
                prepare = connection.prepareStatement(selectData);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_password.getText());
                resultSet = prepare.executeQuery();
                if(resultSet.next())
                {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username or Password");

                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }




    public void regBtn()
    {
        if(register_username.getText().isEmpty() || register_password.getText().isEmpty() || register_combo.getSelectionModel().getSelectedItem() == null || register_answer.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errot Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill all black Fields");
            alert.showAndWait();
        }
        else{
            String reg_query = "INSERT INTO signUpp (username,password,question,answer,date)" + "VALUES (?,?,?,?,?)";
            connection = Conn.connectDB();

            try{
                //Check if the Username is Already Recorded
                String checck_Username = "SELECT username FROM signUpp WHERE username = '" + register_username.getText() + "'";
                prepare = connection.prepareStatement(checck_Username);
                resultSet = prepare.executeQuery();

                if(resultSet.next())
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(register_username.getText() + " already exists");
                    alert.showAndWait();
                }
                else if(register_password.getText().length() < 8)
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(register_password.getText() + " is too short atleast 8 characters are needed");
                    alert.showAndWait();
                }
                else{

                    prepare= connection.prepareStatement(reg_query);
                    prepare.setString(1, register_username.getText());
                    prepare.setString(2,register_password.getText());
                    prepare.setString(3,register_combo.getSelectionModel().getSelectedItem().toString());
                    prepare.setString(4,register_answer.getText());
                    prepare.setDate(5,java.sql.Date.valueOf(LocalDate.now()));
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered Account");
                    alert.showAndWait();

                    register_username.setText("");
                    register_password.setText("");
                    register_combo.getSelectionModel().clearSelection();
                    register_answer.setText("");
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(side_format1);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e)->
                    {
                        ihaveAlrady.setVisible(false);
                        createButton1.setVisible(true);
                    });
                    slider.play();
                }



            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    private String[] quetionList = {"What is Your Favorite Foood?","What is Your Favorite Color?","What is Your Favorite Car?"};
    public void regLquetion()
    {
        List<String> listQ = new ArrayList<>();
        for(String data : quetionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableList(listQ);
        register_combo.setItems(listData);
    }

    public void switchForgatPass()
    {
        fp_format.setVisible(true);
        si_login_format1.setVisible(false);


    }

    public void switchForm(ActionEvent event)
    {
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == createButton1)
        {
            slider.setNode(side_format1);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e)->{
               ihaveAlrady.setVisible(true);
                createButton1.setVisible(false);
                regLquetion();
            });
            slider.play();

        } else if (event.getSource() == ihaveAlrady)
        {
            slider.setNode(side_format1);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e)->
            {
                ihaveAlrady.setVisible(false);
                createButton1.setVisible(true);
            });
            slider.play();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}