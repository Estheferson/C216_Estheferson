package br.inatel.labs.labjpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.labs.labjpa.entity.Produto;

@Service
@Transactional
public class ProdutoService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Produto salvar(Produto p) {
		p = em.merge(p);
		return p;
	}
	
	public Produto buscarPeloId(Long id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}
	
	public List<Produto> listar() {
		List<Produto> listaProduto = em.createQuery("select p from Produto p", Produto.class)
			.getResultList();
		
		return listaProduto;
	}
	
	public void remover(Produto p) {
		p = em.merge(p);
		em.remove(p);
	}
	
	

}
