package com.example.projetoap2;

import java.io.*;
import java.util.ArrayList;

public class RepositorioContas {
    String arquivo = "src/main/resources/contas.ser"; // Caminho dentro do projeto

    public ArrayList<Conta> listContas() throws IOException, ClassNotFoundException {
        ArrayList<Conta> contas = new ArrayList<>();
        File arquivoFisico = new File(arquivo);

        // Verifica se o arquivo existe, caso contrário, cria um novo arquivo vazio
        if (!arquivoFisico.exists()) {
            arquivoFisico.getParentFile().mkdirs(); // Cria os diretórios necessários
            arquivoFisico.createNewFile(); // Cria o arquivo vazio
            return contas; // Retorna a lista vazia
        }

        // Leitura do arquivo caso exista
        FileInputStream fis = new FileInputStream(arquivo);
        if (fis.available() > 0) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            contas = (ArrayList<Conta>) ois.readObject();
            ois.close();
        }
        fis.close();
        return contas;
    }

    public void createConta(Conta conta) throws IOException, ClassNotFoundException {
        System.out.println("Adicionando a conta: " + conta.getLogin());

        ArrayList<Conta> contas = listContas();
        contas.add(conta);

        System.out.println("Lista de contas: " + contas);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo));
        oos.writeObject(contas);
        oos.close();

        System.out.println("Conta salva com sucesso!");
    }


    public Conta readConta(String login, String senha) throws EOFException,IOException,FileNotFoundException,ClassNotFoundException{
        ArrayList<Conta> contas = listContas();
        Conta conta = null;
        for(Conta c : contas){
            if(c.getLogin().equals(login) && c.getSenha().equals(senha)){
                conta = c;
                break;
            }
        }
        return conta;
    }

}
