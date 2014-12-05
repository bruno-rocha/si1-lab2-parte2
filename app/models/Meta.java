package models;
import javax.persistence.*;

/**
 * Created by Bruno on 26/11/2014.
 */
@Entity
public class Meta implements Comparable{
    @Id @GeneratedValue
    private long id;

    private String nome, descricao;
    private int prioridade, prazo;
    private boolean isAlcancada = false;

    public Meta(String meta, String descricao, int prioridade, int prazo){
        nome = meta;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.prazo = prazo;
    }

    public Meta() {
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String meta) {
        this.nome = meta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public boolean isAlcancada() {
        return isAlcancada;
    }

    public void setAlcancada(boolean isAlcancada) {
        this.isAlcancada = isAlcancada;
    }

    @Override
    public int compareTo(Object o) {
        Meta metaC = (Meta) o;
        if(this.getPrazo() < metaC.getPrazo()){
            return -1;
        }else if(this.getPrazo() > metaC.getPrazo()){
            return 1;
        }else{
            if(this.getPrioridade() < metaC.getPrioridade()){
                return -1;
            }else if(this.getPrioridade() > metaC.getPrioridade()){
                return 1;
            }else{
                return 0;
            }
        }
    }
}