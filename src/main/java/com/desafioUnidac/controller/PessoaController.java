package com.desafioUnidac.controller;

import com.desafioUnidac.entity.Pessoa;
import com.desafioUnidac.repository.PessoaRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Miguel Castro
 */
@Controller
public class PessoaController {

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping("/cadastrar")
    public String cadastrarPessoa(@Valid Pessoa pessoa) {
        return "/cadastro";
    }
    
    @GetMapping("/")
    public String index(ModelMap model){
        
        String teste = "123";
        
        model.addAttribute("teste", teste);
        
        return "/index";
    }

    @PostMapping("/salvar")
    public String salvarPessoa(String nome, String cpf, String alimento, @Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {

        String verificadorCpf = pessoaRepository.verificaCpf(cpf);
        String verificadorAlimento = pessoaRepository.verificaAlimento(alimento);
        System.out.println(verificadorCpf);
        System.out.println(verificadorAlimento);

        if (result.hasErrors()) {
            return "/cadastro";

        } else if (verificadorCpf != null) {
            attr.addFlashAttribute("danger", "Este CPF já existe.");
            return "redirect:/cadastrar";

        } else if (verificadorAlimento != null) {
            attr.addFlashAttribute("danger", "Este alimento já existe.");
            return "redirect:/cadastrar";

        } else {
            pessoaRepository.cadastrarPessoaReepository(nome, cpf, alimento);
            attr.addFlashAttribute("success", "Cadastro salvo com sucesso!");
        }
        return "redirect:/listar";
    }

    @GetMapping("/alterar/{id}")
    public String preAlterarPessoa(@PathVariable("id") Long id, ModelMap model) {

        model.addAttribute("pessoa", pessoaRepository.findById(id));
        return "/cadastro";
    }

    @PostMapping("/alterar")
    public String alterarPessoa(String nome, String cpf, String alimento, Long id, @Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {

        Optional<Pessoa> pessoaAntiga = pessoaRepository.findById(id);

        if (result.hasErrors()) {
            return "/cadastro";

        }

        if (!pessoa.getCpf().equalsIgnoreCase(pessoaAntiga.get().getCpf())) {

            String verificadorCpf = pessoaRepository.verificaCpf(cpf);
            if (verificadorCpf != null) {
                attr.addFlashAttribute("danger", "Este CPF já existe.");
                return "redirect:/cadastrar";
            }
        }

        if (!pessoa.getAlimento().equalsIgnoreCase(pessoaAntiga.get().getAlimento())) {

            String verificadorAlimento = pessoaRepository.verificaAlimento(alimento);
            if (verificadorAlimento != null) {
                attr.addFlashAttribute("danger", "Este alimento já existe.");
                return "redirect:/cadastrar";
            }

        }
        
        pessoaRepository.alterarPessoaReepository(nome, cpf, alimento, id);
        attr.addFlashAttribute("warning", "Cadastro editado com sucesso!");
        return "redirect:/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluirPessoa(@PathVariable("id") Long id) {

        pessoaRepository.excluirPessoaRepository(id);
        return "redirect:/listar";
    }

    @GetMapping("/listar")
    public String listarPessoa(Pessoa pessoa, ModelMap model) {

        model.addAttribute("pessoas", pessoaRepository.listarPessoaRepository());
        return "/lista";
    }

    @GetMapping("/buscarNome")
    public String buscarPorNome(ModelMap model, String nome) {

        model.addAttribute("pessoas", pessoaRepository.buscarPorNomeRepository(nome));
        return "/lista";
    }

    @GetMapping("/buscarCpf")
    public String buscarPorCpf(ModelMap model, String cpf) {

        model.addAttribute("pessoas", pessoaRepository.buscarPorCpfRepository(cpf));
        return "/lista";
    }

    @GetMapping("/buscarAlimento")
    public String buscarPorAlimento(ModelMap model, String alimento) {

        model.addAttribute("pessoas", pessoaRepository.buscarPorAlimentoRepository(alimento));
        return "/lista";
    }

}
