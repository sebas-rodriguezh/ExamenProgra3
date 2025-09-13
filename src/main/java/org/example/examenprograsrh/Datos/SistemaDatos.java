package org.example.examenprograsrh.Datos;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class SistemaDatos {
    private final Path xmlPath;
    private final JAXBContext ctx;
    private SistemaConector cache;

    public SistemaDatos(String filePath) {
        try {
            this.xmlPath = Path.of(Objects.requireNonNull(filePath));
            this.ctx = JAXBContext.newInstance(SistemaConector.class,
                    ProyectoEntity.class,
                    TareaEntity.class,
                    UsuarioEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando JAXBContext", e);
        }
    }

    public synchronized SistemaConector load() {
        try {
            if (cache != null) return cache;

            if (!Files.exists(xmlPath)) {
                cache = new SistemaConector();
                save(cache);
                return cache;
            }

            Unmarshaller u = ctx.createUnmarshaller();
            cache = (SistemaConector) u.unmarshal(xmlPath.toFile());
            if (cache.getUsuarios() == null) cache.setUsuarios(new ArrayList<>());
            if (cache.getProyectos() == null) cache.setProyectos(new ArrayList<>());
            return cache;

        } catch (Exception e) {
            throw new RuntimeException("Error cargando XML: " + xmlPath, e);
        }
    }

    public synchronized void save(SistemaConector data) {
        try {
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            File out = xmlPath.toFile();
            File parent = out.getParentFile();
            if (parent != null) parent.mkdirs();

            m.marshal(data, out);
            cache = data;

        } catch (Exception e) {
            throw new RuntimeException("Error guardando XML: " + xmlPath, e);
        }
    }

    public Path getXmlPath() { return xmlPath; }
}