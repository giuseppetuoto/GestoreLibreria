package json;

import jakarta.json.*;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonGeneratorFactory;
import shapes.model.Libro;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibreriaJson {

    public static void salva(List<Libro> libri, String file) {
        Map<String, Object> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);

        JsonGeneratorFactory factory = Json.createGeneratorFactory(config);

        try (JsonGenerator generator = factory.createGenerator(new FileWriter(file))) {
            generator.writeStartArray();
            for (Libro libro:libri) {
                generator.writeStartObject()
                        .write("titolo", libro.getTitolo())
                        .write("autore", libro.getAutore())
                        .write("ISBN", libro.getIsbn())
                        .write("genere", libro.getGenere())
                        .write("valutazione", libro.getValutazione())
                        .write("statoLettura", libro.getStatoLettura().toString())
                        .writeEnd();
            }
            generator.writeEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Libro> carica(String file) {
        List<Libro> libri = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new FileReader(file))) {
            JsonArray array = reader.readArray();

            for (JsonValue val : array) {
                JsonObject obj = val.asJsonObject();

                String titolo = obj.getString("titolo", "");
                String autore = obj.getString("autore", "");
                String isbn = obj.getString("ISBN", "");
                String genere = obj.getString("genere", "");
                int valutazione = obj.getInt("valutazione", 1);
                Libro.StatoLettura stato = Libro.StatoLettura.valueOf(obj.getString("statoLettura", "DA_LEGGERE"));

                Libro libro = new Libro.Builder(titolo, autore, isbn)
                        .genere(genere)
                        .valutazione(valutazione)
                        .statoLettura(stato)
                        .build();

                libri.add(libro);
            }

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return libri;
    }

}
