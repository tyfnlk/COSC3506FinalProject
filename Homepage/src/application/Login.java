package application;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import application.Homepage;

public class Login extends Application {
	
	
	 public static void main(String[] args) {
	        launch(args);
	    }
	 
    @Override
    public void start(Stage primaryStage) {
    	
    	double scaleFactor = 1.8;
    	
        primaryStage.setTitle("Login");

        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25 * scaleFactor, 25 * scaleFactor, 25 * scaleFactor, 25 * scaleFactor));
        
        //Add Company Logo
        Image logoImage = new Image("logo-social.png");
        ImageView logoView = new ImageView(logoImage);
        grid.add(logoView, 0, 0, 2, 1);
        GridPane.setHalignment(logoView, HPos.CENTER); 
        
     
        // Add Company Name Label
        Label scenetitle = new Label("Company Name");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 1, 2, 1);
        GridPane.setHalignment(scenetitle, HPos.CENTER);
       

        // Add a label for the username
        Label userName = new Label("Username:");
        grid.add(userName, 0, 2);

        // Add a text field for the username
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);

        // Add a label for the password
        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        // Add a password field for the password
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        // Add a login button
        Button btn = new Button("Login");
        grid.add(btn, 1, 4);
        btn.setStyle("-fx-background-color: #8BC34A; -fx-text-fill: white;");
     

        // Set action for login button
        btn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            // Add login process here.....

            // Clear the text fields after login
           
            
          if ("username".equals(username) && "password".equals(password)) {
            Homepage homepage = new Homepage();
            homepage.showHomepage(primaryStage);
            
          }
          else {
        	  userTextField.clear();
              pwBox.clear();
          } 
          
        });
   
        
      

        // Create a scene and set it to the stage
        Scene scene = new Scene(grid, 640 * scaleFactor, 480 * scaleFactor);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    

    public void showLogin(Stage stage) {
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
		
	
}

