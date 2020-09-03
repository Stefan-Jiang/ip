package duke;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Creates the Duke to run the program.
 */
public class Duke extends Application {
    private final Storage storage;
    private final TaskList list;
    private final Parser parser;
    private final Ui ui;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private final Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    public Duke(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);
        list = new TaskList(storage.load());
    }

    public Duke() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage("data/Duke.txt");
        list = new TaskList(storage.load());
    }

    /**
     * Runs the Duke.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        Instruction thisInstruction;
        Deadline thisDeadline;
        String thisTaskname;
        String thisTime;
        String keyword;
        Task thisTask;
        String input;
        int number;
        
        ui.greet();
        input = sc.nextLine();
        
        while(!input.equals("bye")) {
            System.out.println("    -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
            thisInstruction = parser.load(input);
            try {
                if (thisInstruction == Instruction.LIST) {
                    ui.printList(list.getList());
                } else if (thisInstruction == Instruction.DONE) {
                    number = Character.getNumericValue(input.charAt(input.length() - 1));
                    thisTask = list.get(number - 1);
                    thisTask.markAsDone();
                    list.set(number - 1, thisTask);
                    ui.markAsDone(thisTask);
                    storage.updateList(list.getList());
                } else if (thisInstruction == Instruction.DELETE) {
                    number = Character.getNumericValue(input.charAt(input.length() - 1));
                    thisTask = list.get(number - 1);
                    list.remove(number - 1);
                    ui.deletedTask(thisTask, list.getList());
                    storage.updateList(list.getList());
                } else if (thisInstruction == Instruction.FIND) {
                    keyword = input.substring(5);
                    ui.printFoundTask(keyword, list.getList());
                } else {
                    if (thisInstruction == Instruction.DEADLINE) {
                        if (input.length() < 10) {
                            throw new DukeException("     The taskname of a deadline cannot be empty.");
                        }
                        ui.noProblem();
                        thisTaskname = input.substring(9, input.indexOf('/') - 1);
                        thisTime = input.substring(input.indexOf('/') + 4);
                        thisDeadline = new Deadline(thisTaskname, false, thisTime);
                        thisDeadline.updateDateTime();
                        list.add(thisDeadline);
                    } else if (thisInstruction == Instruction.EVENT) {
                        if (input.length() < 7) {
                            throw new DukeException("     The taskname of a event cannot be empty.");
                        }
                        ui.noProblem();
                        thisTaskname = input.substring(6, input.indexOf('/') - 1);
                        thisTime = input.substring(input.indexOf('/') + 4);
                        list.add(new Event(thisTaskname, false, thisTime));
                    } else if (thisInstruction == Instruction.TODO) {
                        if (input.length() < 6) {
                            throw new DukeException("     The taskname of a todo cannot be empty.");
                        }
                        ui.noProblem();
                        thisTaskname = input.substring(5);
                        list.add(new Todo(thisTaskname, false));
                    } else {
                        throw new DukeException("     I'm sorry, but I don't know what that means :-(");
                    }
                    storage.updateList(list.getList());
                    ui.updatedTask(list.getList());
                }
            } catch (DukeException ex) {
                ui.printError(ex);
            }
            ui.printLine();
            input = sc.nextLine();
        }
        ui.bye();
    }

    /**
     * Starts the running of the Duke.
     */
    public static void main(String[] args) {
        new Duke("data/Duke.txt").run();
    }

    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        Button sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        Scene scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);
        
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        //Step 3. Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> handleUserInput());

        userInput.setOnAction((event) -> handleUserInput());

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, new ImageView(user)),
                DialogBox.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }
    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    private String getResponse(String input) {
        return getOutput(input);
    }
    
    private String getOutput(String input) {
        Instruction thisInstruction;
        Deadline thisDeadline;
        String thisTaskname;
        String thisTime;
        String keyword;
        String output;
        Task thisTask;
        int number;
        
        thisInstruction = parser.load(input);
        try {
            if (thisInstruction == Instruction.LIST) {
                output = ui.printListMessage(list.getList());
            } else if (thisInstruction == Instruction.DONE) {
                number = Character.getNumericValue(input.charAt(input.length() - 1));
                thisTask = list.get(number - 1);
                thisTask.markAsDone();
                list.set(number - 1, thisTask);
                output = ui.markAsDoneMessage(thisTask);
                storage.updateList(list.getList());
            } else if (thisInstruction == Instruction.DELETE) {
                number = Character.getNumericValue(input.charAt(input.length() - 1));
                thisTask = list.get(number - 1);
                list.remove(number - 1);
                output = ui.deletedTaskMessage(thisTask, list.getList());
                storage.updateList(list.getList());
            } else if (thisInstruction == Instruction.FIND) {
                keyword = input.substring(5);
                output = ui.printFoundTaskMessage(keyword, list.getList());
            } else if (thisInstruction == Instruction.BYE) {
                output = ui.byeMessage();
            } else {
                if (thisInstruction == Instruction.DEADLINE) {
                    if (input.length() < 10) {
                        throw new DukeException("     The taskname of a deadline cannot be empty.");
                    }
                    output = ui.noProblemMessage();
                    thisTaskname = input.substring(9, input.indexOf('/') - 1);
                    thisTime = input.substring(input.indexOf('/') + 4);
                    thisDeadline = new Deadline(thisTaskname, false, thisTime);
                    thisDeadline.updateDateTime();
                    list.add(thisDeadline);
                } else if (thisInstruction == Instruction.EVENT) {
                    if (input.length() < 7) {
                        throw new DukeException("     The taskname of a event cannot be empty.");
                    }
                    output = ui.noProblemMessage();
                    thisTaskname = input.substring(6, input.indexOf('/') - 1);
                    thisTime = input.substring(input.indexOf('/') + 4);
                    list.add(new Event(thisTaskname, false, thisTime));
                } else if (thisInstruction == Instruction.TODO) {
                    if (input.length() < 6) {
                        throw new DukeException("     The taskname of a todo cannot be empty.");
                    }
                    output = ui.noProblemMessage();
                    thisTaskname = input.substring(5);
                    list.add(new Todo(thisTaskname, false));
                } else {
                    throw new DukeException("     I'm sorry, but I don't know what that means :-(");
                }
                storage.updateList(list.getList());
                output = output + ui.updatedTaskMessage(list.getList());
            }
        } catch (DukeException ex) {
            output = ui.printErrorMessage(ex);
        } 
        return output;
    }
}