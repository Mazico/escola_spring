package br.com.apex.api.colaboradores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apex.api.colaboradores.model.AlunoModel;

public interface AlunoRepository extends JpaRepository<AlunoModel, Integer> {

	AlunoModel findByCpf(String cpf);

	
}
