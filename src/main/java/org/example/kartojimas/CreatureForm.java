package org.example.kartojimas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.kartojimas.hibernateControl.GenericHibernate;
import org.example.kartojimas.model.CreatureType;
import org.example.kartojimas.model.MagicalCreature;
import org.example.kartojimas.utils.FxUtils;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CreatureForm implements Initializable {
    public TextField titleField;
    public ComboBox<CreatureType> typeField;
    public TextArea elixirField;
    public DatePicker dateField;
    public TextField wizardField;
    public CheckBox isInDanger;
    public Button createButton;

    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);

        // alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Success");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }

    public void createMagic() {
        if (titleField.getText() == null || titleField.getText().trim().isEmpty()) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Oh no", "Error", "Empty title");
            return;
        }

        if (typeField.getValue() == null) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Oh no", "Error", "Empty type value");
            return;
        }

        if (dateField.getValue() == null) {
            FxUtils.generateAlert(Alert.AlertType.WARNING, "Oh no", "Error", "Empty date value");
            return;
        }

        MagicalCreature magicalCreature = new MagicalCreature(titleField.getText(), typeField.getValue(), elixirField.getText(), dateField.getValue(), wizardField.getText(), isInDanger.isSelected());
        genericHibernate.create(magicalCreature);

        // Close the form
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();

        new Thread(() -> {
            List<MagicalCreature> magicalCreatureList = genericHibernate.getAllRecords(MagicalCreature.class);
            Collections.sort(magicalCreatureList);
            var i = 0;
            for (MagicalCreature m : magicalCreatureList) {
                System.out.println(m);
//                    FileUtils.writeUserToFile(m, "failas.txt");
                FileUtils.writeUserToFile(m, "failas" + i + ".txt");
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeField.getItems().addAll(CreatureType.values());
    }
}
