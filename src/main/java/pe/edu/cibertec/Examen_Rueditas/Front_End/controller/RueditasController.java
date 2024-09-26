package pe.edu.cibertec.Examen_Rueditas.Front_End.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.Examen_Rueditas.Front_End.dto.RuedasRequestDTO;
import pe.edu.cibertec.Examen_Rueditas.Front_End.dto.RueditasRequestDTO;
import pe.edu.cibertec.Examen_Rueditas.Front_End.dto.RueditasResponseDTO;
import pe.edu.cibertec.Examen_Rueditas.Front_End.viewmodel.RueditasModel;

@Controller
@RequestMapping("/rueditas")
public class RueditasController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/buscar")
    public  String buscarRueditas(Model model){
        RueditasModel rueditasModel = new RueditasModel("00", "", "", "", "", "", "");
        model.addAttribute("rueditasModel", rueditasModel);
        return "inicio";
    }

    @PostMapping("/resultado")
    public String resultado(@Valid RuedasRequestDTO requestDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, devolver los mensajes de error.
            RueditasModel rueditasModel = new RueditasModel(
                    "01", bindingResult.getFieldError().getDefaultMessage(), "", "", "", "", ""
            );
            model.addAttribute("rueditasModel", rueditasModel);
            return "inicio";
        }
        try {
            String endpoint = "http://localhost:8081/rueditas/search";

            RueditasResponseDTO responseDTO = restTemplate.postForObject(endpoint, requestDTO, RueditasResponseDTO.class);

            if (responseDTO.codigo().equals("00")){
                RueditasModel rueditasModel = new RueditasModel(
                        "00",
                       "",
                       responseDTO.marca(),
                       responseDTO.modelo(),
                       responseDTO.nroAsientos(),
                       responseDTO.precio(),
                       responseDTO.color()
                );
                model.addAttribute("rueditasModel", rueditasModel);
                return "principal";
            }else{
                RueditasModel rueditasModel = new RueditasModel(
                        "01", "El auto no fue encontrado.",
                        "", "", "", "", "");
                model.addAttribute("rueditasModel", rueditasModel);
                return "inicio";
            }

        } catch (Exception e) {
            RueditasModel rueditasModel = new RueditasModel(
                    "99", "Error al encontrar el auto.",
                    "", "", "", "", "");
            model.addAttribute("rueditasModel", rueditasModel);

            return "inicio";
        }

    }
}
