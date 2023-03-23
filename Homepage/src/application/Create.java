package application;

import java.io.*;
import javafx.stage.FileChooser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import application.Homepage;

public class Create extends Application {
	private ListView<String> contactList;
    private TextArea conversationTextArea;
    private TextArea groupSettings;
    private File selectedFile;
    
   
    
    
	
	
	
    public static void main(String[] args) {
        launch(args);
    }
    


    @Override
    public void start(Stage primaryStage) {
    	
    	double scaleFactor = 1.8;
    	
        primaryStage.setTitle("Create");

        // Create the plus button
        Button homeButton = new Button("Home");

        // Create the username label
        Label usernameLabel = new Label("Randy");

        //Create the drop down list
        ComboBox<String> onlineStatusDropdown = new ComboBox<>();
        onlineStatusDropdown.getItems().addAll("Online", "Away", "Busy");
        onlineStatusDropdown.setValue("Online");
        

        // Create the log out button
        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle("-fx-background-color: red;"); // set button color
        
        //Create group settings area
        groupSettings = new TextArea();
        
       // Create a VBox for group settings elements
        VBox groupSettingsElements = new VBox(10);
        groupSettingsElements.setAlignment(Pos.CENTER_LEFT);
        groupSettingsElements.setPadding(new Insets(5 * scaleFactor, 5 * scaleFactor, 5 * scaleFactor, 5 * scaleFactor));
        
        //Create HBOX for group name and text field
        HBox groupNameContainer = new HBox(5);
        groupNameContainer.setAlignment(Pos.CENTER_LEFT);

        //Create textfield for name of group
        Label groupName = new Label("Group Name:");
        TextField groupNameTextField = new TextField();
        
        groupNameContainer.getChildren().addAll(groupName, groupNameTextField);

        //Create label for users added
        Label usersAdded = new Label("Users Added:");

        //Create the "create group" button
        Button createGroupButton = new Button("Create Group");
        createGroupButton.setStyle("-fx-background-color: green;"); // set button color

        // Add the group name, group name text field, users added, and create group button to the VBox
        groupSettingsElements.getChildren().addAll(groupNameContainer, usersAdded, createGroupButton);

       
      
      
        //Create the conversationtext area
        conversationTextArea = new TextArea();
        conversationTextArea.setPrefWidth(420 * scaleFactor);
        conversationTextArea.setPrefHeight(400 * scaleFactor);

      //Create Contacts label
        Label contactsLabel = new Label ("Contacts");
       
        
        // Create the contactList
        contactList = new ListView<>();
        contactList.setPrefWidth(150 * scaleFactor); // set preferred width
        contactList.setPrefHeight(400 * scaleFactor);
        
       
        // Add the files to the contact list
        try {
            File contactsFile = new File("src/contacts.txt");
            FileReader fileReader = new FileReader(contactsFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> usernames = new ArrayList<>();
            String username;

            while ((username = bufferedReader.readLine()) != null) {
                usernames.add(username);
            }

            bufferedReader.close();
            fileReader.close();

            // Sort the usernames alphabetically
            Collections.sort(usernames);

            // Add the sorted usernames to the contactList
            contactList.getItems().addAll(usernames);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //Create vbox for contact label and contacts
        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(contactsLabel, contactList);


        // Create the conversation
        TextArea conversation = new TextArea();
        conversation.setPrefWidth(420 * scaleFactor);
        conversation.setPrefHeight(400 * scaleFactor);


        // Create the text box
        TextField textBox = new TextField();
        textBox.setPrefWidth(420 * scaleFactor); 

        // Create the send button
        Button sendButton = new Button("Send");

        // Create the top bar
        HBox topBar = new HBox(10);
        topBar.setSpacing(140 * scaleFactor);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10 * scaleFactor, 10 * scaleFactor, 10 * scaleFactor, 10 * scaleFactor));
        topBar.getChildren().addAll(homeButton, usernameLabel, onlineStatusDropdown, logOutButton);
        HBox.setHgrow(logOutButton, Priority.ALWAYS);
       

        // Create the bottom bar
        HBox bottomBar = new HBox(10);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setPadding(new Insets(10 * scaleFactor, 10 * scaleFactor, 10 * scaleFactor, 10 * scaleFactor));
        bottomBar.getChildren().addAll(textBox, sendButton);

        // Create the main layout
        BorderPane layout = new BorderPane();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(groupSettingsElements, conversation);
        // Set the VBox container as the center node of the layout
        layout.setCenter(vbox);
        layout.setTop(topBar);
        layout.setLeft(vbox2);
        layout.setBottom(bottomBar);
        
        
     // Put the txt file in the conversation window
        File selectedFile = new File("src/Empty.txt");
        if (selectedFile.exists()) {
            try {
                // Read the contents of the file using FileReader and BufferedReader
                FileReader fileReader = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder conversationText = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    conversationText.append(line).append("\n");
                }
                bufferedReader.close();
                fileReader.close();

                // Set the conversation TextArea text to the contents of the file
                conversation.setText(conversationText.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        //Send message
        sendButton.setOnAction(event -> {
            String message = usernameLabel.getText() + ": " + textBox.getText() + "\n";
            conversation.appendText(message);
            textBox.clear();

            try {
                // Open the convo.txt file in append mode and write the message to it
                FileWriter fileWriter = new FileWriter("src/Sandy.txt", true);
                fileWriter.write(message);
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        // Set action for Home button
        homeButton.setOnAction(e -> {
            

        	Homepage homepage = new Homepage();
            homepage.showHomepage(primaryStage);
            
        });
        
        // Set action for logout button
        logOutButton.setOnAction(e -> {
            

            Login login = new Login();
            login.showLogin(primaryStage);
          
        });
        
        
    
        
           

        // Create the scene
        Scene scene = new Scene(layout, 640 * scaleFactor, 480 * scaleFactor);

        // Set the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void showHomepage(Stage stage) {
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



	public void showCreate(Stage stage) {
		try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
		
	
}
    