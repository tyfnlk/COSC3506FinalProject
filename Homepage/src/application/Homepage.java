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
import javafx.scene.transform.Scale;
import java.util.Arrays;
import java.util.Comparator;

public class Homepage extends Application {
	private ListView<File> inboxListView;
    private TextArea conversationTextArea;
    private File selectedFile;
    
    
    
   
    
    
	
	
	
    public static void main(String[] args) {
        launch(args);
    }
    


    @Override
    public void start(Stage primaryStage) {
    	
    	double scaleFactor = 1.8;
    	
        primaryStage.setTitle("Home");

        // Create the plus button
        Button plusButton = new Button("+");

        // Create the username label
        Label usernameLabel = new Label("Randy");

        //Create the drop down list
        ComboBox<String> onlineStatusDropdown = new ComboBox<>();
        onlineStatusDropdown.getItems().addAll("Online", "Away", "Busy");
        onlineStatusDropdown.setValue("Online");
        
        //Create conversation title
        Label conversationTitle = new Label("Conversation:");
        
      
      
        //Create the conversationtext area
        conversationTextArea = new TextArea();
        conversationTextArea.setPrefWidth(420 * scaleFactor);
        conversationTextArea.setPrefHeight(400 * scaleFactor);


        // Create the log out button
        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle("-fx-background-color: red;"); // set button color
        
        
        //Create inbox label
        Label inboxLabel = new Label ("Inbox");
       
        
        // Create the inbox
        inboxListView = new ListView<>();
        inboxListView.setPrefWidth(150 * scaleFactor); // set preferred width
        inboxListView.setPrefHeight(400 * scaleFactor);
        
        
        inboxListView.setOnMouseClicked(event -> {
            selectedFile = inboxListView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                try {
                    // Read the contents of the text file using FileReader and BufferedReader
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder conversationText = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        conversationText.append(line).append("\n");
                    }
                    bufferedReader.close();
                    fileReader.close();

                    // Set the conversation TextArea text to the contents of the text file
                    conversationTextArea.setText(conversationText.toString());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //Change conversation name based on which conversation is clicked in the inbox
                selectedFile = inboxListView.getSelectionModel().getSelectedItem();
                if (selectedFile != null) {
                    String conversationTitleText = selectedFile.getName();
                    conversationTitle.setText(conversationTitleText);
                    try {
                        // Read the contents of the text file using FileReader and BufferedReader
                        FileReader fileReader = new FileReader(selectedFile);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        StringBuilder conversationText = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            conversationText.append(line).append("\n");
                        }
                        bufferedReader.close();
                        fileReader.close();

                        // Set the conversation TextArea text to the contents of the text file
                        conversationTextArea.setText(conversationText.toString());

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Add the files to the inbox
        File[] files = new File("src").listFiles();
        if (files != null) {
            // Sort the files based on last modified time (most recent first)
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    inboxListView.getItems().add(file);
                }
            }
        }


      

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
        topBar.getChildren().addAll(plusButton, usernameLabel, onlineStatusDropdown, logOutButton);
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
        vbox.getChildren().addAll(conversationTitle, conversationTextArea);
        // Set the VBox container as the center node of the layout
        layout.setCenter(vbox);
        layout.setTop(topBar);
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(inboxLabel, inboxListView);
        
        layout.setLeft(vbox2);
        layout.setBottom(bottomBar);
        
        
     // Put the txt file in the conversation window
        selectedFile = inboxListView.getSelectionModel().getSelectedItem();
       
        
        //Send message
        sendButton.setOnAction(event -> {
            String message = usernameLabel.getText() + ": " + textBox.getText() + "\n";
            conversationTextArea.appendText(message);
            textBox.clear();

            try {
                // Open the convo.txt file in append mode and write the message to it
            	FileWriter fileWriter = new FileWriter(selectedFile, true);
                fileWriter.write(message);
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        });
        
        // Set action for logout button
        logOutButton.setOnAction(e -> {
            

            Login login = new Login();
            login.showLogin(primaryStage);
            
         
          
        });
        
     // Set action for + button
        plusButton.setOnAction(e -> {
            

            Create create = new Create();
            create.showCreate(primaryStage);
          
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
		
	
}
    
 
