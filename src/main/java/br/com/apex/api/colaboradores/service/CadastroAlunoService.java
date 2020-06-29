package br.com.apex.api.colaboradores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apex.api.colaboradores.Exception.NegocioException;
import br.com.apex.api.colaboradores.model.AlunoModel;
import br.com.apex.api.colaboradores.repository.AlunoRepository;

@Service
public class CadastroAlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;

	public AlunoModel salvar(AlunoModel alunoModel) {

		AlunoModel alunoExistente = alunoRepository.findByCpf(alunoModel.getCpf());

		if (alunoExistente != null && !alunoExistente.equals(alunoModel)) {
			throw new NegocioException("já existe um aluno cadastrado com este cpf.");

		}
		
		return alunoRepository.save(alunoModel);

	}
	
	public void excluir(Integer alunoId) {
		alunoRepository.deleteById(alunoId);
		
	}

}
