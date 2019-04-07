package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


//warisa's comment
public class clientFX extends Application {

    private Scene startScene;
    private Scene gameScene, waitingScene;
    private NetworkConnection conn;
    private TextArea ipInput = new TextArea();
    private TextArea portInput = new TextArea();
    private TextArea nameInput = new TextArea();
    private Label ip, port, messages, name,  startMessages;
    private Button connect = new Button("connect");

    private Image backgroundImage = new Image("background.jpg");
    private Background background = new Background(new BackgroundFill(new ImagePattern(backgroundImage), CornerRadii.EMPTY, Insets.EMPTY));

    private Image buttonBackgroundImage = new Image("buttonBackground.jpg");
    private Background buttonBackground = new Background(new BackgroundFill(new ImagePattern(buttonBackgroundImage), CornerRadii.EMPTY, Insets.EMPTY));

    private Image healthBarImage = new Image("healthBar.jpg");
    private Background healthBarBackground = new Background(new BackgroundFill(Color.HONEYDEW, CornerRadii.EMPTY, Insets.EMPTY));

    private Image rockImage = new Image("rock.png");
    private Image paperImage = new Image("paper.png");
    private Image scissorsImage = new Image("scissors.png");
    private Image lizardImage = new Image("lizard.png");
    private Image spockImage = new Image("spock.png");
    private Image blankImage = new Image("blank.png");


    private ImageView rockIV = new ImageView(rockImage);
    private ImageView paperIV = new ImageView(paperImage);
    private ImageView scissorsIV = new ImageView(scissorsImage);
    private ImageView lizardIV = new ImageView(lizardImage);
    private ImageView spockIV = new ImageView(spockImage);

    private Button rockButton = new Button();
    private Button paperButton = new Button();
    private Button scissorsButton = new Button();
    private Button lizardButton = new Button();
    private Button spockButton = new Button();

    private Button playAgain = new Button("Play Again");
    private Button quit = new Button("Quit");
    private Button next = new Button("Next");

    private Button play = new Button("Play");
    private Label userChoiceDisplay = new Label();
    private Label oppChoiceDisplay = new Label();
    private String userChoice = "blank";
    private String oppChoice = "blank";

    private Image lifeImage = new Image("life.png");
    private Image deadImage = new Image("dead.png");

    private ImageView lifeIV = new ImageView(lifeImage);
    private ImageView deadIV = new ImageView(deadImage);

    private int userScore = 0;
    private int oppScore = 0;

    private Label uhealth1 = new Label();
    private Label uhealth2 = new Label();
    private Label uhealth3 = new Label();

    private Label ohealth1 = new Label();
    private Label ohealth2 = new Label();
    private Label ohealth3 = new Label();

    private HBox userHealthBar;
    private HBox oppHealthBar;
    private Label userScoreLabel = new Label("USER:");
    private Label oppScoreLabel = new Label("OPPONENT:");
    private BorderPane gamePane, startPane, waitingPane;
    private HBox playBox;

    private boolean madeChoice = false;
    private boolean isConnected = false;
    private ArrayList clientsConnected = new ArrayList<>();

