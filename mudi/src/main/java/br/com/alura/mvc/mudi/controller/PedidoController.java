package br.com.alura.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.mvc.mudi.dto.ReqNovoPedido;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.User;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import br.com.alura.mvc.mudi.repository.UserRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRespository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("formulario")
	public String formulario(ReqNovoPedido req) {
		return "pedido/formulario";
	}

	@PostMapping("novo")
	public String novo(@Valid ReqNovoPedido req, BindingResult result) {
		if(result.hasErrors()) {
			return "pedido/formulario";
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		Pedido pedido = req.toPedido();
		pedido.setUser(user);
		pedidoRespository.save(pedido);
		return "redirect:/usuario/pedidos";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(name="id")Long id) {
	    pedidoRespository.deleteById(id);
	    return "redirect:/home";
	}
	
	
}
