package com.mnks.pokedex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PokeClass {

    private final List<Object[]> filas = new ArrayList<>();

    public PokeClass() {
    }

    public void InfoPokemon(DefaultTableModel modelo, JTextField buscador, JTextField nombre,
            JTextField peso, JTextField altura, JTextField expbase, JLabel pokefoto) {


        try {
            URL urlobj = new URL("https://pokeapi.co/api/v2/pokemon/" + buscador.getText());
            HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader readerobj = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseobj = new StringBuilder();
            String line;

            while ((line = readerobj.readLine()) != null) {
                responseobj.append(line);
            }
            readerobj.close();

            JSONObject jsonObject = new JSONObject(responseobj.toString());
            String name = jsonObject.getString("name");
            int weight = jsonObject.getInt("weight");
            int height = jsonObject.getInt("height");
            int experience = jsonObject.getInt("base_experience");
            String imageUrl = jsonObject.getJSONObject("sprites").getString("front_default");

            Object[] fila = {name, weight, height};
            filas.add(fila); // Agregar la nueva fila al registro
            modelo.addRow(fila); // Agregar la nueva fila al modelo de tabla
            // Actualizar campos de texto y la imagen
            nombre.setText(name);
            peso.setText(String.valueOf(weight));
            altura.setText(String.valueOf(height));
            expbase.setText(String.valueOf(experience));
            ImageIcon icon = new ImageIcon(new URL(imageUrl));
            pokefoto.setIcon(icon);

        } catch (Exception e) {
            e.printStackTrace(); // Manejar adecuadamente las excepciones
        }
    }
}
