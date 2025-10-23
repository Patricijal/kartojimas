package org.example.kartojimas;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.kartojimas.hibernateControl.GenericHibernate;
import org.example.kartojimas.model.CreatureType;
import org.example.kartojimas.model.MagicalCreature;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public ListView<MagicalCreature> magicalList;
    public TextField wizardDataField;
    public TextField titleDataField;
    public DatePicker dateField;
    public ComboBox<CreatureType> creatureTypeField;
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("suffer");
    private GenericHibernate genericHibernate = new GenericHibernate(entityManagerFactory);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creatureTypeField.getItems().addAll(CreatureType.values());

        List<MagicalCreature> magicalCreatureList = FileUtils.readFromFile();
        for (MagicalCreature m : magicalCreatureList) {
            genericHibernate.create(m);
        }
        magicalList.getItems().addAll(genericHibernate.getAllRecords(MagicalCreature.class));
    }

    public void filterData() {
        List<MagicalCreature> filtered = genericHibernate.getByCriteria(wizardDataField.getText(), titleDataField.getText(), creatureTypeField.getValue(), dateField.getValue());
        magicalList.getItems().clear();
        magicalList.getItems().addAll(filtered);
    }

    public void createNew() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("creature-form.fxml"));
        Parent parent = fxmlLoader.load();

        CreatureForm creatureForm = fxmlLoader.getController();
        creatureForm.setData(entityManagerFactory);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
