package br.com.apex.api.colaboradores.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.apex.api.colaboradores.Exception.NegocioException;
import br.com.apex.api.colaboradores.model.AlunoModel;
import br.com.apex.api.colaboradores.repository.AlunoRepository;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping
	public List<AlunoModel> listar() {
		return alunoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<AlunoModel> buscar(@PathVariable Integer id) {
		Optional<AlunoModel> aluno = alunoRepository.findById(id);

		if (aluno.isPresent()) {
			return ResponseEntity.ok(aluno.get());
		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AlunoModel adicionar(@Valid @RequestBody AlunoModel alunoModel) {
		if (alunoModel.getMedia() >= 7) {
			alunoModel.setSituacao("Aprovado");
		} else {
			alunoModel.setSituacao("Reprovado");

		}

		return alunoRepository.save(alunoModel);

	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<AlunoModel> atualizar(@Valid @PathVariable Integer id,
			@RequestBody AlunoModel alunoModel) throws NegocioException {
		AlunoModel aluno = alunoRepository.findById(id)
				.orElseThrow(() -> new NegocioException(" aluno não encontrado para  o id :: " + id));

		
		
		aluno.setNome(alunoModel.getNome());
		aluno.setCpf(alunoModel.getCpf());
		aluno.setIdade(alunoModel.getIdade());
		aluno.setMedia(alunoModel.getMedia());
		
		if (aluno.getMedia() >= 7) {
			aluno.setSituacao("Aprovado");

		} else {
			aluno.setSituacao("Reprovado");
		}
		AlunoModel alunoAtualizado = alunoRepository.save(aluno);
		return ResponseEntity.ok(alunoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@Valid @PathVariable Integer id) {
		if (!alunoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		alunoRepository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

}
