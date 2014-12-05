package controllers;

import models.*;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

import java.util.Collections;
import java.util.List;

public class Application extends Controller {
    private static final GenericDAO dao = new GenericDAO();
    private static List<Meta> metas;

    @Transactional
    public static Result index() {
        metas = dao.findAllByClassName(Meta.class.getName());
        Collections.sort(metas);
        return ok(index.render(metas, getMetasPorSemana(), getMetasAlcancadas(), getMetasNaoAlcancadas()));
    }

    @Transactional
    public static Result criaMeta(){
        DynamicForm form = Form.form().bindFromRequest();

        String nome = form.get("nome");
        String descricao = form.get("descricao");
        int prioridade = Integer.parseInt(form.get("prioridade"));
        int prazo = Integer.parseInt(form.get("prazo"));

        Meta meta = new Meta(nome, descricao, prioridade, prazo);
        dao.persist(meta);

        metas = dao.findAllByClassName(Meta.class.getName());
        Collections.sort(metas);
        return ok(index.render(metas, getMetasPorSemana(), getMetasAlcancadas(), getMetasNaoAlcancadas()));
    }

    @Transactional
    public static Result removeMeta(long id){
        dao.removeById(Meta.class, id);
        return index();
    }

    @Transactional
    public static Result setStatus(long id){
        Meta meta = dao.findByEntityId(Meta.class, id);
        meta.setAlcancada(true);
        dao.merge(meta);
        return index();
    }

    public static int[] getMetasPorSemana(){
        metas = dao.findAllByClassName(Meta.class.getName());
        int[] metasPorSemana = new int[6];
        for (Meta meta: metas){
            metasPorSemana[meta.getPrazo() - 1]++;
        }
        return metasPorSemana;
    }

    public static int[] getMetasAlcancadas(){
        metas = dao.findAllByClassName(Meta.class.getName());
        int[] metasAlcancadas = new int[6];
        for (Meta meta: metas){
            if (meta.isAlcancada()){
                metasAlcancadas[meta.getPrazo() - 1]++;
            }
        }
        return metasAlcancadas;
    }

    public static int[] getMetasNaoAlcancadas(){
        metas = dao.findAllByClassName(Meta.class.getName());
        int[] metasAlcancadas = new int[6];
        for (Meta meta: metas){
            if (!meta.isAlcancada()){
                metasAlcancadas[meta.getPrazo() - 1]++;
            }
        }
        return metasAlcancadas;
    }
}
