package com.example.projetoap2;

import java.io.*;
import java.util.ArrayList;

public class RepositorioClientes {
    private final String arquivo = "src/main/resources/clientes.ser";

    /**
     * Carrega a lista de clientes do arquivo.
     *
     * @return Lista de clientes.
     * @throws IOException            Se ocorrer um erro de E/S.
     * @throws ClassNotFoundException Se a classe `Cliente` não for encontrada.
     */
    public ArrayList<Cliente> listarClientes() throws IOException, ClassNotFoundException {
        File file = new File(arquivo);

        if (!file.exists()) {
            file.createNewFile();
            return new ArrayList<>(); // Retorna uma lista vazia se o arquivo não existir
        }

        if (file.length() == 0) {
            return new ArrayList<>(); // Retorna uma lista vazia se o arquivo estiver vazio
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Cliente>) ois.readObject();
        }
    }

    /**
     * Salva a lista de clientes no arquivo.
     *
     * @param clientes Lista de clientes.
     * @throws IOException Se ocorrer um erro de E/S.
     */
    public void salvarClientes(ArrayList<Cliente> clientes) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(clientes);
        }
    }
}
