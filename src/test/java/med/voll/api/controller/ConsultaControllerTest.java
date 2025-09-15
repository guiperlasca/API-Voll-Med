package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.medico.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    // Mockear os repositórios para evitar consultas ao banco
    @MockBean
    private PacienteRepository pacienteRepository;

    @MockBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var idMedico = 2L;
        var idPaciente = 5L;

        // Mock da consulta que será retornada
        var consulta = new Consulta(1L, null, null, data);

        // Configurar os mocks dos repositórios para simular que os dados existem
        when(pacienteRepository.existsById(idPaciente)).thenReturn(true);
        when(medicoRepository.existsById(idMedico)).thenReturn(true);

        // Configurar o mock para retornar a consulta
        when(agendaDeConsultas.agendar(any())).thenReturn(consulta);

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJson.write(
                                        new DadosAgendamentoConsulta(idMedico, idPaciente, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Verificar se a resposta contém os dados corretos
        var jsonResponse = response.getContentAsString();
        assertThat(jsonResponse).isNotEmpty();

        // Como o controller retorna a própria consulta, vamos verificar se contém os campos básicos
        assertThat(jsonResponse).contains("\"id\":");
        assertThat(jsonResponse).contains("\"data\":");
    }

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando data for no passado")
    @WithMockUser
    void agendar_cenario3() throws Exception {
        var dataNoPassado = LocalDateTime.now().minusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJson.write(
                                        new DadosAgendamentoConsulta(2L, 5L, dataNoPassado, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando paciente id for nulo")
    @WithMockUser
    void agendar_cenario4() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJson.write(
                                        new DadosAgendamentoConsulta(2L, null, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}