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
import java.util.Arrays;

public class TelaMController {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfFabricante;

    @FXML
    private TextField tfPreco;

    @FXML
    private TextField tfClasse;

    @FXML
    private TextField tfConcentracao;

    @FXML
    private TextField tfNivelToxicidade;

    @FXML
    private TextField tfPrincipiosAtivos;

    @FXML
    private TextField tfQuantidadeNoEstoque;

    @FXML
    private Button buttonSalvar;

    @FXML
    private Button buttonVoltar;

    private final String arquivoDefensivos = "src/main/resources/produtos.ser";

    /**
     * Evento acionado ao clicar no botão "Salvar".
     */
    @FXML
    public void salvar(ActionEvent event) {
        // Coleta os dados do formulário
        String nome = tfNome.getText();
        String fabricante = tfFabricante.getText();
        double preco;
        String classe = tfClasse.getText();
        int nivelToxicidade;
        ArrayList<String> principiosAtivos;
        ArrayList<Double> concentracoes;

        // Valida os campos obrigatórios
        if (nome.isEmpty() || fabricante.isEmpty() || tfPreco.getText().isEmpty() || classe.isEmpty()
                || tfPrincipiosAtivos.getText().isEmpty() || tfConcentracao.getText().isEmpty()
                || tfNivelToxicidade.getText().isEmpty()) {
            showAlert("Erro", "Por favor, preencha todos os campos obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        try {
            preco = Double.parseDouble(tfPreco.getText());
            nivelToxicidade = Integer.parseInt(tfNivelToxicidade.getText());

            // Valida nível de toxicidade
            if (nivelToxicidade < 1 || nivelToxicidade > 5) {
                showAlert("Erro", "O nível de toxicidade deve estar entre 1 e 5.", Alert.AlertType.ERROR);
                return;
            }

            // Processa princípios ativos e concentrações
            principiosAtivos = new ArrayList<>(Arrays.asList(tfPrincipiosAtivos.getText().split(",")));
            concentracoes = new ArrayList<>();
            for (String s : tfConcentracao.getText().split(",")) {
                concentracoes.add(Double.parseDouble(s));
            }

            // Valida tamanhos correspondentes
            if (principiosAtivos.size() != concentracoes.size()) {
                showAlert("Erro", "O número de princípios ativos deve corresponder ao número de concentrações.", Alert.AlertType.ERROR);
                return;
            }

        } catch (NumberFormatException e) {
            showAlert("Erro", "Preencha os campos numéricos com valores válidos.", Alert.AlertType.ERROR);
            return;
        }

        // Cria um objeto Defensivo
        Defensivo defensivo = new Defensivo(nome, fabricante, preco, classe, principiosAtivos, concentracoes, nivelToxicidade, null);

        // Salva o defensivo no arquivo
        try {
            ArrayList<Produto> defensivos = carregarDefensivos();
            defensivos.add(defensivo);
            salvarDefensivos(defensivos);
            showAlert("Sucesso", "Defensivo Agrícola salvo com sucesso!", Alert.AlertType.INFORMATION);

            // Limpa os campos após salvar
            limparCampos();

        } catch (IOException | ClassNotFoundException e) {
            showAlert("Erro", "Erro ao salvar o defensivo: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Evento acionado ao clicar no botão "Voltar".
     */
    @FXML
    public void voltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TelaB.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Principal");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Erro ao voltar para a Tela Principal: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de defensivos do arquivo.
     */
    private ArrayList<Produto> carregarDefensivos() throws IOException, ClassNotFoundException {
        File arquivo = new File(arquivoDefensivos);

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<Produto>) ois.readObject();
        }
    }

    /**
     * Salva a lista de defensivos no arquivo.
     */
    private void salvarDefensivos(ArrayList<Produto> defensivos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoDefensivos))) {
            oos.writeObject(defensivos);
        }
    }

    /**
     * Limpa os campos do formulário.
     */
    private void limparCampos() {
        tfNome.clear();
        tfFabricante.clear();
        tfPreco.clear();
        tfClasse.clear();
        tfConcentracao.clear();
        tfNivelToxicidade.clear();
        tfPrincipiosAtivos.clear();
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
