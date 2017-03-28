package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.awt.SystemColor.text;

public class Main extends Application {

    public static String userDir = System.getProperty("user.home");
    //public static String dataSheetDir = "C:\\Users\\1153\\Documents\\Datasheets";
    public static String dataSheetDir = userDir + "\\Documents\\Datasheets";
    public Writer writer = null;
    public ArrayList<RobotData> robotList = new ArrayList<RobotData>();
    public List<AllianceData> adList = new ArrayList<AllianceData>();
    public List<Integer> teamList = new ArrayList<Integer>();

    public List<Text> availableTeamTextList = new ArrayList<Text>();
    public List<Text> pickedTeamTextList = new ArrayList<Text>();
    public List<Text> doNotPickTeamTextList = new ArrayList<Text>();
    public List<Text> allianceTeamTextList = new ArrayList<Text>();


    public Text strongestR1;
    public Text strongestR2;
    public Text strongestR3;
    public Text predictedScore;

    public Text avgTeleGearsText;
    public Text avgAutoGearsText;
    public Text avgTeleFuelText;
    public Text avgAutoFuelText;
    public Text avgClimbsText;

    public int avgAutoFuelX = 400;
    public int avgAutoFuelY = 550;
    public int avgTeleFuelX = 510;
    public int avgTeleFuelY = 550;
    public int avgAutoGearsX = 630;
    public int avgAutoGearsY = 550;
    public int avgTeleGearsX = 745;
    public int avgTeleGearsY = 550;
    public int avgClimbsX = 835;
    public int avgClimbsY = 550;

    public int strongestR1X = 17;
    public int strongestR1Y = 550;
    public int strongestR2X = 97;
    public int strongestR2Y = 550;
    public int strongestR3X = 177;
    public int strongestR3Y = 550;
    public int predictedScoreX = 275;
    public int predictedScoreY = 550;

    public int r1LabelX = 10;
    public int r1LabelY = 500;
    public int r2LabelX = 90;
    public int r2LabelY = 500;
    public int r3LabelX = 170;
    public int r3LabelY = 500;
    public int predictedScoreLabelX = 240;
    public int predictedScoreLabelY = 500;
    public int avgTeleFuelLabelX = 470;
    public int avgTeleFuelLabelY = 500;
    public int avgAutoFuelLabelX = 360;
    public int avgAutoFuelLabelY = 500;
    public int avgTeleGearsLabelX = 700;
    public int avgTeleGearsLabelY = 500;
    public int avgAutoGearsLabelX = 580;
    public int avgAutoGearsLabelY = 500;
    public int avgClimbsLabelX = 810;
    public int avgClimbsLabelY = 500;

    public int pickedTextX = 700;
    public int pickedTextY = 10;
    public int availableTextX = 0;
    public int availableTextY = 5;
    public int allianceTextX = 30;
    public int allianceTextY = 350;
    public int doNotPickTextX = 450;
    public int doNotPickTextY = 20;

    public int robot1TextX = 50;
    public int robot1TextY = 380;
    public int robot2TextX = 140;
    public int robot2TextY = 380;
    public int robot3TextX = 240;
    public int robot3TextY = 380;

    public int startX = 10;
    public int startY = 50;
    public int incrX = 50;
    public int incrY = 40;
    public int currX = startX;
    public int currY = startY;
    public int columns = 8;
    public int pickedTeamColumns = 4;
    public int doNotPickTeamColumns = 4;
    public int pickedTeamYIncr = 25;
    public int pickedTeamYOffset = 30;
    public int doNotPickTeamYIncr = 25;
    public int doNotPickTeamYOffset = 21;

