/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Access.Connection;
import model.Chave;

/**
 *
 * @author Luciano
 */
public class GerenciaChaveController extends MainController {

    private int numero;
    private String local;
    private String dpto;
    private String EditExlui;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();

    @FXML
    private TableView<Chave> tbBuscaChave;

    @FXML
    private TableColumn<Chave, Integer> tbChaveChave;

    @FXML
    private TableColumn<Chave, String> tbChaveLocal;

    @FXML
    private TableColumn<Chave, String> tbChaveDPTO;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField txtNovoLocal;

    @FXML
    private TextField txtNovoNumero;

    @FXML
    private TextField txtEditDepartamento;

    @FXML
    private TextField txtBuscaChave;

    @FXML
    private TextField txtEditLocal;

    @FXML
    private TextField txtNovoDepartamento;

    @FXML
    private TextField txtEditNum;

    @FXML
    private Button btnCancelar;

    @FXML
    private AnchorPane Anchor;

    @FXML
    private JFXTabPane tbPane;

    @FXML
    private JFXButton btnCadastrar;

    @FXML
    void Editar(ActionEvent event) {
        navegacao("editar");
    }

    @FXML
    void Confirmar(ActionEvent event) {
        if ("editar".equals(EditExlui)) {
            EditExlui();
        } else {
            EditExlui();
        }
        navegacao("navegar");
        txtEditNum.setText("");
        txtEditLocal.setText("");
        txtEditDepartamento.setText("");
        txtBuscaChave.setText("");
    }

    @FXML
    private void Exclluir(ActionEvent event) {
        navegacao("excluir");
    }

    @FXML
    void Cancelar(ActionEvent event) {
        navegacao("navegar");
    }

    @FXML
    private void selectLinha(MouseEvent e) {
        numero = tbBuscaChave.getSelectionModel().getSelectedItem().getNumChave();
        local = tbBuscaChave.getSelectionModel().getSelectedItem().getLocal();
        dpto = tbBuscaChave.getSelectionModel().getSelectedItem().getDepartamento();
        txtEditNum.setText(String.valueOf(numero));
        txtEditLocal.setText(local);
        txtEditDepartamento.setText(dpto);
    }

    @FXML
    private void cadastrarChave(ActionEvent ev) {
        try {
            int num = Integer.parseInt(txtNovoNumero.getText());
            String localN = txtNovoLocal.getText().toUpperCase();
            String departamento = txtNovoDepartamento.getText().toUpperCase();
            Chave c = new Chave(num, localN, departamento, "SIM");
            c.InsereChave();
            initTable();
            txtNovoNumero.setText("");
            txtNovoLocal.setText("");
            txtNovoDepartamento.setText("");
        } catch (Exception ex) {
            alert("Cadastro", "Há dado(s) em branco!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void cmdEnterChave(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tbBuscaChave.setItems(BuscaChave());
        }
    }
    
    @FXML
    private void cmdChaveNull() {
            tbBuscaChave.setItems(BuscaChave());
    }

    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        navegacao("navegar");
    }

    private void initTable() {
        tbChaveChave.setCellValueFactory(new PropertyValueFactory("NumChave"));
        tbChaveLocal.setCellValueFactory(new PropertyValueFactory("local"));
        tbChaveDPTO.setCellValueFactory(new PropertyValueFactory("departamento"));

        tbBuscaChave.setItems(atualizaTabela());
    }

    public void ListarChave() {
        Chave c = new Chave();
        Connection con = new Connection();
        con.Open();
        c.AlteraChave();
        con.Close();
    }

    public ObservableList<Chave> atualizaTabela() {
        Chave c = new Chave();
        return FXCollections.observableArrayList(c.allChave());
    }
    
    public ObservableList<Chave> BuscaChave() {
        Chave c = new Chave();
        String chave = txtBuscaChave.getText();
        c.setBuscaChave(chave);
        c.BuscaChave();
        return FXCollections.observableArrayList(c.BuscaChave());
    }

    private void navegacao(String tipo) {
        switch (tipo) {
            case "editar": {
                btnExcluir.setDisable(true);
                btnEditar.setDisable(true);
                btnConfirmar.setDisable(false);
                btnCancelar.setDisable(false);
                tbBuscaChave.setDisable(true);
                txtEditNum.setDisable(false);
                txtEditLocal.setDisable(false);
                txtEditDepartamento.setDisable(false);
                EditExlui = "editar";
            }
            break;
            case "excluir": {
                btnEditar.setDisable(true);
                btnExcluir.setDisable(true);
                btnConfirmar.setDisable(false);
                btnCancelar.setDisable(false);
                txtEditNum.setDisable(true);
                txtEditLocal.setDisable(true);
                txtEditDepartamento.setDisable(true);
                tbBuscaChave.setDisable(true);
                EditExlui = "excluir";
            }
            break;
            case "navegar": {
                tbBuscaChave.setDisable(false);
                btnConfirmar.setDisable(true);
                btnCancelar.setDisable(true);
                btnExcluir.setDisable(false);
                btnEditar.setDisable(false);
                txtEditNum.setDisable(true);
                txtEditLocal.setDisable(true);
                txtEditDepartamento.setDisable(true);
                txtEditNum.setText("");
                txtEditLocal.setText("");
                txtEditDepartamento.setText("");
            }
            break;
        }
    }

    private void EditExlui() {
        int numN = Integer.parseInt(txtEditNum.getText().toUpperCase());
        String localN = txtEditLocal.getText().toUpperCase();
        String dptoN = txtEditDepartamento.getText().toUpperCase();

        switch (EditExlui) {
            case "editar": {
                Chave c = new Chave();
                c.setNumChave(numN);
                c.setLocal(localN);
                c.setDepartamento(dptoN);
                c.setWhere(numero);
                c.AlteraChave();
                initTable();
                EditExlui = "";
            }
            break;
            case "excluir": {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));
                alert.setTitle("Exclusão de dados");
                alert.setHeaderText("Você irá excluir:\nChave: " + numN
                        + "\nLocal: " + localN + "\nDepartamento: " + dptoN);
                alert.setContentText("A exclusão não poderá ser desfeita!\nDeseja continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Chave c = new Chave();
                    c.setWhere(numero);
                    c.excluir();
                    initTable();
                    EditExlui = "";
                } else {
                    alert("Exclusão de dados", "Exclusão cancelada!", Alert.AlertType.INFORMATION);
                }
            }
            break;
        }
    }

    private void alert(String title, String tentext, Alert.AlertType type) {
        Alert alert = new Alert(type);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));
        alert.setTitle(title);
        alert.setX(width / 2 - 150);
        alert.setY(height / 2 - 150);
        alert.setContentText(tentext);
        alert.setHeaderText(null);
        alert.setWidth(5);
        alert.show();

    }
}
