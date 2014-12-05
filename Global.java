import play.*;
import models.Meta;
import models.dao.GenericDAO;
import play.db.jpa.JPA;
import java.util.*;

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");
        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                Meta meta1 = new Meta("Aprender HTML", "Será usado nos labs de SI", 3, 1);
                Meta meta2 = new Meta("Aprender CSS", "Será usado nos labs de SI", 3, 1);
                Meta meta3 = new Meta("Aprender Javascript", "Será usado nos labs de SI", 3, 1);
                Meta meta4 = new Meta("Aprender a usar Git/GitHub", "Será usado nos labs de SI", 2, 1);
                Meta meta5 = new Meta("Configurar ferramentas de desenvolvimento", "Será usado nos labs de SI", 3, 2);
                Meta meta6 = new Meta("Aprender Play", "Será usado nos labs de SI", 2, 2);
                Meta meta7 = new Meta("Terminar o lab", "Parte da nota da disciplina", 3, 2);
                Meta meta8 = new Meta("Estudar para as provas", "Tirar notas boas", 3, 4);
                Meta meta9 = new Meta("Buscar um projeto", "Descobrir interesses", 2, 5);
                Meta meta10 = new Meta("Viajar nas férias", "Descansar", 1, 6);

                dao.persist(meta1);
                dao.persist(meta2);
                dao.persist(meta3);
                dao.persist(meta4);
                dao.persist(meta5);
                dao.persist(meta6);
                dao.persist(meta7);
                dao.persist(meta8);
                dao.persist(meta9);
                dao.persist(meta10);
            }
        });
    }

    public void onStop(Application app) {
        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                Logger.info("Aplicação finalizando...");
                List<Meta> metas = dao.findAllByClassName("Meta");

                for (Meta meta : metas) {
                    dao.removeById(Meta.class, meta.getId());
                }
            }
        });
    }
}