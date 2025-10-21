package data_acces_layer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface IFileStore<T> {
    /**
     * Lee todos los datos del tipo T guardados en el archivo.
     * @return Lista de elementos de tipo T.
     */
    List<T> readAll();

    /**
     * Escribe todos los datos del tipo indicado en el archivo.
     * @param data Los datos de tipo T.
     */
    void writeAll(List<T> data);

    /**
     * Se asegura de que el archivo donde se guardara la informacion exista.
     */
    default void ensureFile(File xmlFile) {
        try {
            File parent = xmlFile.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
                writeAll(new ArrayList<>());
            }
        } catch (Exception ignored) {}
    }
}
