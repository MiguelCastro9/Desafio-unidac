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
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView index(ModelMap model) {

        ModelAndView andView = new ModelAndView("index");
        String teste = "123";

        model.addAttribute("teste", teste);

        return andView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvarPessoa(String nome, String cpf, String alimento, @Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {

        ModelAndView andView = new ModelAndView("cadastro");
        ModelAndView andViewRedirectCadastrar = new ModelAndView("redirect:/cadastrar");
        ModelAndView andViewRedirectListar = new ModelAndView("redirect:/listar");

        String verificadorCpf = pessoaRepository.verificaCpf(cpf);
        String verificadorAlimento = pessoaRepository.verificaAlimento(alimento);
        System.out.println(verificadorCpf);
        System.out.println(verificadorAlimento);

        if (result.hasErrors()) {
            return andView;

        } else if (verificadorCpf != null) {
            attr.addFlashAttribute("danger", "Este CPF já existe.");
            return andViewRedirectCadastrar;

        } else if (verificadorAlimento != null) {
            attr.addFlashAttribute("danger", "Este alimento já existe.");
            return andViewRedirectCadastrar;

        } else {
            pessoaRepository.cadastrarPessoaReepository(nome, cpf, alimento);
            attr.addFlashAttribute("success", "Cadastro salvo com sucesso!");
        }
        return andViewRedirectListar;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView preAlterarPessoa(@PathVariable("id") Long id, ModelMap model) {

        ModelAndView andView = new ModelAndView("cadastro");

        model.addAttribute("pessoa", pessoaRepository.findById(id));
        return andView;
    }

    @PostMapping("/alterar")
    public ModelAndView alterarPessoa(String nome, String cpf, String alimento, Long id, @Valid Pessoa pessoa, BindingResult result, RedirectAttributes attr) {

        ModelAndView andView = new ModelAndView("cadastro");
        ModelAndView andViewRedirectCadastrar = new ModelAndView("redirect:/cadastrar");
        ModelAndView andViewRedirectListar = new ModelAndView("redirect:/listar");
        Optional<Pessoa> pessoaAntiga = pessoaRepository.findById(id);

        if (result.hasErrors()) {
            return andView;

        }

        if (!pessoa.getCpf().equalsIgnoreCase(pessoaAntiga.get().getCpf())) {

            String verificadorCpf = pessoaRepository.verificaCpf(cpf);
            if (verificadorCpf != null) {
                attr.addFlashAttribute("danger", "Este CPF já existe.");
                return andViewRedirectCadastrar;
            }
        }

        if (!pessoa.getAlimento().equalsIgnoreCase(pessoaAntiga.get().getAlimento())) {

            String verificadorAlimento = pessoaRepository.verificaAlimento(alimento);
            if (verificadorAlimento != null) {
                attr.addFlashAttribute("danger", "Este alimento já existe.");
                return andViewRedirectCadastrar;
            }

        }

        pessoaRepository.alterarPessoaReepository(nome, cpf, alimento, id);
        attr.addFlashAttribute("warning", "Cadastro editado com sucesso!");
        return andViewRedirectListar;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirPessoa(@PathVariable("id") Long id) {
        ModelAndView andViewRedirectListar = new ModelAndView("redirect:/listar");
        pessoaRepository.excluirPessoaRepository(id);
        return andViewRedirectListar;
    }

    @GetMapping("/listar")
    public ModelAndView listarPessoa(Pessoa pessoa, ModelMap model) {
        ModelAndView andViewRedirectListar = new ModelAndView("lista");
        model.addAttribute("pessoas", pessoaRepository.listarPessoaRepository());
        return andViewRedirectListar;
    }

    @GetMapping("/buscarNome")
    public ModelAndView buscarPorNome(ModelMap model, String nome) {
        ModelAndView andViewRedirectListar = new ModelAndView("lista");
        model.addAttribute("pessoas", pessoaRepository.buscarPorNomeRepository(nome));
        return andViewRedirectListar;
    }

    @GetMapping("/buscarCpf")
    public ModelAndView buscarPorCpf(ModelMap model, String cpf) {
        ModelAndView andViewRedirectListar = new ModelAndView("lista");
        model.addAttribute("pessoas", pessoaRepository.buscarPorCpfRepository(cpf));
        return andViewRedirectListar;
    }

    @GetMapping("/buscarAlimento")
    public ModelAndView buscarPorAlimento(ModelMap model, String alimento) {
        ModelAndView andViewRedirectListar = new ModelAndView("lista");
        model.addAttribute("pessoas", pessoaRepository.buscarPorAlimentoRepository(alimento));
        return andViewRedirectListar;
    }

}
