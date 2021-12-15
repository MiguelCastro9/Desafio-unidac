package com.desafioUnidac.repository;

import com.desafioUnidac.entity.Pessoa;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Miguel Castro
 */
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Pessoa (nome, cpf, alimento) VALUES (?, ?, ?)", nativeQuery = true)
    public void cadastrarPessoaReepository(@Param("nome") String nome, @Param("cpf") String cpf, @Param("alimento") String alimento);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE Pessoa SET nome = :nome, cpf = :cpf, alimento = :alimento WHERE id = :id", nativeQuery = true)
    public void alterarPessoaReepository(@Param("nome") String nome, @Param("cpf") String cpf, @Param("alimento") String alimento, @Param("id") long id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Pessoa WHERE id = :id", nativeQuery = true)
    public void excluirPessoaRepository(@Param("id") long id);
    
    @Query(value = "SELECT * FROM Pessoa", nativeQuery = true)
    public List<Pessoa> listarPessoaRepository();
     
    @Query(value = "SELECT * FROM Pessoa WHERE nome LIKE %:nome%", nativeQuery = true)
    public List<Pessoa> buscarPorNomeRepository(@Param("nome") String nome);
    
    @Query(value = "SELECT * FROM Pessoa WHERE cpf LIKE %:cpf%", nativeQuery = true)
    public List<Pessoa> buscarPorCpfRepository(@Param("cpf") String cpf);
    
    @Query(value = "SELECT * FROM Pessoa WHERE alimento LIKE %:alimento%", nativeQuery = true)
    public List<Pessoa> buscarPorAlimentoRepository(@Param("alimento") String alimento);
    
    @Query(value = "SELECT cpf FROM Pessoa WHERE cpf = :cpf", nativeQuery = true)
    public String verificaCpf(@Param("cpf") String cpf);
    
    @Query(value = "SELECT alimento FROM Pessoa WHERE alimento = :alimento", nativeQuery = true)
    public String verificaAlimento(@Param("alimento") String alimento);
    
}
