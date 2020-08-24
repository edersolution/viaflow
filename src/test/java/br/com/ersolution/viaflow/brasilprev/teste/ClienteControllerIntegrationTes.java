package br.com.ersolution.viaflow.brasilprev.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import br.com.ersolution.viaflow.brasilprev.Application;
import br.com.ersolution.viaflow.brasilprev.model.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerIntegrationTes {

	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllClientes() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/cliente",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }

   @Test
   public void testGetClienteById() {
       Cliente cliente = restTemplate.getForObject(getRootUrl() + "/cliente/1", Cliente.class);
       System.out.println(cliente.getNome());
       assertNotNull(cliente);
   }

   @Test
   public void testCreateCliente() {
       Cliente cliente = new Cliente();
       cliente.setNome("Eder Rocha");
       cliente.setEmail("eder.rocha@ersolution.com.br");
       cliente.setCep("05202090");
       cliente.setCpf("00000000000");
       cliente.setEndereco("Rua Dr Joao Rodrigues de Abreu");
       cliente.setBairro("Perus");
       cliente.setCidade("Sao Paulo");
       cliente.setEstado("SP");
       
       ResponseEntity<Cliente> postResponse = restTemplate.postForEntity(getRootUrl() + "/cliente", cliente, Cliente.class);
       assertNotNull(postResponse);
       assertNotNull(postResponse.getBody());
   }

   @Test
   public void testUpdateCliente() {
       int id = 1;
       Cliente cliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
       cliente.setNome("Eder Rocha");
       cliente.setEmail("eder.rocha@ersolution.com.br");
       cliente.setCep("05202090");
       cliente.setCpf("00000000000");
       cliente.setEndereco("Rua Dr Joao Rodrigues de Abreu");
       cliente.setBairro("Perus");
       cliente.setCidade("Sao Paulo");
       cliente.setEstado("SP");
       restTemplate.put(getRootUrl() + "/cliente/" + id, cliente);
       Cliente updatedCliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
       assertNotNull(updatedCliente);
   }

   @Test
   public void testDeleteCliente() {
        int id = 2;
        Cliente cliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
        assertNotNull(cliente);
        restTemplate.delete(getRootUrl() + "/cliente/" + id);
        try {
             cliente = restTemplate.getForObject(getRootUrl() + "/cliente/" + id, Cliente.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }
}