    public ImageView getChoicePlayed(String s){
        ImageView c;
       if(s.equals("blank")){
            c = new ImageView(blankImage);
        }
        else if(s.equals("rock")){  c = new ImageView(rockImage); }
        else if(s.equals("paper")){  c = new ImageView(paperImage); }
        else if(s.equals("scissors")){  c = new ImageView(scissorsImage); }
        else if(s.equals("lizard")){ c = new ImageView(lizardImage); }
        else { c = new ImageView(spockImage); }

        c.setFitWidth(100);
        c.setFitHeight(100);
        c.setPreserveRatio(true);
        return c;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    private HBox getUserHealthBar(){
        HBox healthBar;
        ImageView life1 = new ImageView(lifeImage);
        ImageView life2 = new ImageView(lifeImage);
        ImageView life3 = new ImageView(lifeImage);

        ImageView dead1 = new ImageView(deadImage);
        ImageView dead2 = new ImageView(deadImage);
        ImageView dead3 = new ImageView(deadImage);

        life1.setFitHeight(30);
        life1.setFitWidth(30);
        life1.setPreserveRatio(true);

        dead1.setFitHeight(30);
        dead1.setFitWidth(30);
        dead1.setPreserveRatio(true);

        life2.setFitHeight(30);
        life2.setFitWidth(30);
        life2.setPreserveRatio(true);
        dead2.setFitHeight(30);
        dead2.setFitWidth(30);
        dead2.setPreserveRatio(true);

        life3.setFitHeight(30);
        life3.setFitWidth(30);
        life3.setPreserveRatio(true);
        dead3.setFitHeight(30);
        dead3.setFitWidth(30);
        dead3.setPreserveRatio(true);

        userScoreLabel.setWrapText(true);
        userScoreLabel.setPrefSize(80, 30);
        userScoreLabel.setAlignment(Pos.CENTER);
        userScoreLabel.setTextFill(Color.WHITE);

        uhealth1.setPrefSize(30, 30);
        uhealth2.setPrefSize(30, 30);
        uhealth3.setPrefSize(30, 30);

        if(userScore == 0) {
            uhealth1.setGraphic(dead1);
            uhealth2.setGraphic(dead2);
            uhealth3.setGraphic(dead3);
        }
        else if(userScore == 1) {
            uhealth1.setGraphic(life1);
            uhealth2.setGraphic(dead2);
            uhealth3.setGraphic(dead3);
        }
        else if(userScore == 2) {
            uhealth1.setGraphic(life1);
            uhealth2.setGraphic(life2);
            uhealth3.setGraphic(dead3);
        }
        else if(userScore == 3) {
            uhealth1.setGraphic(life1);
            uhealth2.setGraphic(life2);
            uhealth3.setGraphic(life3);
        }
        healthBar = new HBox(2, userScoreLabel, uhealth1, uhealth2, uhealth3);
        return healthBar;

    }

    private HBox getOppHealthBar(){
        HBox healthBar;
        ImageView olife1 = new ImageView(lifeImage);
        ImageView olife2 = new ImageView(lifeImage);
        ImageView olife3 = new ImageView(lifeImage);

        ImageView odead1 = new ImageView(deadImage);
        ImageView odead2 = new ImageView(deadImage);
        ImageView odead3 = new ImageView(deadImage);

        olife1.setFitHeight(30);
        olife1.setFitWidth(30);
        olife1.setPreserveRatio(true);
        odead1.setFitHeight(30);
        odead1.setFitWidth(30);
        odead1.setPreserveRatio(true);

        olife2.setFitHeight(30);
        olife2.setFitWidth(30);
        olife2.setPreserveRatio(true);
        odead2.setFitHeight(30);
        odead2.setFitWidth(30);
        odead2.setPreserveRatio(true);

        olife3.setFitHeight(30);
        olife3.setFitWidth(30);
        olife3.setPreserveRatio(true);
        odead3.setFitHeight(30);
        odead3.setFitWidth(30);
        odead3.setPreserveRatio(true);


        oppScoreLabel.setWrapText(true);
        oppScoreLabel.setPrefSize(80, 30);
        oppScoreLabel.setAlignment(Pos.CENTER);
        oppScoreLabel.setTextFill(Color.WHITE);

        ohealth1.setPrefSize(30, 30);
        ohealth2.setPrefSize(30, 30);
        ohealth3.setPrefSize(30, 30);

        if(oppScore == 0) {
            ohealth1.setGraphic(odead1);
            ohealth2.setGraphic(odead2);
            ohealth3.setGraphic(odead3);
        }
        else if(oppScore == 1) {
            ohealth1.setGraphic(olife1);
            ohealth2.setGraphic(odead2);
            ohealth3.setGraphic(odead3);
        }
        else if(oppScore == 2) {
            ohealth1.setGraphic(olife1);
            ohealth2.setGraphic(olife2);
            ohealth3.setGraphic(odead3);
        }
        else if(oppScore == 3) {
            ohealth1.setGraphic(olife1);
            ohealth2.setGraphic(olife2);
            ohealth3.setGraphic(olife3);
        }
        healthBar = new HBox(2, oppScoreLabel, ohealth1, ohealth2, ohealth3);
        return healthBar;
    }



    private Parent createPlayTableContent(){
        BorderPane playTable = new BorderPane();

        oppChoiceDisplay.setPrefSize(80, 80);
        oppChoiceDisplay.setAlignment(Pos.CENTER);
        oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
        userChoiceDisplay.setPrefSize(80, 80);
        userChoiceDisplay.setAlignment(Pos.CENTER);
        userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));

