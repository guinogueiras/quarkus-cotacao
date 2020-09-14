package br.com.cotacao.repository;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.com.cotacao.entity.CotacaoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CotacaoRepository implements PanacheRepository<CotacaoEntity> {

   public List<CotacaoEntity> findByData(LocalDate localDate){
       return find("data", localDate).list();
   }
}