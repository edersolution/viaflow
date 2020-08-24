package br.com.ersolution.viaflow.brasilprev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ersolution.viaflow.brasilprev.exception.ResourceNotFoundException;
import br.com.ersolution.viaflow.brasilprev.model.Cliente;
import br.com.ersolution.viaflow.brasilprev.repository.ClienteRepository;

/**
 * Classe ClienteController
 * 
 * essa classe e responsavel pela CRUD que esta sendo enviado 
 * pela API
 * 
 * **/
@RestController
@RequestMapping("/api/v1")

public class ClienteController {
	
	
	@Autowired
	private ClienteRepository clienteRepository;

	//Lista todos os clientes
	@GetMapping("/cliente")
	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	//Lista apenas o Ciente que esta sendo informado pelo ID
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable(value = "id") Long clienteId)
			throws ResourceNotFoundException {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para este id :: " + clienteId));
		return ResponseEntity.ok().body(cliente);
	}

	
	
	//Criacao de um cliente
	@PostMapping("/cliente")
	public Cliente createCliente(@Valid @RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	//Alteracao de Cliente pelo Id enviado
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") Long clienteId,
			@Valid @RequestBody Cliente clienteDetails) throws ResourceNotFoundException {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para este id :: " + clienteId));

		cliente.setId(clienteDetails.getId());
        cliente.setNome(clienteDetails.getNome());
        cliente.setEmail(clienteDetails.getEmail());
        cliente.setEndereco(clienteDetails.getEndereco());
        cliente.setBairro(clienteDetails.getBairro());
        cliente.setCidade(clienteDetails.getCidade());
        cliente.setEstado(clienteDetails.getEstado());
        cliente.setCep(clienteDetails.getCep());
        cliente.setCpf(clienteDetails.getCpf());
		final Cliente updatedCliente = clienteRepository.save(cliente);
		return ResponseEntity.ok(updatedCliente);
	}

	//Delete de Cliente pelo ID
	@DeleteMapping("/cliente/{id}")
	public Map<String, Boolean> deleteCliente(@PathVariable(value = "id") Long clienteId)
			throws ResourceNotFoundException {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para este id :: " + clienteId));

		clienteRepository.delete(cliente);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