        playAgain.setDisable(true);
        playAgain.setPrefSize(100, 25);
        playAgain.setBackground(buttonBackground);
        playAgain.setTextFill(Color.WHITE);
        quit.setPrefSize(80, 25);
        quit.setDisable(true);
        quit.setTextFill(Color.WHITE);
        quit.setBackground(buttonBackground);

        HBox gameOptions = new HBox(10, playAgain, quit);
        gameOptions.setAlignment(Pos.CENTER);
        gameOptions.setPadding(new Insets(10));
        VBox centerBox = new VBox(10, this.oppChoiceDisplay, gameOptions, this.userChoiceDisplay);
        centerBox.setAlignment(Pos.CENTER);

        playTable.setCenter(centerBox);
        return playTable;
    }

    private Parent createGameContent() {
        gamePane = new BorderPane();
        gamePane.setBackground(background);
        messages = new Label("welcome :-)");
        messages.setPrefSize(700, 40);
        messages.setAlignment(Pos.CENTER);
        messages.setTextAlignment(TextAlignment.CENTER);
        messages.setPadding(new Insets(20));
        messages.setTextFill(Color.WHITE);

        play.setPrefSize(80, 45);
        play.setDisable(true);
        play.setAlignment(Pos.CENTER);
        play.setTextFill(Color.WHITE);
        play.setBackground(buttonBackground);

        next.setPrefSize(80, 45);
        next.setDisable(true);
        next.setAlignment(Pos.CENTER);
        next.setTextFill(Color.WHITE);
        next.setBackground(buttonBackground);

        playBox = new HBox(15, play, next);
        playBox.setAlignment(Pos.CENTER);

        userHealthBar = getUserHealthBar();
        userHealthBar.setAlignment(Pos.CENTER_RIGHT);
        oppHealthBar = getOppHealthBar();
        oppHealthBar.setAlignment(Pos.CENTER_LEFT);

        Background optBackground = new Background(new BackgroundFill(Color.TRANSPARENT,  CornerRadii.EMPTY, Insets.EMPTY));
        rockButton.setBackground(optBackground);
        paperButton.setBackground(optBackground);
        scissorsButton.setBackground(optBackground);
        lizardButton.setBackground(optBackground);
        spockButton.setBackground(optBackground);

        rockIV.setFitHeight(70);
        paperIV.setFitHeight(70);
        scissorsIV.setFitHeight(70);
        lizardIV.setFitHeight(70);
        spockIV.setFitHeight(70);

        rockIV.setFitWidth(70);
        paperIV.setFitWidth(70);
        scissorsIV.setFitWidth(70);
        lizardIV.setFitWidth(70);
        spockIV.setFitWidth(70);

        rockButton.setGraphic(rockIV);
        paperButton.setGraphic(paperIV);
        scissorsButton.setGraphic(scissorsIV);
        lizardButton.setGraphic(lizardIV);
        spockButton.setGraphic(spockIV);

        rockButton.setPrefSize(70, 70);
        paperButton.setPrefSize(70, 70);
        scissorsButton.setPrefSize(70, 70);
        lizardButton.setPrefSize(70, 70);
        spockButton.setPrefSize(70, 70);


        HBox options = new HBox(5, rockButton, paperButton, scissorsButton, lizardButton, spockButton);
        options.setAlignment(Pos.CENTER);

        VBox gamePaneBottom = new VBox(10, options, playBox);
        gamePaneBottom.setAlignment(Pos.CENTER);
        gamePane.setTop(this.messages);
        gamePane.setLeft(this.userHealthBar);
        gamePane.setRight(this.oppHealthBar);
        gamePane.setBottom(gamePaneBottom);
        gamePaneBottom.setPadding(new Insets(10));
        gamePaneBottom.setAlignment(Pos.TOP_CENTER);
        gamePane.setCenter(createPlayTableContent());

        return gamePane;

    }

    private Parent createStartContent() {
        startPane = new BorderPane();
        startPane.setBackground(background);
        Label title = new Label("ROCK ~ PAPER ~ SCISSORS ~ LIZARD ~ SPOCK");
        title.setTextFill(Color.WHITE);
        this.startMessages = new Label("welcome :-)");
        startMessages.setTextFill(Color.WHITE);
        title.setPrefSize(400, 30);
        startMessages.setPrefSize(100, 60);
        title.setAlignment(Pos.CENTER);
        startMessages.setAlignment(Pos.CENTER);
        startMessages.setWrapText(true);
        VBox paneTop = new VBox(10, title, startMessages);
        paneTop.setAlignment(Pos.CENTER);
        startPane.setTop(paneTop);

        ipInput.setPrefSize(150, 20);
        portInput.setPrefSize(150, 20);
        nameInput.setPrefSize(150, 20);
        ip = new Label ("      IP:");
        ip.setTextAlignment(TextAlignment.RIGHT);
        ip.setTextFill(Color.WHITE);
        ip.setPrefSize(50, 16);
        port = new Label("Port #:");
        port.setTextFill(Color.WHITE);
        port.setPrefSize(50, 16);
        name = new Label("Name: ");
        name.setTextFill(Color.WHITE);
        name.setPrefSize(50, 16);
        HBox ipBox = new HBox(ip, ipInput);
        ipBox.setAlignment(Pos.CENTER);
        HBox portBox = new HBox(port, portInput);
        portBox.setAlignment(Pos.CENTER);
        HBox nameBox = new HBox(name, nameInput);
        nameBox.setAlignment(Pos.CENTER);
        connect.setPrefSize(100, 40);
        connect.setAlignment(Pos.CENTER);
        connect.setTextFill(Color.WHITE);
        connect.setBackground(buttonBackground);
        VBox paneCenter = new VBox(30, ipBox, portBox, nameBox, connect);
        paneCenter.setAlignment(Pos.TOP_CENTER);
        startPane.setCenter(paneCenter);

        return startPane;
    }

    private Parent createWaitingContent(){
        //need list of all currently connected players
        waitingPane = new BorderPane();
        waitingPane.setBackground(background);

        return waitingPane;
    }
    public void disableOptions(){
        rockButton.setDisable(true);
        paperButton.setDisable(true);
        scissorsButton.setDisable(true);
        lizardButton.setDisable(true);
        spockButton.setDisable(true);
    }
    public void enableOptions(){
        rockButton.setDisable(false);
        paperButton.setDisable(false);
        scissorsButton.setDisable(false);
        lizardButton.setDisable(false);
        spockButton.setDisable(false);
    }

    @Override
    public void start(Stage primaryStage){
        // TODO Auto-generated method stub
        startScene = new Scene(createStartContent(), 400, 400);
        waitingScene = new Scene(createWaitingContent(), 400, 400);
        gameScene = new Scene(createGameContent(), 700, 500);
        primaryStage.setScene(startScene);

        rockButton.setOnAction(event->{
            enableOptions();
            rockButton.setDisable(true);
            this.userChoice = "rock";
            userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
            play.setDisable(false);
        });

        paperButton.setOnAction(event->{
            enableOptions();
            paperButton.setDisable(true);
            this.userChoice = "paper";
            userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
            play.setDisable(false);
        });
        scissorsButton.setOnAction(event->{
            enableOptions();
            scissorsButton.setDisable(true);
            this.userChoice = "scissors";
            userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
            play.setDisable(false);
        });
        lizardButton.setOnAction(event->{
            enableOptions();
            lizardButton.setDisable(true);
            this.userChoice = "lizard";
            userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
            play.setDisable(false);
        });
        spockButton.setOnAction(event->{
            enableOptions();
            spockButton.setDisable(true);
            this.userChoice = "spock";
            userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
            play.setDisable(false);
        });

        play.setOnAction(event->{
            disableOptions();
            play.setDisable(true);
            try{
                messages.setText("waiting for opponent's move...");
                conn.send(userChoice);
            }
            catch(Exception e){
                System.out.println("NO CONNECTION");
            }
        });

        next.setOnAction(event-> {
            enableOptions();
            oppChoice = "blank";
            oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
            userChoice = "blank";
            userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
            messages.setText("......");
            next.setDisable(true);
        });
        quit.setOnAction(event->{
            try {
                conn.send("quit");
                primaryStage.setScene(startScene);
                ip.setVisible(true);
                ipInput.setVisible(true);
                port.setVisible(true);
                portInput.setVisible(true);
                connect.setText("connect");
                connect.setPrefSize(100, 40);
                connect.setDisable(false);
                stop();
            } catch(Exception e){
                System.out.println("NO CONNECTION");
            }
            playAgain.setDisable(true);
            quit.setDisable(true);
        });
        playAgain.setOnAction(event-> {
            try {
                conn.send("playing again");
                oppScore = 0;
                userScore = 0;
                oppHealthBar = getOppHealthBar();
                userHealthBar = getUserHealthBar();
                userHealthBar.setAlignment(Pos.CENTER);
                oppHealthBar.setAlignment(Pos.CENTER);
                gamePane.setLeft(this.userHealthBar);
                gamePane.setRight(this.oppHealthBar);
                startMessages.setText("CONNECTED TO SERVER");
                messages.setText(".....");
                primaryStage.setScene(startScene);
            }
            catch(Exception e){
                System.out.println("NO CONNECTION");
            }
            playAgain.setDisable(true);
            quit.setDisable(true);

        });

        connect.setOnAction(event->{
            if(!ipInput.getText().isEmpty() && !portInput.getText().isEmpty()){
                try {
                    conn = createClient(ipInput.getText(), Integer.parseInt(portInput.getText()), nameInput.getText(), primaryStage);
                    Runnable task = () -> conn.clientConnect();
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
                    ipInput.clear();
                    ipInput.setVisible(false);
                    ip.setVisible(false);
                    port.setVisible(false);
                    portInput.clear();
                    portInput.setVisible(false);
                    name.setVisible(false);
                    nameInput.clear();
                    nameInput.setVisible(false);
                    connect.setDisable(true);
                    connect.setText("waiting for opponent");
                    connect.setPrefSize(300, 40);
                }
                catch(Exception e){
                    connect.setDisable(false);
                    ipInput.clear();
                    portInput.clear();
                    nameInput.clear();
                }
            }

        });

        primaryStage.show();

    }

    @Override
    public void stop() throws Exception{
        if(isConnected) {
            conn.send("disconnected");
            conn.closeConn();
        }
    }

    private Client createClient(String IP, int portIn, String username, Stage primaryStage) {
        return new Client(IP, portIn, username, data -> {
           Platform.runLater(()->{
               data.toString();
               if (data.toString().split(",")[0].equals("[NAMESLIST")){ //received list of connected clients
                   String[] clients = data.toString().split(","); //populate clientsConnected list
                   for (int i = 0; i < clients.length; i++){
                       clientsConnected.add(clients[i]);
                   }
               }

               switch (data.toString()) {
                   case "CONNECTED":
                       startMessages.setText("CONNECTED TO SERVER");
                       startMessages.setPrefSize(300, 40);
                       isConnected = true;
                       try{ conn.send("NAME: " + username); }
                       catch(Exception e){ System.out.println("Error in clientFX"); }
                       break;
                   case "NO CONNECTION":
                       isConnected = false;
                       ipInput.clear();
                       ipInput.setVisible(true);
                       ip.setVisible(true);
                       port.setVisible(true);
                       portInput.clear();
                       portInput.setVisible(true);
                       name.setVisible(true);
                       nameInput.clear();
                       nameInput.setVisible(true);
                       connect.setDisable(false);
                       connect.setText("connect");
                       connect.setPrefSize(100, 40);
                       startMessages.setText("NO CONNECTION");
                       startMessages.setPrefSize(300, 40);
                       primaryStage.setScene(startScene);
                       break;
                   case "closing":
                       break;
                   case "start":
                       messages.setText(".....");
                       enableOptions();
                       userScore = 0;
                       oppScore = 0;
                       userHealthBar = getUserHealthBar();
                       oppHealthBar = getOppHealthBar();
                       userHealthBar.setAlignment(Pos.CENTER);
                       oppHealthBar.setAlignment(Pos.CENTER);
                       gamePane.setLeft(this.userHealthBar);
                       gamePane.setRight(this.oppHealthBar);
                       oppChoice = "blank";
                       userChoice = "blank";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
                       primaryStage.setScene(gameScene);
                       break;
                   case "rock":
                       oppChoice = "rock";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       next.setDisable(false);
                       break;
                   case "paper":
                       oppChoice = "paper";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       next.setDisable(false);
                       break;
                   case "scissors":
                       oppChoice = "scissors";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       next.setDisable(false);
                       break;
                   case "lizard":
                       oppChoice = "lizard";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       next.setDisable(false);
                       break;
                   case "spock":
                       oppChoice = "spock";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       next.setDisable(false);
                       break;
                   case "winner":
                       messages.setText(userChoice + " wins :-)");
                       gamePane.setTop(messages);
                       userScore++;
                       userHealthBar = getUserHealthBar();
                       oppHealthBar = getOppHealthBar();
                       userHealthBar.setAlignment(Pos.CENTER);
                       oppHealthBar.setAlignment(Pos.CENTER);
                       gamePane.setLeft(this.userHealthBar);
                       gamePane.setRight(this.oppHealthBar);
                       next.setDisable(false);
                       break;
                   case "loser":
                       messages.setText(userChoice + " loses :-(");
                       gamePane.setTop(messages);
                       oppScore++;
                       oppHealthBar = getOppHealthBar();
                       userHealthBar = getUserHealthBar();
                       userHealthBar.setAlignment(Pos.CENTER);
                       oppHealthBar.setAlignment(Pos.CENTER);
                       gamePane.setLeft(this.userHealthBar);
                       gamePane.setRight(this.oppHealthBar);
                       next.setDisable(false);
                       break;
                   case "WIN":
                       playAgain.setDisable(false);
                       quit.setDisable(false);
                       disableOptions();
                       messages.setText("CONGRATS YOU WIN :-)");
                       gamePane.setTop(messages);
                       next.setDisable(true);
                       oppChoice = "blank";
                       userChoice = "blank";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
                       break;
                   case "LOSE":
                       playAgain.setDisable(false);
                       quit.setDisable(false);
                       disableOptions();
                       next.setDisable(true);
                       messages.setText("SORRY YOU LOSE :-(");
                       gamePane.setTop(messages);
                       oppChoice = "blank";
                       userChoice = "blank";
                       oppChoiceDisplay.setGraphic(getChoicePlayed(oppChoice));
                       userChoiceDisplay.setGraphic(getChoicePlayed(userChoice));
                       break;
                   case "playerDisconnected":
                       primaryStage.setScene(startScene);
                       messages.setText("PLAYER DISCONNECTED :-(");
                       gamePane.setTop(messages);
                       userScore = 0;
                       oppScore = 0;
                       break;
               }

           });
        });
    }
}
