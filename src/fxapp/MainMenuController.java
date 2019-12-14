package fxapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuController {
    public Button ordersButton;
    public Button booksButton;
    public Button authorsButton;
    public Button readersButton;
    public Button checkOutsButton;
    public Button copiesButton;
    public Button affiliatesButton;
    public Button positionsButton;
    public Button shiftScheduleButton;
    public Button shiftsButton;
    public Button employeesButton;
    public Button sectionsButton;

    public void toOrders(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("orders.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toBooks(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("books.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toAuthors(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("authors.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toReaders(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("readers.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toCheckOuts(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("check-outs.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toCopies(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("copies.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toSections(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("sections.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toAffiliates(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("affiliates.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toShiftSchedule(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("shift-schedule.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toShifts(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("shifts.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toEmployees(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("employees.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void toPositions(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("positions.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void exit(ActionEvent actionEvent) {
        Main.getMainStage().close();
    }

}
