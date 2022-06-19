package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class AjouterServiceController implements Initializable {
	@FXML
	private TextField idServiceTextField = new TextField();
	@FXML
	private TextField nomTextFiield = new TextField() ;
	@FXML
	private TextField idGestionnaireTextField = new TextField();
	@FXML
	private Button validationBtn;
	@FXML
	private Button retourBtn;
	
	Admin admin = TableServiceController.admin;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idServiceTextField.setText("");
		nomTextFiield.setText("");
		idGestionnaireTextField.setText("");
	}
	
	// Event Listener on Button[#validationBtn].onAction
	@FXML
	public void validationHandler(ActionEvent event) {
		if((idServiceTextField.getText() != null) && !(idServiceTextField.getText().equals(""))) {
			if (idGestionnaireTextField.getText().equals("0000")) {
				Service service = new Service(idServiceTextField.getText(), nomTextFiield.getText(), admin);
				int test = admin.ajouterService(service);
				if (test == -1) {
					Alert error = new Alert(Alert.AlertType.ERROR);
					error.setTitle("Op�ration �chou�e");
					error.setContentText("Le service correspondant � l'id saisi figure d�j� dans la base de donn�es");
					error.setHeaderText(null);
					error.show();
				}
				else {
					if(test == 1) {
						TableServiceController.items.add(service);
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setTitle("Op�ration r�ussie");
						information.setContentText("Le service a �t� ajout� avec succ�s");
						information.setHeaderText(null);
						information.show();
						initialize(null, null);
						TableServiceController t = new TableServiceController();
						t.initialize(null, null);
					}
					else {
						Alert error = new Alert(Alert.AlertType.ERROR);
						error.setTitle("Op�ration �chou�e");
						error.setContentText("Erreur de la connexion avec la base de donn�es. Veuillez r�essayer plus tard.");
						error.setHeaderText(null);
						error.show();
					}
				}
			}
			else {
				Gestionnaire gestionnaire = Data.rechercherGestionnaire(idGestionnaireTextField.getText());
				if (gestionnaire !=null) {
					Service service = new Service(idServiceTextField.getText(), nomTextFiield.getText(), gestionnaire);
					int test = admin.ajouterService(service);
					if(test == 1) {
						TableServiceController.items.add(service);
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setTitle("Op�ration r�ussie");
						information.setContentText("Le service a �t� ajout� avec succ�s");
						information.setHeaderText(null);
						information.show();
						TableServiceController t = new TableServiceController();
						t.initialize(null, null);
					}
					else {
						if (test == -1) {
							Alert error = new Alert(Alert.AlertType.ERROR);
							error.setTitle("Op�ration �chou�e");
							error.setContentText("Le service correspondant � l'id saisi figure d�j� dans la base de donn�es");
							error.setHeaderText(null);
							error.show();
						}
						else {
							Alert error = new Alert(Alert.AlertType.ERROR);
							error.setTitle("Op�ration �chou�e");
							error.setContentText("Erreur de la connexion avec la base de donn�es. Veuillez r�essayer plus tard.");
							error.setHeaderText(null);
							error.show();
						}
					}
				}
				else {
					Alert error = new Alert(Alert.AlertType.ERROR);
					error.setTitle("Op�ration �chou�e");
					error.setContentText("Aucun gestionnaire ne correspond � l'id saisi");
					error.setHeaderText(null);
					error.show();
				}
			}
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Veuillez saisir un id pour le service");
			error.setHeaderText(null);
			error.show();
		}
		
	}
	
	// Event Listener on Button[#retourBtn].onAction
	@FXML
	public void returnHandler(ActionEvent event) throws IOException {
		// TODO Autogenerated
		TableServiceController.s.close(SceneAjouterService.principaleWindow);
	}
	
}
