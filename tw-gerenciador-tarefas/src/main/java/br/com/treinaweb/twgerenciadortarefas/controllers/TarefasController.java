package br.com.treinaweb.twgerenciadortarefas.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twgerenciadortarefas.modelos.Tarefa;
import br.com.treinaweb.twgerenciadortarefas.repositorios.RepositorioTarefa;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

	// Injecao de dependencia realizado pelo Spring
	@Autowired
	private RepositorioTarefa repositorioTarefa;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/listar");
		mv.addObject("tarefas", repositorioTarefa.findAll());
		return mv;
	}

	@GetMapping("/inserir")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/inserir");
		mv.addObject("tarefa", new Tarefa());
		return mv;
	}

	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
					"A data de expiração é obrigatória.");
		}else if (tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
					"A data de expiração não pode ser anterior a data atual.");
		}
		if (result.hasErrors()) {
			mv.setViewName("tarefas/inserir");
			mv.addObject(tarefa);
		} else {
			mv.setViewName("redirect:/tarefas/listar");
			repositorioTarefa.save(tarefa);
		}
		return mv;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Tarefa tarefa = repositorioTarefa.getOne(id);
		mv.addObject("tarefa", tarefa);
		
		return mv;
		
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
					"A data de expiração é obrigatória.");
		}else if (tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
					"A data de expiração não pode ser anterior a data atual.");
		}
		if (result.hasErrors()) {
			mv.setViewName("tarefas/alterar");
			mv.addObject(tarefa);
		} else {
			mv.setViewName("redirect:/tarefas/listar");
			repositorioTarefa.save(tarefa);
		}
		return mv;
	}
}
