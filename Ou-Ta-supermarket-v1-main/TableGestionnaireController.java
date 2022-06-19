package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class TableGestionnaireController implements Initializable {
	@FXML
	private Button btnservices;
	@FXML
	private Button btngestionnaires;
	@FXML
	private TableView<Gestionnaire> tablegestionnaire = new TableView<Gestionnaire>();
	@FXML
	private TableColumn<Gestionnaire, String> clidgestionnaire = new TableColumn<Gestionnaire, String>();
	@FXML
	private TableColumn<Gestionnaire, String>  clnomcomplet = new TableColumn<Gestionnaire, String>();
	@FXML
	private TableColumn<Gestionnaire, String>  cltelephone = new TableColumn<Gestionnaire, String>();
	@FXML
	private TableColumn<Gestionnaire, String>  cladrdess = new TableColumn<Gestionnaire, String>();
	@FXML
	private Button btnajoutergest;
	@FXML
	private Button btnsupprimergest;
	@FXML
	private Button btnaffecterservice;
	@FXML
    private Button btndisconnection;
	@FXML
	private Button btnactualiser;
	
	static SceneAjouterGestionnaire s = new SceneAjouterGestionnaire();
	static SceneAffecterService s1 = new SceneAffecterService();
	static Stage window = new Stage();
	static ObservableList<Gestionnaire> items = Data.getGestionnaire();
	static Admin admin = (Admin) Data.rechercherGestionnaire("0000");
	static Gestionnaire gestionnaire = new Gestionnaire();
	static Service service = Data.rechercherService("0");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clidgestionnaire.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("idGestionnaire"));
		clnomcomplet.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("nomComplet"));
		cltelephone.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("telephone"));
		cladrdess.setCellValueFactory(new PropertyValueFactory<Gestionnaire, String>("address"));
		tablegestionnaire.setItems(items);
	}

	// Event Listener on Button[#btnservices].onAction
	@FXML
	public void afficherservicesbutton(ActionEvent event) throws IOException {
		AnchorPane root = FXMLLoader.load(getClass().getResource("TableService.fxml"));
		GestionApp.scene.setRoot(root);
		GestionApp.principaleWindow.setWidth(720);
	}
	
	// Event Listener on Button[#btnajoutergest].onAction
	@FXML
	public void ajoutergestionnairebutton(ActionEvent event) {
		// TODO Autogenerated
		try {
			s.start(window);
		} catch (Exception e) {
		}
	}
	
	// Event Listener on Button[#btnsupprimergest].onAction
	@FXML
	public void supprimergestionnairebutton(ActionEvent event) {
		// TODO Autogenerated
		Gestionnaire gestionnaire = tablegestionnaire.getSelectionModel().getSelectedItem();
		if(gestionnaire != null) {
			if(gestionnaire.getIdGestionnaire().equals("0000")) {
				Alert error = new Alert(Alert.AlertType.ERROR);
				error.setTitle("Op�ration �chou�e");
				error.setContentText("Impossible de supprimer l'admin.");
				error.setHeaderText(null);
				error.show();
			}
			else {
				Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
				confirm.setTitle("Confirmation");
				confirm.setHeaderText(null);
				confirm.setContentText("�tes vous sur de vouloir supprimer ce gestionnaire ?");
				confirm.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
				ButtonType btnOui = new ButtonType("Oui");
				ButtonType btnNon = new ButtonType("Annuler");
				confirm.getButtonTypes().addAll(btnOui, btnNon);
				Optional<ButtonType> result = confirm.showAndWait();
				if(result.get() == btnOui) {
					int test =admin.supprimerGestionnaire(gestionnaire);
					if(test == 1) {
						items.remove(gestionnaire);
						initialize(null, null);
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setTitle("Op�ration r�ussie");
						information.setContentText("Le gestionnaire a �t� supprim� avec succ�s");
						information.setHeaderText(null);
						information.show();
					}
					else {
						Alert error = new Alert(Alert.AlertType.ERROR);
						error.setTitle("Op�ration �chou�e");
						error.setContentText("Probl�me de connexion.");
						error.setHeaderText(null);
						error.show();
					}
					
				}
			}
			
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Aucun gestionnaire s�lectionn�. Veuillez s�lectionner un gestionnaire.");
			error.setHeaderText(null);
			error.show();
		}
	}
	
	// Event Listener on Button[#btnaffecterservice].onAction
	@FXML
	public void affecterservicebutton(ActionEvent event) {
		// TODO Autogenerated
		gestionnaire = tablegestionnaire.getSelectionModel().getSelectedItem();
		if( gestionnaire != null) {
			try {
				s1.start(window);
			} catch (Exception e) {
			}
		}
		else {
			Alert error = new Alert(Alert.AlertType.ERROR);
			error.setTitle("Op�ration �chou�e");
			error.setContentText("Veuillez selecetionner un service.");
			error.setHeaderText(null);
			error.show();
		}	
	}
	
	// Event Listener on Button[#btndisconnection].onAction
	 @FXML
	    private void disconnectionHandler(ActionEvent event) {
	    	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	    	confirm.setTitle("Confirmer la d�connexion");
	    	confirm.setContentText("�tes vous s�r de vouloir se d�connecter?");
	    	confirm.setHeaderText(null);
	    	confirm.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
			ButtonType btnOui = new ButtonType("Oui");
			ButtonType btnNon = new ButtonType("Annuler");
			confirm.getButtonTypes().addAll(btnOui, btnNon);
			Optional<ButtonType> result = confirm.showAndWait();
			if(result.get()==btnOui) {
				AnchorPane root = null;
				try {
					root = FXMLLoader.load(getClass().getResource("Login2.fxml"));
					GestionApp.scene.setRoot(root);
					GestionApp.principaleWindow.setWidth(624);
					GestionApp.principaleWindow.setTitle("Ou&Ta Supermarket");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	    }
	 
	 
	// Event Listener on Button[#btnactualiser].onAction
			@FXML
			public void actualiserHandler(ActionEvent event) {
				items.clear();
				tablegestionnaire.setItems(items);
				initialize(null, null);
				items.clear();
				tablegestionnaire.setItems(items);
				initialize(null, null);
			}
	
	public Gestionnaire getSelectedGestionnaire() {
		return gestionnaire;
	}
	
}