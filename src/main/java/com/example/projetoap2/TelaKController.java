package com.example.projetoap2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class TelaKController {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfFabricante;

    @FXML
    private TextField tfPreco;

    @FXML
    private TextField tfTamanhoDoSaco;

    @FXML
    private TextField tfCPotassio;

    @FXML
    private TextField tfCNitrogenio;

    @FXML
    private TextField tfCFosforo;

    @FXML
    private CheckBox cbEConcentrado;

    @FXML
    private CheckBox cbGranulado;

    @FXML
    private CheckBox cbPurificado;

    @FXML
    private TextField tfQuantidadeNoEstoque;

    @FXML
    private Button buttonSalvar;

    private final String arquivoFertilizantes = "src/main/resources/produtos.ser";

    /**
     * Evento acionado ao clicar no botão "Salvar".
     */
    @FXML
    public void salvar(ActionEvent event) {
        // Coleta os dados do formulário
        String nome = tfNome.getText();
        String fabricante = tfFabricante.getText();
        int quantidade;
        double preco;
        int tamanhoSaco;
        double potassio, nitrogenio, fosforo;
        boolean concentrado = cbEConcentrado.isSelected();
        String forma = cbGranulado.isSelected() ? "granulado" : cbPurificado.isSelected() ? "purificado" : null;

        // Valida os campos obrigatórios
        if (nome.isEmpty() || fabricante.isEmpty() || tfPreco.getText().isEmpty() || tfTamanhoDoSaco.getText().isEmpty()
                || tfCPotassio.getText().isEmpty() || tfCNitrogenio.getText().isEmpty()
                || tfCFosforo.getText().isEmpty() || forma == null) {
            showAlert("Erro", "Por favor, preencha todos os campos obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        try {
            quantidade = Integer.parseInt(tfQuantidadeNoEstoque.getText());
            preco = Double.parseDouble(tfPreco.getText());
            tamanhoSaco = Integer.parseInt(tfTamanhoDoSaco.getText());
            potassio = Double.parseDouble(tfCPotassio.getText());
            nitrogenio = Double.parseDouble(tfCNitrogenio.getText());
            fosforo = Double.parseDouble(tfCFosforo.getText());
        } catch (NumberFormatException e) {
            showAlert("Erro", "Preencha os campos de preço, tamanho do saco e concentrações com valores válidos.", Alert.AlertType.ERROR);
            return;
        }

        // Cria um objeto FSolo
        FSolo fertilizante = new FSolo(nome, fabricante, quantidade, preco, (int) nitrogenio, (int) fosforo, (int) potassio, tamanhoSaco, forma, concentrado);

        // Salva o fertilizante no arquivo
        try {
            ArrayList<Produto> fertilizantes = carregarFertilizantes();
            fertilizantes.add(fertilizante);
            salvarFertilizantes(fertilizantes);
            showAlert("Sucesso", "Fertilizante de Solo salvo com sucesso!", Alert.AlertType.INFORMATION);

            // Limpa os campos após salvar
            limparCampos();

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao salvar o fertilizante: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de fertilizantes do arquivo.
     */
    private ArrayList<Produto> carregarFertilizantes() throws IOException, ClassNotFoundException {
        File arquivo = new File(arquivoFertilizantes);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<Produto>) ois.readObject();
        }
    }

    /**
     * Salva a lista de fertilizantes no arquivo.
     */
    private void salvarFertilizantes(ArrayList<Produto> fertilizantes) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoFertilizantes))) {
            oos.writeObject(fertilizantes);
        }
    }

    public void voltarParaTelaB(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da TelaB
            Parent root = FXMLLoader.load(getClass().getResource("TelaB.fxml"));

            // Obtém o Stage atual e substitui a cena
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Principal");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Erro ao voltar para a TelaB: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Limpa os campos do formulário.
     */
    private void limparCampos() {
        tfNome.clear();
        tfFabricante.clear();
        tfPreco.clear();
        tfTamanhoDoSaco.clear();
        tfCPotassio.clear();
        tfCNitrogenio.clear();
        tfCFosforo.clear();
        cbEConcentrado.setSelected(false);
        cbGranulado.setSelected(false);
        cbPurificado.setSelected(false);
        tfQuantidadeNoEstoque.clear();
    }

    /**
     * Exibe um alerta para o usuário.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
