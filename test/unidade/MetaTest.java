package unidade;

import base.AbstractTest;
import models.Meta;
import models.dao.GenericDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import play.Logger;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bruno on 03/12/2014.
 */
public class MetaTest extends AbstractTest{
    private GenericDAO dao = new GenericDAO();
    List<Meta> metas;

    @Test
    public void deveIniciarSemMetas() throws Exception {
        metas = dao.findAllByClassName(Meta.class.getName());
        assertThat(metas.size()).isEqualTo(0);
    }

    @Test
    public void deveSalvarMetaNoBD() throws Exception {
        Meta meta = new Meta("Fazer Testes", "Garantir que o sistema funcione", 3, 1);
        dao.persist(meta);

        metas = dao.findAllByClassName(Meta.class.getName());
        assertThat(metas.size()).isEqualTo(1);
        assertThat(metas.get(0).getNome()).isEqualTo("Fazer Testes");
    }

    @Test
    public void deveDeletarMetaNoBD() throws Exception {
        Meta meta = new Meta("Fazer Testes", "Garantir que o sistema funcione", 3, 1);
        dao.persist(meta);

        metas = dao.findAllByClassName(Meta.class.getName());
        assertThat(metas.size()).isEqualTo(1);
        Long id = metas.get(0).getId();

        dao.removeById(Meta.class, id);
        metas = dao.findAllByClassName(Meta.class.getName());
        assertThat(metas).isEmpty();
    }

    @Test
    public void deveAlcancarMeta() throws Exception {
        Meta meta = new Meta("Fazer Testes", "Garantir que o sistema funcione", 3, 1);
        dao.persist(meta);

        metas = dao.findAllByClassName(Meta.class.getName());
        assertFalse(metas.get(0).isAlcancada());

        metas.get(0).setAlcancada(true);
        assertTrue(metas.get(0).isAlcancada());
    }

    @Test
    public void deveOrdenarMetas() throws Exception{
        Meta meta1 = new Meta("meta1", "testando", 1, 1);
        Meta meta2 = new Meta("meta2", "testando", 3, 2);
        Meta meta3 = new Meta("meta3", "testando", 2, 6);
        Meta meta4 = new Meta("meta4", "testando", 1, 4);
        Meta meta5 = new Meta("meta5", "testando", 2, 2);
        Meta meta6 = new Meta("meta6", "testando", 3, 3);

        dao.persist(meta1);
        dao.persist(meta2);
        dao.persist(meta3);
        dao.persist(meta4);
        dao.persist(meta5);
        dao.persist(meta6);

        dao.flush();
        metas = dao.findAllByClassName(Meta.class.getName());
        Collections.sort(metas);

        assertThat(metas.get(0).getNome()).isEqualTo("meta1");
        assertThat(metas.get(1).getNome()).isEqualTo("meta5");
        assertThat(metas.get(2).getNome()).isEqualTo("meta2");
        assertThat(metas.get(3).getNome()).isEqualTo("meta6");
        assertThat(metas.get(4).getNome()).isEqualTo("meta4");
        assertThat(metas.get(5).getNome()).isEqualTo("meta3");
    }
}
