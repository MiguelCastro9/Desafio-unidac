package com.desafioUnidac.repositoryTests;

import com.desafioUnidac.entity.Pessoa;
import com.desafioUnidac.repository.PessoaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Miguel Castro
 */
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class PessoaRepositoryTest {

    @Autowired
    PessoaRepository pessoaRepository;
    
    private static final String CPF = "855.786.890-19";
    private static final String ALIMENTO = "alimentoTest";

    @BeforeEach
    public void setUp() {

        Pessoa pessoa = new Pessoa();
        
        pessoa.setNome("NomeTest");
        pessoa.setCpf("855.786.890-19");
        pessoa.setAlimento("alimentoTest");
        pessoaRepository.save(pessoa);
        
        Assertions.assertNotNull(pessoa);
    }
    
    @Test
    public void testVerificador() {

        String responseAlimento = pessoaRepository.verificaAlimento(ALIMENTO);
        String responseCpf = pessoaRepository.verificaCpf(CPF);
        
        Assertions.assertTrue(!responseAlimento.isEmpty());
        Assertions.assertTrue(!responseCpf.isEmpty());
        Assertions.assertEquals(responseCpf, CPF);
        Assertions.assertEquals(responseAlimento, ALIMENTO);
        
    }

    @AfterEach
    public void tearDown() {

        pessoaRepository.deleteAll();
    }

}
