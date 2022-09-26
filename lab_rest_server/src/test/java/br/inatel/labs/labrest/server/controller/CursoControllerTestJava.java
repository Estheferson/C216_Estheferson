package br.inatel.labs.labrest.server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import br.inatel.labs.labrest.server.model.Curso;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CursoControllerTestJava {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void deveListarCursos(){
        webTestClient.get()
                .uri("/curso")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();
    }
    @Test
    void dadoCursoIdValido_quandoGetCursoPeloId_entaoRespondeComCursoValido(){
        Long cursoIdValido = 1L;

        Curso cursoRespondido = webTestClient.get()
                .uri("/curso/" + cursoIdValido)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Curso.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(cursoRespondido);
        assertEquals(cursoRespondido.getId(),cursoIdValido);
    }
    @Test
    void dadoCursoIdInvalido_quandoGetCursoPeloId_entaoRespondeComStatusNotFound(){
        Long cursoIdInvalido = 99L;

        webTestClient.get()
                .uri("/curso/" + cursoIdInvalido)
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void dadoNovoCurso_quandoPostCurso_entaoRespondeComStatusCreatedECursoValido(){

        Curso novoCurso = new Curso();
        novoCurso.setDescricao("Testes");
        novoCurso.setCargaHoraria(120);

        Curso cursoRespondido = webTestClient.post()
                .uri("/curso")
                .bodyValue(novoCurso)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Curso.class)
                .returnResult().getResponseBody();

        assertNotNull(cursoRespondido);
        assertNotNull(cursoRespondido.getId());
    }
    @Test
    void dadoCursoIdValido_quandoPutCursoPeloId_entaoRespondeComStatusAcceptedECorpoVazio(){

        Curso cursoExistente = new Curso(1L,"Descricao do curso atualizada", 120);

         webTestClient
                .put()
                .uri("/curso")
                .bodyValue(cursoExistente)
                .exchange()
                .expectStatus().isAccepted()
                 .expectBody().isEmpty();
    }
    @Test
    void dadoCursoIdValido_quandoDeleteCursoPeloId_entaoRespondeComStatusNoContentECorpoVazio(){

        Long cursoIdRemover = 2L; //Troca de ID

        webTestClient
                .delete()
                .uri("/curso/" + cursoIdRemover)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }
    @Test
    void dadoCursoIdInvalido_quandoDeleteCursoPeloId_entaoRespondeComStatusNotFound(){

        Long cursoIdRemover = 99L;

        webTestClient
                .delete()
                .uri("/curso/" + cursoIdRemover)
                .exchange()
                .expectStatus().isNotFound();
    }
}