    @Override
    public void start(Stage primaryStage) throws Exception{
        getDataFromDB();
        //System.out.println("start");
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Group root = new Group();
        primaryStage.setTitle("Alliance Selector");
        primaryStage.setScene(new Scene(root, 900, 650));

        int currNum = 1;

        final Text availableText = new Text (availableTextX+125,availableTextY+35,"Available Robots");
        availableText.setFill(Color.MEDIUMBLUE);
        availableText.setStyle("-fx-font: 20 cambria");
        final Text doNotPickText = new Text (doNotPickTextX+50,doNotPickTextY+20,"Do Not Pick");
        doNotPickText.setFill(Color.RED);
        doNotPickText.setStyle("-fx-font: 20 cambria");
        final Text pickedText = new Text (pickedTextX+50,pickedTextY+30,"Robot Picked");
        pickedText.setFill(Color.MEDIUMBLUE);
        pickedText.setStyle("-fx-font: 20 cambria");

        final Text robot1Text = new Text (robot1TextX,robot1TextY,"Robot 1");
        robot1Text.setFill(Color.RED);
        robot1Text.setStyle("-fx-font: 18 cambria");
        final Text robot2Text = new Text (robot2TextX+10,robot2TextY,"Robot 2");
        robot2Text.setFill(Color.RED);
        robot2Text.setStyle("-fx-font: 18 cambria");
        final Text robot3Text = new Text (robot3TextX+10,robot3TextY,"Robot 3");
        robot3Text.setFill(Color.RED);
        robot3Text.setStyle("-fx-font: 18 cambria");
        strongestR1 = new Text(strongestR1X,strongestR1Y,"");
        strongestR1.setFill(Color.RED);
        strongestR1.setStyle("-fx-font: 16 cambria");
        strongestR2 = new Text(strongestR2X,strongestR2Y,"");
        strongestR2.setFill(Color.RED);
        strongestR2.setStyle("-fx-font: 16 cambria");
        strongestR3 = new Text(strongestR3X,strongestR3Y,"");
        strongestR3.setFill(Color.RED);
        strongestR3.setStyle("-fx-font: 16 cambria");
        predictedScore = new Text(predictedScoreX,predictedScoreY,"");
        predictedScore.setFill(Color.RED);
        predictedScore.setStyle("-fx-font: 16 cambria");

        avgTeleFuelText = new Text (avgTeleFuelX,avgTeleFuelY, "");
        avgTeleFuelText.setFill(Color.RED);
        avgTeleFuelText.setStyle("-fx-font: 16 cambria");
        avgAutoFuelText = new Text (avgAutoFuelX,avgAutoFuelY, "");
        avgAutoFuelText.setFill(Color.RED);
        avgAutoFuelText.setStyle("-fx-font: 16 cambria");
        avgTeleGearsText = new Text (avgTeleGearsX,avgTeleGearsY, "");
        avgTeleGearsText.setFill(Color.RED);
        avgTeleGearsText.setStyle("-fx-font: 16 cambria");
        avgAutoGearsText = new Text (avgAutoGearsX,avgAutoGearsY, "") ;
        avgAutoGearsText.setFill(Color.RED);
        avgAutoGearsText.setStyle("-fx-font: 16 cambria");
        avgClimbsText = new Text (avgClimbsX,avgClimbsY, "");
        avgClimbsText.setFill(Color.RED);
        avgClimbsText.setStyle("-fx-font: 16 cambria");
        root.getChildren().add(avgTeleFuelText);
        root.getChildren().add(avgAutoFuelText);
        root.getChildren().add(avgTeleGearsText);
        root.getChildren().add(avgAutoGearsText);
        root.getChildren().add(avgClimbsText);

        Text r1Label = new Text(r1LabelX, r1LabelY, "Robot 1");
        r1Label.setFill(Color.MEDIUMBLUE);
        r1Label.setStyle("-fx-font: 16 cambria");
        Text r2Label = new Text (r2LabelX, r2LabelY, "Robot 2");
        r2Label.setFill(Color.MEDIUMBLUE);
        r2Label.setStyle("-fx-font: 16 cambria");
        Text r3Label = new Text (r3LabelX, r3LabelY, "Robot 3");
        r3Label.setFill(Color.MEDIUMBLUE);
        r3Label.setStyle("-fx-font: 16 cambria");
        Text predictedScoreLabel = new Text (predictedScoreLabelX, predictedScoreLabelY, "Predicted Score");
        predictedScoreLabel.setFill(Color.MEDIUMBLUE);
        predictedScoreLabel.setStyle("-fx-font: 16 cambria");
        Text avgTeleFuelLabel = new Text (avgTeleFuelLabelX, avgTeleFuelLabelY, "Avg. Tele Fuel");
        avgTeleFuelLabel.setFill(Color.MEDIUMBLUE);
        avgTeleFuelLabel.setStyle("-fx-font: 16 cambria");
        Text avgAutoFuelLabel = new Text (avgAutoFuelLabelX, avgAutoFuelLabelY, "Avg. Auto Fuel");
        avgAutoFuelLabel.setFill(Color.MEDIUMBLUE);
        avgAutoFuelLabel.setStyle("-fx-font: 16 cambria");
        Text avgAutoGearsLabel = new Text (avgAutoGearsLabelX, avgAutoGearsLabelY, "Avg. Auto Gears");
        avgAutoGearsLabel.setFill(Color.MEDIUMBLUE);
        avgAutoGearsLabel.setStyle("-fx-font: 16 cambria");
        Text avgTeleGearsLabel = new Text (avgTeleGearsLabelX, avgTeleGearsLabelY, "Avg. Tele Gears");
        avgTeleGearsLabel.setFill(Color.MEDIUMBLUE);
        avgTeleGearsLabel.setStyle("-fx-font: 16 cambria");
        Text avgClimbsLabel = new Text (avgClimbsLabelX, avgClimbsLabelY, "Avg Climbs");
        avgClimbsLabel.setFill(Color.MEDIUMBLUE);
        avgClimbsLabel.setStyle("-fx-font: 16 cambria");

        root.getChildren().add(r1Label);
        root.getChildren().add(r2Label);
        root.getChildren().add(r3Label);
        root.getChildren().add(predictedScoreLabel);
        root.getChildren().add(avgTeleFuelLabel);
        root.getChildren().add(avgAutoFuelLabel);
        root.getChildren().add(avgAutoGearsLabel);
        root.getChildren().add(avgTeleGearsLabel);
        root.getChildren().add(avgClimbsLabel);
        root.getChildren().add(strongestR1);
        root.getChildren().add(strongestR2);
        root.getChildren().add(strongestR3);
        root.getChildren().add(predictedScore);
        root.getChildren().add(availableText);

        getStrongestAlliance();

        root.getChildren().add(robot1Text);
        root.getChildren().add(robot2Text);
        root.getChildren().add(robot3Text);

        root.getChildren().add(doNotPickText);
        root.getChildren().add(pickedText);

        //final Text availableTarget = new Text(availableTextX, availableTextY+30, "Robot available: DROP HERE");
        //final Text pickedTarget = new Text(pickedTextX, pickedTextY+30, "Robot picked:DROP HERE");
        final Rectangle availableRect = new Rectangle(availableTextX, availableTextY+15, 400, 300);
        availableRect.setFill(null);
        availableRect.setStroke(Color.MEDIUMBLUE);
        availableRect.toBack();
        root.getChildren().add(availableRect);

        final TextFlow availableTarget = new TextFlow(
        );
        availableTarget.setLayoutX(availableTextX);
        availableTarget.setLayoutY(availableTextY+15);
        availableTarget.setPrefSize(400,300);
        availableTarget.toBack();

        final Rectangle pickedRect = new Rectangle(pickedTextX, pickedTextY+10, 200, 300);
        pickedRect.setFill(null);
        pickedRect.setStroke(Color.MEDIUMBLUE);
        pickedRect.toBack();
        root.getChildren().add(pickedRect);

         final TextFlow pickedTarget = new TextFlow(
        );
        pickedTarget.setLayoutX(pickedTextX);
        pickedTarget.setLayoutY(pickedTextY+10);
        pickedTarget.setPrefSize(200,300);
        pickedTarget.toBack();

        final Text allianceTargetText = new Text(allianceTextX, allianceTextY+15,"Add to Alliance");

        final Rectangle allianceRect = new Rectangle(allianceTextX, allianceTextY, 300, 100);
        allianceRect.setFill(null);
        allianceRect.setStroke(Color.RED);
        allianceRect.toBack();
        root.getChildren().add(allianceRect);

        final TextFlow allianceTarget = new TextFlow(
        );
        allianceTarget.setLayoutX(allianceTextX);
        allianceTarget.setLayoutY(allianceTextY);
        allianceTarget.setPrefSize(300,100);
        allianceTarget.toBack();

        final Rectangle doNotPickRect = new Rectangle(doNotPickTextX, doNotPickTextY, 200, 300);
        doNotPickRect.setFill(null);
        doNotPickRect.setStroke(Color.RED);
        doNotPickRect.toBack();
        root.getChildren().add(doNotPickRect);

        final TextFlow doNotPickTarget = new TextFlow(
        );
        doNotPickTarget.setLayoutX(doNotPickTextX);
        doNotPickTarget.setLayoutY(doNotPickTextY);
        doNotPickTarget.setPrefSize(200,300);
        doNotPickTarget.toBack();
        //System.out.println("Have " + teamList.size() + " robots");

        Collections.sort(teamList);

        // create text for each team
        for (int teamNum : teamList) {
            //System.out.println(teamNum);
            Text teamText = new Text(0,0,Integer.toString(teamNum));
            teamText.setStyle("-fx-font: 18 cambria");
            if (teamNum == 1153) {
                teamText.setFill(Color.RED);
            }
            // create an event handler to handle drag and drop
            teamText.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                /* drag detected */
                    Dragboard db = teamText.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(teamText.getText());
                    db.setContent(content);

                    event.consume();
                }
            });
            teamText.setOnDragDone(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        //teamText.setText("");
                        teamText.toFront();
                    }
                    event.consume();
                }
            });
            availableTeamTextList.add(teamText);
            root.getChildren().add(teamText);
        }
        placeAvailableTeams();
        ///////////////////////////////////////////////////////////////////////
        // Handle pickedTarget
        ///////////////////////////////////////////////////////////////////////
        // create an event handler for the target
        pickedTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedOver");
                if (event.getGestureSource() != pickedTarget &&
                        event.getDragboard().hasString() && isInPickedList(event.getDragboard().getString()) == false) {
                    /* allow both copy and move */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
            }
        });

        pickedTarget.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedEntered");
                if (event.getGestureSource() != pickedTarget &&
                        event.getDragboard().hasString()) {
                    //pickedTarget.setFill(Color.GREEN);
                }
            }
        });

        pickedTarget.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedExited");
                //pickedTarget.setFill(Color.BLACK);
            }
        });

        pickedTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedDropped");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //target.setText(db.getString());

                    Text t = getTextObject(db.getString());

                    if (isInAvailableList(t.getText())) {
                        availableTeamTextList.remove(t);

                    } else if (isInAllianceList(t.getText())) {
                        allianceTeamTextList.remove(t);
                    }
                    pickedTeamTextList.add(t);
                    placePickedTeams();
                    getStrongestAlliance();
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });
        ///////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////
        // Handle availableTarget
        ///////////////////////////////////////////////////////////////////////
        // create an event handler for the target
        availableTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != availableTarget &&
                        event.getDragboard().hasString() && isInAvailableList(event.getDragboard().getString()) == false) {
                    /* allow both copy and move */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
            }
        });

        availableTarget.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != availableTarget &&
                        event.getDragboard().hasString()) {
                    //availableTarget.setFill(Color.GREEN);
                }
            }
        });

        availableTarget.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /*availableTarget.setFill(Color.BLACK);*/
            }
        });

        availableTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {

                    Text t = getTextObject(db.getString());
                    String tempString = db.getString();
                    if (isInPickedList(t.getText())) {
                        pickedTeamTextList.remove(t);
                        placePickedTeams();
                    } else if (isInAllianceList(t.getText())) {
                        allianceTeamTextList.remove(t);
                        placeAllianceTeams();
                    }

                    if (isInDoNotPickList(t.getText())) {
                        doNotPickTeamTextList.remove(t);
                        placeDoNotPickTeams();
                    }
                    availableTeamTextList.add(t);
                    getStrongestAlliance();
                    placeAvailableTeams();
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });
        doNotPickTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedOver");
                if (event.getGestureSource() != doNotPickTarget &&
                        event.getDragboard().hasString() && isInDoNotPickList(event.getDragboard().getString()) == false) {
                    /* allow both copy and move */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
            }
        });

        doNotPickTarget.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedEntered");
                if (event.getGestureSource() != doNotPickTarget &&
                        event.getDragboard().hasString()) {
                    //pickedTarget.setFill(Color.GREEN);
                }
            }
        });

        doNotPickTarget.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedExited");
                //pickedTarget.setFill(Color.BLACK);
            }
        });

        doNotPickTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //System.out.println("dragedDropped");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //target.setText(db.getString());

                    // move target text below here
                    Text t = getTextObject(db.getString());

                    if (isInAvailableList(t.getText())) {
                        availableTeamTextList.remove(t);

                    } else if (isInAllianceList(t.getText())) {
                        allianceTeamTextList.remove(t);
                    }
                    doNotPickTeamTextList.add(t);
                    placeDoNotPickTeams();
                    getStrongestAlliance();
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });
        ///////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////
        // Handle allianceTarget
        ///////////////////////////////////////////////////////////////////////
        // create an event handler for the target
        allianceTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != allianceTarget &&
                        event.getDragboard().hasString() && isInAllianceList(event.getDragboard().getString()) == false) {
                    /* allow both copy and move */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
            }
        });

        allianceTarget.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != allianceTarget &&
                        event.getDragboard().hasString()) {
                    //allianceTarget.setFill(Color.GREEN);
                }
            }
        });

        allianceTarget.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //allianceTarget.setFill(Color.BLACK);
            }
        });

        allianceTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {

                    Text t = getTextObject(db.getString());

                    if (isInAvailableList(t.getText())) {
                        availableTeamTextList.remove(t);

                    } else if (isInPickedList(t.getText())) {
                        pickedTeamTextList.remove(t);
                    }
                    allianceTeamTextList.add(t);
                    placeAllianceTeams();
                    getStrongestAlliance();
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });
        ///////////////////////////////////////////////////////////////////////

        root.getChildren().add(allianceTarget);
        root.getChildren().add(availableTarget);
        root.getChildren().add(doNotPickTarget);
        // put the target button in the back of the layout stack
        pickedTarget.toBack();
        doNotPickTarget.toBack();
        availableTarget.toBack();
        root.getChildren().add(pickedTarget);
        primaryStage.show();
    }

    // display strongest alliance
    public void getStrongestAlliance() {
        // first sort alliances by strength
        Collections.sort(adList, new Comparator<AllianceData>() {
            public int compare(AllianceData o1, AllianceData o2) {
                if (o1.allianceStrength == o2.allianceStrength)
                    return 0;
                return o1.allianceStrength > o2.allianceStrength ? -1 : 1;
            }
        });

        int r1 = 0;
        int r2 = 0;
        int r3 = 0;
        double strength = 0.0;
        double avgAutoFuel = 0;
        double avgTeleFuel = 0;
        double avgAutoGears = 0;
        double avgTeleGears = 0;
        double avgClimbs = 0;
        // if no picked alliance members, then just use strongest alliance
        if (allianceTeamTextList.size() == 0) {
            int c =0;
            boolean keepSearching = true;
            while (keepSearching == true) {
                // make sure robots are not in the unavailable list
                if (!isInPickedList(Integer.toString(adList.get(c).robot1)) &&
                        !isInPickedList(Integer.toString(adList.get(c).robot2)) &&
                        !isInPickedList(Integer.toString(adList.get(c).robot3)) &&
                        !isInDoNotPickList(Integer.toString(adList.get(c).robot1)) &&
                        !isInDoNotPickList(Integer.toString(adList.get(c).robot2)) &&
                        !isInDoNotPickList(Integer.toString(adList.get(c).robot3))){
                    keepSearching = false;
                    r1 = adList.get(c).robot1;
                    r2 = adList.get(c).robot2;
                    r3 = adList.get(c).robot3;

                    strength = adList.get(c).allianceStrength;
                    avgAutoFuel = adList.get(c).avgAutoFuel;
                    avgTeleFuel = adList.get(c).avgTeleFuel;
                    avgAutoGears = adList.get(c).avgAutoGear;
                    avgTeleGears = adList.get(c).avgTeleGear;
                    avgClimbs = adList.get(c).avgTeleClimb;
                }
                c++;
                if (c >= adList.size()) {
                    keepSearching = false;
                }
            }
        } else if (allianceTeamTextList.size() == 1) {
            r1 = Integer.parseInt(allianceTeamTextList.get(0).getText().toString());
            // find highest matching alliance with robot 1 in it
            int c =0;
            boolean keepSearching = true;
            while (keepSearching == true) {
                if (adList.get(c).robot1 == r1 || adList.get(c).robot2 == r1 || adList.get(c).robot3 == r1) {
                    // make sure robot2 and robot3 are not in the unavailable list
                    if (!isInPickedList(Integer.toString(adList.get(c).robot1)) &&
                            !isInPickedList(Integer.toString(adList.get(c).robot2)) &&
                            !isInPickedList(Integer.toString(adList.get(c).robot3)) &&
                            !isInDoNotPickList(Integer.toString(adList.get(c).robot1)) &&
                            !isInDoNotPickList(Integer.toString(adList.get(c).robot2)) &&
                            !isInDoNotPickList(Integer.toString(adList.get(c).robot3))){
                        keepSearching = false;
                        // set all of the robot numbers
                        r1 = adList.get(c).robot1;
                        r2 = adList.get(c).robot2;
                        r3 = adList.get(c).robot3;
                        strength = adList.get(c).allianceStrength;
                        avgAutoFuel = adList.get(c).avgAutoFuel;
                        avgTeleFuel = adList.get(c).avgTeleFuel;
                        avgAutoGears = adList.get(c).avgAutoGear;
                        avgTeleGears = adList.get(c).avgTeleGear;
                        avgClimbs = adList.get(c).avgTeleClimb;
                    }
                }
                c++;
                if (c >= adList.size()) {
                    keepSearching = false;
                }
            }
        } else if (allianceTeamTextList.size() == 2) {
            // find highest matching alliance with robots 1 and 2 in it
            r1 = Integer.parseInt(allianceTeamTextList.get(0).getText().toString());
            r2 = Integer.parseInt(allianceTeamTextList.get(1).getText().toString());
            // find highest matching alliance with robot 1 in it
            int c =0;
            boolean keepSearching = true;
            while (keepSearching == true) {
                if ((adList.get(c).robot1 == r1 || adList.get(c).robot2 == r1 || adList.get(c).robot3 == r1) &&
                        (adList.get(c).robot1 == r2 || adList.get(c).robot2 == r2 || adList.get(c).robot3 == r2)) {

                    // make sure r3 is available
                    if (!isInPickedList(Integer.toString(adList.get(c).robot1)) &&
                            !isInPickedList(Integer.toString(adList.get(c).robot2)) &&
                            !isInPickedList(Integer.toString(adList.get(c).robot3)) &&
                            !isInDoNotPickList(Integer.toString(adList.get(c).robot1)) &&
                            !isInDoNotPickList(Integer.toString(adList.get(c).robot2)) &&
                            !isInDoNotPickList(Integer.toString(adList.get(c).robot3))){
                        keepSearching = false;
                        // set all of the robot numbers
                        r1 = adList.get(c).robot1;
                        r2 = adList.get(c).robot2;
                        r3 = adList.get(c).robot3;
                        strength = adList.get(c).allianceStrength;
                        avgAutoFuel = adList.get(c).avgAutoFuel;
                        avgTeleFuel = adList.get(c).avgTeleFuel;
                        avgAutoGears = adList.get(c).avgAutoGear;
                        avgTeleGears = adList.get(c).avgTeleGear;
                        avgClimbs = adList.get(c).avgTeleClimb;
                    }
                }
                c++;
                if (c >= adList.size()) {
                    keepSearching = false;
                }
            }
        } else {
            // find highest matching alliance with all 3 robots in it
            // find highest matching alliance with robots 1 and 2 in it
            r1 = Integer.parseInt(allianceTeamTextList.get(0).getText().toString());
            r2 = Integer.parseInt(allianceTeamTextList.get(1).getText().toString());
            r3 = Integer.parseInt(allianceTeamTextList.get(2).getText().toString());

            // find highest matching alliance with robot 1 in it
            int c =0;
            boolean keepSearching = true;
            while (keepSearching == true) {
                if ((adList.get(c).robot1 == r1 || adList.get(c).robot2 == r1 || adList.get(c).robot3 == r1) &&
                        (adList.get(c).robot1 == r2 || adList.get(c).robot2 == r2 || adList.get(c).robot3 == r2) &&
                        (adList.get(c).robot1 == r3 || adList.get(c).robot2 == r3 || adList.get(c).robot3 == r3)) {
                    keepSearching = false;
                    // set all of the robot numbers
                    r1 = adList.get(c).robot1;
                    r2 = adList.get(c).robot2;
                    r3 = adList.get(c).robot3;
                    strength = adList.get(c).allianceStrength;
                    avgAutoFuel = adList.get(c).avgAutoFuel;
                    avgTeleFuel = adList.get(c).avgTeleFuel;
                    avgAutoGears = adList.get(c).avgAutoGear;
                    avgTeleGears = adList.get(c).avgTeleGear;
                    avgClimbs = adList.get(c).avgTeleClimb;

                }
                c++;
                if (c >= adList.size()) {
                    keepSearching = false;
                }
            }
        }
        strongestR1.setText(Integer.toString(r1));
        strongestR2.setText(Integer.toString(r2));
        strongestR3.setText(Integer.toString(r3));
        predictedScore.setText(String.format("%.1f",strength));
        avgAutoFuelText.setText(String.format("%.1f",avgAutoFuel));
        avgTeleFuelText.setText(String.format("%.1f", avgTeleFuel));
        avgAutoGearsText.setText(String.format("%.1f", avgAutoGears));
        avgTeleGearsText.setText(String.format("%.1f", avgTeleGears));
        avgClimbsText.setText(String.format("%.1f", avgClimbs));
    }

    // loop through available team list and place teams
    public void placeAvailableTeams() {
        int currNum = 0;
        currY = startY;
        //System.out.println("size of available team list = " + availableTeamTextList.size());
        Collections.sort(availableTeamTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        });
        for (Text t : availableTeamTextList) {
            currX = (currNum % columns)*incrX + startX;
            if (currNum % columns == 0) {
                currX = startX;
                currY += incrY;
            }
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }

    public void placeAllianceTeams() {
        int currNum = 0;
        int columns = 3;
        currY = robot1TextY+30;
        //System.out.println("size of available team list = " + availableTeamTextList.size());
        Collections.sort(allianceTeamTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        });
        for (Text t : allianceTeamTextList) {
            currX = (currNum % columns)*(incrX+55) + robot1TextX;
            if (currNum % columns == 3) {
                currX = robot1TextX;
                currY += incrY;
            }
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }
    // loop through available team list and place teams
    public void placePickedTeams() {
        int currNum = 0;
        currY = pickedTextY + pickedTeamYOffset;
        //System.out.println("size of picked team list = " + pickedTeamTextList.size());
        Collections.sort(pickedTeamTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        });
        for (Text t : pickedTeamTextList) {
            currX = (currNum % pickedTeamColumns)*incrX + pickedTextX;
            if (currNum % pickedTeamColumns == 0) {
                currX = pickedTextX;
                currY += incrY;
            }
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }

    public void placeDoNotPickTeams() {
        int currNum = 0;
        currY = doNotPickTextY + doNotPickTeamYOffset;
        //System.out.println("size of picked team list = " + pickedTeamTextList.size());
        Collections.sort(doNotPickTeamTextList, new Comparator<Text>() {
            public int compare(Text o1, Text o2) {
                if (Integer.parseInt(o1.getText()) == Integer.parseInt(o2.getText()))
                    return 0;
                return  Integer.parseInt(o1.getText())> Integer.parseInt(o2.getText()) ? 1 : -1;
            }
        });
        for (Text t : doNotPickTeamTextList) {
            currX = (currNum % doNotPickTeamColumns)*incrX + doNotPickTextX;
            if (currNum % doNotPickTeamColumns == 0) {
                currX = doNotPickTextX;
                currY += incrY;
            }
            t.setX(currX);
            t.setY(currY);
            currNum++;
        }
    }
    // given a robot number string, return the text
    public Text getTextObject (String robotNumber) {
        // check all three lists
        for (Text t : availableTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }
        // check all three lists
        for (Text t : pickedTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }

        for (Text t : doNotPickTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }
        // check all three lists
        for (Text t : allianceTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return t;
            }
        }
        return null;
    }

    public boolean isInPickedList (String robotNumberString) {
        // check all three lists
        // check all three lists
        for (Text t : pickedTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumberString)) {
                return true;
            }
        }

        return false;
    }
    public boolean isInDoNotPickList (String robotNumberString) {
        // check all three lists
        // check all three lists
        for (Text t : doNotPickTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumberString)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInAvailableList (String robotNumberString) {
        // check all three lists
        // check all three lists
        for (Text t : availableTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumberString)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInAllianceList (String robotNumber) {
        // check all three lists
        // check all three lists
        for (Text t : allianceTeamTextList) {
            if (t.getText().toString().equalsIgnoreCase(robotNumber)) {
                return true;
            }
        }

        return false;
    }

    // check the robot list to see if we have a robot already with the given number
    public boolean haveRobot(int robotNumber) {
        for (RobotData r : robotList) {
            if (r.robotNumber == robotNumber) {
                return true;
            }
        }
        return false;
    }

    // get the robot with the given robotNumber from the list
    // or return null
    public RobotData getRobot(int robotNumber) {

        for (RobotData r : robotList) {
            if (r.robotNumber == robotNumber) {
                return r;
            }
        }
        return null;
    }

    public void getRanks() {
        // rank the robots based on average alliance score
        ArrayList<RobotData> rankList = new ArrayList<RobotData>();
        for (RobotData r : robotList) {
            rankList.add(r);
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.lowShots.avg == o2.lowShots.avg)
                    return 0;
                return o1.lowShots.avg > o2.lowShots.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoLowShots.rank;
                if (getRobot(rankList.get(c).robotNumber).autoLowShots.avg < getRobot(rankList.get(c-1).robotNumber).autoLowShots.avg) {
                    getRobot(rankList.get(c).robotNumber).autoLowShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoLowShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoLowShots.rank = 1;
            }
        }

        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoHighShots.rank;
                if (getRobot(rankList.get(c).robotNumber).autoHighShots.avg < getRobot(rankList.get(c-1).robotNumber).autoHighShots.avg) {
                    getRobot(rankList.get(c).robotNumber).autoHighShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoHighShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoHighShots.rank = 1;
            }
        }

        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).lowShots.rank;
                if (getRobot(rankList.get(c).robotNumber).lowShots.avg < getRobot(rankList.get(c-1).robotNumber).lowShots.avg) {
                    getRobot(rankList.get(c).robotNumber).lowShots.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).lowShots.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).lowShots.rank = 1;
            }
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.highAttempt.avg == o2.highAttempt.avg)
                    return 0;
                return o1.highAttempt.avg > o2.highAttempt.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).highAttempt.rank;
                if (getRobot(rankList.get(c).robotNumber).highAttempt.avg < getRobot(rankList.get(c-1).robotNumber).highAttempt.avg) {
                    getRobot(rankList.get(c).robotNumber).highAttempt.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).highAttempt.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).highAttempt.rank = 1;
            }
        }
        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.autoCross.avg == o2.autoCross.avg)
                    return 0;
                return o1.autoCross.avg > o2.autoCross.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            getRobot(rankList.get(c).robotNumber).autoCross.rank = c + 1;
        }
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoCross.rank;
                if (getRobot(rankList.get(c).robotNumber).autoCross.avg < getRobot(rankList.get(c-1).robotNumber).autoCross.avg) {
                    getRobot(rankList.get(c).robotNumber).autoCross.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoCross.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoCross.rank = 1;
            }
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.autoGears.avg == o2.autoGears.avg)
                    return 0;
                return o1.autoGears.avg > o2.autoGears.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).autoGears.rank;
                if (getRobot(rankList.get(c).robotNumber).autoGears.avg < getRobot(rankList.get(c-1).robotNumber).autoGears.avg) {
                    getRobot(rankList.get(c).robotNumber).autoGears.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).autoGears.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).autoGears.rank = 1;
            }
        }
        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.gears.avg == o2.gears.avg)
                    return 0;
                return o1.gears.avg > o2.gears.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).gears.rank;
                if (getRobot(rankList.get(c).robotNumber).gears.avg < getRobot(rankList.get(c-1).robotNumber).gears.avg) {
                    getRobot(rankList.get(c).robotNumber).gears.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).gears.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).gears.rank = 1;
            }
        }

        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.climb.avg == o2.climb.avg)
                    return 0;
                return o1.climb.avg > o2.climb.avg ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            if (c > 0) {
                int prev_rank = getRobot(rankList.get(c-1).robotNumber).climb.rank;
                if (getRobot(rankList.get(c).robotNumber).climb.avg < getRobot(rankList.get(c-1).robotNumber).climb.avg) {
                    getRobot(rankList.get(c).robotNumber).climb.rank = prev_rank + 1;
                } else {
                    getRobot(rankList.get(c).robotNumber).climb.rank = prev_rank;
                }
            } else {
                getRobot(rankList.get(c).robotNumber).climb.rank = 1;
            }
        }
        Collections.sort(rankList, new Comparator<RobotData>() {
            public int compare(RobotData o1, RobotData o2) {
                if (o1.accuracy == o2.accuracy)
                    return 0;
                return o1.accuracy > o2.accuracy ? -1 : 1;
            }
        });
        // now loop through the lists and set the rank based on avg score
        for (int c = 0; c < rankList.size(); c++) {
            getRobot(rankList.get(c).robotNumber).accuracyRank = c + 1;
        }
    }

    public void getDataFromDB() {

        // make directory if not found
        File dataSheetDirFile = new File(dataSheetDir);
        if (dataSheetDirFile.exists() == false) {
            dataSheetDirFile.mkdir();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("My SQL JDBC Driver Not Registered?");
            e.printStackTrace();
            return;
        }
        System.out.println("Getting Data from SQL Database");

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roborebels", "root", "roborebels1153");
            Statement stmt;
            ResultSet rs;

            //create hashmap of data
            stmt = conn.createStatement();

            //get match table, then create robots
            rs = stmt.executeQuery("SELECT * from matchdata");

            //process Data
            RobotData rd;
            while (rs.next()) {
                int rn = rs.getInt("RobotNumber");
                String matchName = rs.getString("matchNumber");
                Integer matchNumber = Integer.parseInt(matchName);

                if (haveRobot(rn)) {
                    // check to see if we have this match number already, if not, add it to the list and increase
                    // number of matches
                    if (getRobot(rn).matchList.contains(matchNumber) == false) {
                        getRobot(rn).matches++;
                        getRobot(rn).matchList.add(matchNumber);
                    }
                } else {
                    rd = new RobotData();
                    rd.robotNumber = rn;
                    rd.matches = 1;
                    rd.matchList.add(matchNumber);
                    robotList.add(rd);

                }
            }
            rs.close();
            rs = stmt.executeQuery("SELECT * from matchdata");
            while (rs.next()) {
                int rn = rs.getInt("RobotNumber");
                if (haveRobot(rn)) {
                    String gameEvent = rs.getString("gameEvent");
                    if (gameEvent.equals("crossBaselineAuto")){getRobot(rn).autoCross.total++;}
                    if (gameEvent.equals("climbed")){getRobot(rn).climb.total++;}
                    if (gameEvent.equals("gearPlacedAuto")){getRobot(rn).autoGears.total++;}
                    if (gameEvent.equals("gearPlacedTeleop")){getRobot(rn).teleGears.total++;}
                    if (gameEvent.equals("lowGoal")){getRobot(rn).lowShots.total++;}
                    if (gameEvent.equals("highGoal")){getRobot(rn).highAttempt.total++;}
                    if (gameEvent.equals("lowGoalAuto")){getRobot(rn).autoLowShots.total++;}
                    if (gameEvent.equals("highGoalAuto")){getRobot(rn).autoHighShots.total++;}
                    if (gameEvent.equals("gearPlacedTeleop") || gameEvent.equals("gearPlacedAuto")){getRobot(rn).gears.total++;}

                }
            }
            rs.close();
            stmt.close();

            //averages
            for (RobotData r : robotList) {
                r.autoLowShots.avg = (double) r.autoLowShots.total / r.matches;
                r.autoHighShots.avg = (double) r.autoHighShots.total / r.matches;
                r.autoCross.avg = (double) r.autoCross.total / r.matches;
                r.autoGears.avg = (double) r.autoGears.total / r.matches;
                r.teleGears.avg = (double) r.teleGears.total / r.matches;
                r.climb.avg = (double) r.climb.total / r.matches;
                r.gears.avg = (double) r.gears.total / r.matches;
                r.highAttempt.avg = (double) r.highAttempt.total / r.matches;
                r.lowShots.avg = (double) r.lowShots.total / r.matches;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        getRanks();



        for (RobotData r : robotList) {
            teamList.add(r.robotNumber);
        }

        //System.out.println("Got " + teamList.size() + " robots");

        for (Integer t1 : teamList) {
            for (Integer t2 : teamList) {
                for (Integer t3 : teamList) {
                    if (t1 != t3 && t3 != t2 && t1 != t2) {
                        AllianceData ad = new AllianceData();
                        ad.robot1 = t1;
                        ad.robot2 = t2;
                        ad.robot3 = t3;

                        // create the combined averages
                        // in auto low shots are worth 1/3 point, high shots = 1pt
                        ad.avgAutoFuel = (getRobot(t1).autoLowShots.avg + getRobot(t2).autoLowShots.avg + getRobot(t3).autoLowShots.avg) / 3 +
                                (getRobot(t1).autoHighShots.avg + getRobot(t2).autoHighShots.avg + getRobot(t3).autoHighShots.avg);
                        ad.avgTeleFuel = (getRobot(t1).lowShots.avg + getRobot(t2).lowShots.avg + getRobot(t3).lowShots.avg) / 9 +
                                (getRobot(t1).autoHighShots.avg + getRobot(t2).autoHighShots.avg + getRobot(t3).autoHighShots.avg) / 3;
                        ad.avgAutoGear = getRobot(t1).autoGears.avg + getRobot(t2).autoGears.avg + getRobot(t3).autoGears.avg;
                        ad.avgTeleGear = getRobot(t1).teleGears.avg + getRobot(t2).teleGears.avg + getRobot(t3).teleGears.avg;
                        ad.avgTeleClimb = getRobot(t1).climb.avg + getRobot(t2).climb.avg + getRobot(t3).climb.avg;

                        ad.calcStrength();
                        adList.add(ad);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
